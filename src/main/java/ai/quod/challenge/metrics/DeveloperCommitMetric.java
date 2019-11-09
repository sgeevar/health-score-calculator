package ai.quod.challenge.metrics;

import ai.quod.challenge.repo.RepoSummary;

public class DeveloperCommitMetric extends Metric {
    public DeveloperCommitMetric() {
        super("commit_per_dev");
    }

    @Override
    protected double computeScore(Long repoId, RepoSummary repoSummary) {
        long n = repoSummary.getNumberOfDevelopers();
        return n > 0 ? repoSummary.getNumberOfCommits() / n : 0;
    }

    @Override
    protected float computeNormalizedScore(double score) {
        return (float) (score / maxScore);
    }
}
