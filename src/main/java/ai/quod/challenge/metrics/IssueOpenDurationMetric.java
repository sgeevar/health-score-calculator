package ai.quod.challenge.metrics;

import ai.quod.challenge.repo.RepoSummary;

public class IssueOpenDurationMetric extends Metric {
    public IssueOpenDurationMetric() {
        super("issue_open_duration");
    }

    @Override
    protected double computeScore(Long repoId, RepoSummary repoSummary) {
        long c = repoSummary.getNumberOfClosedIssues();
        return c > 0 ? repoSummary.getSumOfIssueOpenDuration() / c : 0;
    }

    @Override
    float getNormalizedScore(Long repoId) {
        //here, having minimum delay between open & close is better
        return 1 - super.getNormalizedScore(repoId);
    }
}
