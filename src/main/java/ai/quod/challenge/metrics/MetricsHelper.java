package ai.quod.challenge.metrics;

import ai.quod.challenge.repo.RepoSummary;

import java.util.ArrayList;

public class MetricsHelper {
    private ArrayList<Metric> metrics;
    private ArrayList<HealthSummary> healthSummaryList;

    public MetricsHelper() {
        this.metrics = new ArrayList<>();
        this.healthSummaryList = new ArrayList<>();
    }

    public void addMetrics(Metric metric) {
        this.metrics.add(metric);
    }

    public void addRepoSummary(Long repoId, RepoSummary repoSummary) {
        for (Metric m : metrics) {
            m.addRepoSummary(repoId, repoSummary);
            healthSummaryList.add(new HealthSummary(repoId, repoSummary.getRepoName()));
        }
    }

    //Sum of all normalized scored / number of metrics
    private float getNormalizedScore(Long repoId) {
        float s = 0;
        for (Metric m : metrics) {
            s += m.getNormalizedScore(repoId);
        }
        return s / metrics.size();
    }

    /* Computes the overall normalized score and build a summary of health stats
    Then sort the stats descending order using a custom lambda */
    public ArrayList<HealthSummary> extractSummary() {
        for (HealthSummary hs : healthSummaryList) {
            hs.setScore(getNormalizedScore(hs.getRepoId()));
            for (Metric m : metrics) {
                hs.addMetricDetail(m.getMetricName(), m.getScore(hs.getRepoId()));
            }
        }
        healthSummaryList.sort((HealthSummary m1, HealthSummary m2)
                -> Float.compare(m2.getScore(), m1.getScore()));
        return healthSummaryList;
    }
}
