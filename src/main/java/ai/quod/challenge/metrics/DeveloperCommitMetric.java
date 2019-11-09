package ai.quod.challenge.metrics;

import ai.quod.challenge.repo.RepoSummary;

public class DeveloperCommitMetric extends Metric {
    public DeveloperCommitMetric() {
        super("commit_per_dev");
    }

    @Override
    protected double computeScore(Long repoId, RepoSummary repoSummary) {
        long n = repoSummary.getDevelopers();
        return n > 0 ? repoSummary.getCommits() / n : 0;
    }
}
