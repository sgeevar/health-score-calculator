package ai.quod.challenge.metrics;

import ai.quod.challenge.repo.RepoSummary;

public class IssueOpenCloseMetric extends Metric {
    public IssueOpenCloseMetric() {
        super("issue_open_close_ratio");
    }

    @Override
    protected double computeScore(Long repoId, RepoSummary repoSummary) {
        long o = repoSummary.getNumberOfOpenIssues();
        long c = repoSummary.getNumberOfClosedIssues();
        return c > 1 ? o / c : o;
    }

    @Override
    float getNormalizedScore(Long repoId) {
        //lower the better
        return 1 - super.getNormalizedScore(repoId);
    }
}
