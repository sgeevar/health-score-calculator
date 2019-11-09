package ai.quod.challenge.metrics;

public class MetricScore {
    private double score;
    private float normalizedScore;

    double getScore() {
        return score;
    }

    void setScore(double score) {
        this.score = score;
    }

    public float getNormalizedScore() {
        return normalizedScore;
    }

    public void setNormalizedScore(float normalizedScore) {
        this.normalizedScore = normalizedScore;
    }
}
