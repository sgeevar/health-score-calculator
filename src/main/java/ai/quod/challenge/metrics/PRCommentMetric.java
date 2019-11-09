package ai.quod.challenge.metrics;

import ai.quod.challenge.repo.RepoSummary;

public class PRCommentMetric extends Metric {
    public PRCommentMetric() {
        super("comments_per_pr");
    }

    @Override
    protected double computeScore(Long repoId, RepoSummary repoSummary) {
        long pr = repoSummary.getPullRequests();
        return pr > 0 ? repoSummary.getCommentsOnPR() / pr : 0;
    }
}
