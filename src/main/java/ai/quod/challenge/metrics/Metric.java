package ai.quod.challenge.metrics;

import ai.quod.challenge.repo.RepoSummary;

import java.util.HashMap;
import java.util.Map;

/* Base class for all Metrics. To add a new metric
1. Extend this class and define the abstract method computeScore
2. Configure the Metric in AppConfig */
public abstract class Metric {
    private double minScore;
    private double maxScore;
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

    private float computeNormalizedScore(double score) {
        return (float) (score / maxScore);
    }

    void addRepoSummary(Long repoId, RepoSummary repoSummary) {
        double s = computeScore(repoId, repoSummary); //Compute based on metric specific implementation
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
