package ai.quod.challenge.metrics;

import ai.quod.challenge.repo.RepoSummary;

public class ReleaseMetric extends Metric {
    public ReleaseMetric() {
        super("releases");
    }

    @Override
    protected double computeScore(Long repoId, RepoSummary repoSummary) {
        return repoSummary.getNumberOfReleases();
    }

    @Override
    protected float computeNormalizedScore(double score) {
        return (float) (score / maxScore);
    }
}