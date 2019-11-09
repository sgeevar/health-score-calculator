package ai.quod.challenge.metrics;

import ai.quod.challenge.repo.RepoSummary;

public class ReleaseMetric extends Metric {
    public ReleaseMetric() {
        super("num_releases");
    }

    @Override
    protected double computeScore(Long repoId, RepoSummary repoSummary) {
        return repoSummary.getReleases();
    }
}