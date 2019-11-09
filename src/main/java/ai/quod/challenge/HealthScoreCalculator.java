package ai.quod.challenge;

import ai.quod.challenge.parser.ParsingHelper;
import ai.quod.challenge.parser.ReleaseEventParser;
import ai.quod.challenge.repo.RepoSummary;
import ai.quod.challenge.repo.RepoSummaryList;
import ai.quod.challenge.utils.Utilities;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;

public class HealthScoreCalculator {
    private static final Logger logger = Logger.getLogger(HealthScoreCalculator.class.getName());

    public static void main(String[] args) {
        //endTime is 10min before now, to avoid 404 scenarios due to GH delay
        Instant endTime = Instant.now().minus(Duration.ofMinutes(10));
        Instant startTime = endTime.minus(Duration.ofHours(1));

        RepoSummaryList repoSummaryList = new RepoSummaryList();

        ParsingHelper parsingHelper = new ParsingHelper();
        parsingHelper.addEventParser("ReleaseEvent", new ReleaseEventParser());

        try {
            switch (args.length) {
                case 2:
                    endTime = Utilities.parseISOTime(args[1]);
                case 1:
                    startTime = Utilities.parseISOTime(args[0]);
                case 0:
                    break;
                default:
                    Utilities.showUsage();
                    return;

            }
        } catch (Exception e) {
            Utilities.displayError(e);
            Utilities.showUsage();
            return;
        }

        if (endTime.compareTo(startTime) < 0) {
            System.out.println("Error: Start time should be less than end time");
            return;
        }

        logger.info("startTime[" + startTime.toString() + "] endTime[" + endTime.toString() + "]");

        while (startTime.compareTo(endTime) < 0) {
            String fileURL = Utilities.getGitHubFileURL(startTime);
            logger.info("Processing " + fileURL);
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(new GZIPInputStream(new URL(fileURL).openStream())));
                String line;
                JSONObject jo;
                while ((line = br.readLine()) != null) {
                    jo = new JSONObject(line);
                    long repoId = jo.getJSONObject("repo").getLong("id");
                    String repoName = jo.getJSONObject("repo").getString("name");
                    RepoSummary rs = repoSummaryList.getOrCreate(repoId, repoName);
                    parsingHelper.parse(rs, jo);
                }
            } catch (Exception e) {
                Utilities.displayError(e);
            }
            startTime = startTime.plus(Duration.ofHours(1));
        }

        ArrayList<Long> repoIdList = repoSummaryList.getRepoIds();
        for (long id : repoIdList) {
            RepoSummary rs = repoSummaryList.get(id);
            System.out.println(rs.getRepoName() + "," + rs.getNumberOfReleases());
        }
    }
}
