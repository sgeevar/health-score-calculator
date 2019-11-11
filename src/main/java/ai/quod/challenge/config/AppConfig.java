package ai.quod.challenge.config;

import ai.quod.challenge.metrics.*;
import ai.quod.challenge.parser.*;

/* Basic configuration for the Application. Ideally this should be populated
via dependency injection (Ex Spring) */
public class AppConfig {
    private static AppConfig instance = null;
    private ParsingHelper parsingHelper;
    private MetricsHelper metricsHelper;

    private AppConfig() {
        parsingHelper = InitParsingHelper();
        metricsHelper = InitMetricsHelper();
    }

    public static AppConfig getInstance() {
        if (instance == null) {
            instance = new AppConfig();
        }
        return instance;
    }

    public ParsingHelper getParsingHelper() {
        return parsingHelper;
    }

    public MetricsHelper getMetricsHelper() {
        return metricsHelper;
    }

    private ParsingHelper InitParsingHelper() {
        ParsingHelper parsingHelper = new ParsingHelper();
        parsingHelper.addEventParser("ReleaseEvent", new ReleaseEventParser());
        parsingHelper.addEventParser("CommitCommentEvent", new CommitCommentEventParser());
        parsingHelper.addEventParser("IssuesEvent", new IssuesEventParser());
        parsingHelper.addEventParser("PullRequestEvent", new PullRequestEventParser());
        return parsingHelper;
    }

    private MetricsHelper InitMetricsHelper() {
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
        return metricsHelper;
    }
}
