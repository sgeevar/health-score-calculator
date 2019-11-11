package ai.quod.challenge.cmd;

import java.time.Instant;

//Command line options passed to the application
public class CommandOptions {
    private Instant startTime;
    private Instant endTime;

    public CommandOptions(String[] args) throws Exception {
        switch (args.length) {
            case 2:
                endTime = Instant.parse(args[1]);
            case 1:
                startTime = Instant.parse(args[0]);
            case 0:
                break;
            default:
                throw new Exception("Too many arguments");
        }

        if (endTime.compareTo(startTime) < 0) {
            throw new Exception("Start time should be less than end time");
        }
    }

    public Instant getStartTime() {
        return startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }
}
