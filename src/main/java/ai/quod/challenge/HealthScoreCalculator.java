package ai.quod.challenge;

import ai.quod.challenge.utils.Utilities;

import java.time.Duration;
import java.time.Instant;
import java.util.logging.Logger;

public class HealthScoreCalculator {
    private static final Logger logger = Logger.getLogger(HealthScoreCalculator.class.getName());

    public static void main(String[] args) {
        //endTime is 10min before now, to avoid 404 scenarios due to GH delay
        Instant endTime = Instant.now().minus(Duration.ofMinutes(10));
        Instant startTime = endTime.minus(Duration.ofHours(1));

        try {
            switch (args.length) {
                case 2:
                    endTime = Utilities.parseISOTime(args[1]);
                case 1:
                    startTime = Utilities.parseISOTime(args[0]);
                case 0:
                    break;
                default:
                    Utilities.showUsage();
                    return;

            }
        } catch (Exception e) {
            Utilities.showUsage();
            return;
        }

        logger.info("startTime[" + startTime.toString() + "] endTime[" + endTime.toString() + "]");
    }
}
