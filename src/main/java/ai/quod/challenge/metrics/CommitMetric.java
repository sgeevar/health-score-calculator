package ai.quod.challenge.metrics;

import ai.quod.challenge.repo.RepoSummary;

public class CommitMetric extends Metric {
    public CommitMetric() {
        super("commits");
    }

    @Override
    protected double computeScore(Long repoId, RepoSummary repoSummary) {
        return repoSummary.getNumberOfCommits();
    }

    @Override
    protected float computeNormalizedScore(double score) {
        return (float) (score / maxScore);
    }
}
