package ai.quod.challenge.utils;

import java.time.Instant;

public final class Utilities {
    public static Instant parseISOTime(String timeString) {
        return Instant.parse(timeString);
    }

    public static void showUsage() {
        StringBuilder sb = new StringBuilder("Usage: ai.quod.challenge.HealthScoreCalculator [ISO_8601_datetime_start] [ISO_8601_datetime_end]");
        sb.append("\n\nai.quod.challenge.HealthScoreCalculator 2019-08-01T00:00:00Z 2019-09-01T00:00:00Z");
        sb.append("\nHealth score between 2019-08-01T00:00:00Z and 2019-09-01T00:00:00Z");
        sb.append("\n\nai.quod.challenge.HealthScoreCalculator 2019-08-01T00:00:00Z");
        sb.append("\nHealth score between 2019-08-01T00:00:00Z and now");
        sb.append("\n\nai.quod.challenge.HealthScoreCalculator");
        sb.append("\nHealth score for the last one hour\n");
        System.out.print(sb.toString());
    }
}
