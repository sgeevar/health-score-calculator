package ai.quod.challenge.metrics;

import ai.quod.challenge.repo.RepoSummary;

public class PROpenDurationMetric extends Metric {
    public PROpenDurationMetric() {
        super("pr_open_duration");
    }

    @Override
    protected double computeScore(Long repoId, RepoSummary repoSummary) {
        long n = repoSummary.getMergedPullRequests();
        return n > 0 ? repoSummary.getTotalPROpenDuration() / n : 0;
    }

    @Override
    float getNormalizedScore(Long repoId) {
        //here, having minimum delay between open & close is better
        return 1 - super.getNormalizedScore(repoId);
    }
}
