package ai.quod.challenge;

import ai.quod.challenge.metrics.*;
import ai.quod.challenge.parser.*;
import ai.quod.challenge.repo.RepoSummary;
import ai.quod.challenge.repo.RepoSummaryList;
import ai.quod.challenge.utils.Utilities;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
        int topLimit = 1000;

        RepoSummaryList repoSummaryList = new RepoSummaryList();

        ParsingHelper parsingHelper = new ParsingHelper();
        parsingHelper.addEventParser("ReleaseEvent", new ReleaseEventParser());
        parsingHelper.addEventParser("CommitCommentEvent", new CommitCommentEventParser());
        parsingHelper.addEventParser("IssuesEvent", new IssuesEventParser());
        parsingHelper.addEventParser("PullRequestEvent", new PullRequestEventParser());

        MetricsHelper metricsHelper = new MetricsHelper();
        metricsHelper.addMetrics(new ReleaseMetric());
        metricsHelper.addMetrics(new CommitMetric());
        metricsHelper.addMetrics(new DeveloperCommitMetric());
        metricsHelper.addMetrics(new IssueOpenCloseMetric());
        metricsHelper.addMetrics(new IssueOpenerMetric());
        metricsHelper.addMetrics(new IssueOpenDurationMetric());
        metricsHelper.addMetrics(new ContributorMetric());
        metricsHelper.addMetrics(new PRCommentMetric());
        metricsHelper.addMetrics(new OpenPRMetric());
        metricsHelper.addMetrics(new PROpenDurationMetric());

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
        logger.info("Computing metrics for [" + repoIdList.size() + "] repos");
        for (long id : repoIdList) {
            // metricsHelper will iterate though all metrics and compute scores
            metricsHelper.addRepoSummary(id, repoSummaryList.get(id));
        }

        //Now that we have all metrics, just need to normalize and compute overall score
        logger.info("Computing normalized metrics and overall score");
        ArrayList<HealthSummary> hs = metricsHelper.extractSummary();

        try (PrintWriter pw = new PrintWriter(new File("health_scores.csv"))) {
            pw.write(hs.get(0).getHeaders());
            for (int i = 0; i < topLimit; i++) {
                pw.write(hs.get(i).toString());
            }
        } catch (Exception e) {
            Utilities.displayError(e);
        }
        logger.info("Done");
    }
}
