package ai.quod.challenge.metrics;

import ai.quod.challenge.repo.RepoSummary;

import java.util.HashMap;
import java.util.Map;

public abstract class Metric {
    double minScore;
    double maxScore;
    private Map<Long, MetricScore> scoreMap;
    private String metricName;

    Metric(String metricName) {
        this.scoreMap = new HashMap<>();
        this.metricName = metricName;
        this.minScore = Double.MAX_VALUE;
        this.maxScore = Double.MIN_VALUE;
    }

    String getMetricName() {
        return metricName;
    }

    protected abstract double computeScore(Long repoId, RepoSummary repoSummary);

    protected abstract float computeNormalizedScore(double score);

    void addRepoSummary(Long repoId, RepoSummary repoSummary) {
        double s = computeScore(repoId, repoSummary);
        if (s < minScore)
            minScore = s;
        if (s > maxScore)
            maxScore = s;

        MetricScore rs = scoreMap.get(repoId);
        if (rs == null) {
            rs = new MetricScore();
            scoreMap.put(repoId, rs);
        }
        rs.setScore(s);
    }

    float getNormalizedScore(Long repoId) {
        MetricScore s = scoreMap.get(repoId);
        return computeNormalizedScore(s.getScore());
    }

    double getScore(Long repoId) {
        return scoreMap.get(repoId).getScore();
    }
}
