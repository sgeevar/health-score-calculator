package ai.quod.challenge.metrics;

import ai.quod.challenge.repo.RepoSummary;

public class ContributorMetric extends Metric {
    public ContributorMetric() {
        super("num_contributors");
    }

    @Override
    protected double computeScore(Long repoId, RepoSummary repoSummary) {
        return repoSummary.getContributors();
    }
}
