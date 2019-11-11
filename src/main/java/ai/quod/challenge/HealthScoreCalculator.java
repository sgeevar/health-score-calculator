package ai.quod.challenge;

import ai.quod.challenge.cmd.CommandOptions;
import ai.quod.challenge.config.AppConfig;
import ai.quod.challenge.metrics.MetricsHelper;
import ai.quod.challenge.parser.ParsingHelper;
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
        //Processing the command line args. Invalid args will throw exception.
        CommandOptions co;
        try {
            co = new CommandOptions(args);
        } catch (Exception e) {
            Utilities.displayError(e);
            Utilities.showUsage();
            return;
        }

        Instant startTime = co.getStartTime();
        Instant endTime = co.getEndTime();
        logger.info("startTime[" + startTime.toString() + "] endTime[" + endTime.toString() + "]");

        AppConfig appConfig = AppConfig.getInstance(); //Configs for the App
        RepoSummaryList repoSummaryList = new RepoSummaryList(); //Consolidated list of all Repo Stats
        ParsingHelper parsingHelper = appConfig.getParsingHelper(); //List of event parsers
        MetricsHelper metricsHelper = appConfig.getMetricsHelper(); //List of event Metrics

        /* GitHub provides event data as archives for every hour.
        Iterating though all zips and extract the consolidated stats */
        while (startTime.compareTo(endTime) < 0) {
            String fileURL = Utilities.getGitHubFileURL(startTime);
            logger.info("Processing " + fileURL);

            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(new GZIPInputStream(new URL(fileURL).openStream())));
                String line;
                while ((line = br.readLine()) != null) {
                    JSONObject jo = new JSONObject(line);

                    long repoId = jo.getJSONObject("repo").getLong("id");
                    String repoName = jo.getJSONObject("repo").getString("name");

                    RepoSummary rs = repoSummaryList.getOrCreate(repoId, repoName);

                    /* Parsing helper will parse all the events for which a parser is configured
                    and extract the data into RepoSummary */
                    parsingHelper.parse(rs, jo);
                }
            } catch (Exception e) {
                Utilities.displayError(e);
            }

            startTime = startTime.plus(Duration.ofHours(1));
        }

        // Iterating through all the RepoSummaries and computing the metrics.
        ArrayList<Long> repoIdList = repoSummaryList.getRepoIds();
        logger.info("Computing metrics for [" + repoIdList.size() + "] repos");
        for (long id : repoIdList) {
            // metricsHelper will iterate though all metrics and compute scores
            metricsHelper.addRepoSummary(id, repoSummaryList.get(id));
        }

        //Extracting normalized scores, computing overall score and exporting to CSV
        logger.info("Computing normalized metrics, overall scores and exporting into CSV");
        Utilities.exportCSV(metricsHelper.extractSummary());

        logger.info("Export complete");
    }
}
