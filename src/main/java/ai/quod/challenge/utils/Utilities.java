package ai.quod.challenge.utils;

import ai.quod.challenge.metrics.HealthSummary;

import java.io.File;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public final class Utilities {
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

    public static String getGitHubFileURL(Instant time) {
        String dateFormat = "YYYY-MM-dd-H";
        DateTimeFormatter df = DateTimeFormatter.ofPattern(dateFormat).withZone(ZoneOffset.UTC);
        return "https://data.gharchive.org/" + df.format(time) + ".json.gz";
    }

    public static void displayError(Exception e) {
        System.out.println("\nError: " + e.getMessage());
    }

    public static void exportCSV(ArrayList<HealthSummary> hs) {
        try (PrintWriter pw = new PrintWriter(new File("health_scores.csv"))) {
            pw.write(hs.get(0).getHeaders());
            for (int i = 0; i < 1000; i++) {
                pw.write(hs.get(i).toString());
            }
        } catch (Exception e) {
            displayError(e);
        }
    }
}
