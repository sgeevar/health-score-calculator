package ai.quod.challenge.metrics;

import ai.quod.challenge.repo.RepoSummary;

public class OpenPRMetric extends Metric {
    public OpenPRMetric() {
        super("num_open_pr");
    }

    @Override
    protected double computeScore(Long repoId, RepoSummary repoSummary) {
        return repoSummary.getOpenPRs();
    }

    @Override
    float getNormalizedScore(Long repoId) {
        //Less number of PRs in Open state is better
        return 1 - super.getNormalizedScore(repoId);
    }
}
