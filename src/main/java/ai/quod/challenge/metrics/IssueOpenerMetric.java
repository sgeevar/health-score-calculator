package ai.quod.challenge.metrics;

import ai.quod.challenge.repo.RepoSummary;

public class IssueOpenerMetric extends Metric {
    public IssueOpenerMetric() {
        super("issue_open_user");
    }

    @Override
    protected double computeScore(Long repoId, RepoSummary repoSummary) {
        return repoSummary.getIssueOpeners();
    }
}
