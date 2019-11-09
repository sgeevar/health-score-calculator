package ai.quod.challenge.metrics;

import java.util.HashMap;
import java.util.Map;

public class HealthSummary {
    private long repoId;
    private String repoName; //repoName contains 'org/repo'
    private float score;
    /* metricDetail captures individual stats used to compute the final score
    map allows n numbers of status to be captured with out a code change
    so that we can add new metrics in future with least amount of changes */
    private Map<String, Double> metricDetail;

    HealthSummary(long repoId, String repoName) {
        this.repoId = repoId;
        this.repoName = repoName;
        this.metricDetail = new HashMap<>();
    }

    long getRepoId() {
        return repoId;
    }

    private String getOrgName() {
        return repoName.split("/")[0];
    }

    private String getRepoName() {
        return repoName.split("/")[1];
    }

    float getScore() {
        return score;
    }

    void setScore(float score) {
        this.score = score;
    }

    void addMetricDetail(String metricName, Double metricScore) {
        metricDetail.put(metricName, metricScore);
    }

    public String getHeaders() {
        StringBuilder sb = new StringBuilder("org,repo,score");
        for (String s : metricDetail.keySet()) {
            sb.append(',');
            sb.append(s);
        }
        sb.append("\n");
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getOrgName() + "," + getRepoName() + "," + getScore());
        for (Double d : metricDetail.values()) {
            sb.append(',');
            sb.append(d);
        }
        sb.append("\n");
        return sb.toString();
    }
}
