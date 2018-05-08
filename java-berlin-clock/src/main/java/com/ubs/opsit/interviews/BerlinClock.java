package com.ubs.opsit.interviews;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Prasad on 07-05-2018.
 */
public class BerlinClock {

    private static final Pattern TIME_VALIDATION_PATTERN = Pattern.compile("^([0-1]\\d|2[0-4]):([0-5]\\d):([0-5]\\d)$");
    private String secondsBlinkingLamp;
    private String fiveHourLamps;
    private String oneHourLamp;
    private String fiveMinLamps;
    private String oneMinLamp;

    private BerlinClock(BerlinClockBuilder builder) {
        secondsBlinkingLamp = builder.secondsBlinkingLamp;
        fiveHourLamps = builder.fiveHourLamps;
        oneHourLamp = builder.oneHourLamp;
        fiveMinLamps = builder.fiveMinLamps;
        oneMinLamp = builder.oneMinLamp;
    }

    public static BerlinClock constructBerlinClock(String time) {

        Matcher matcher = TIME_VALIDATION_PATTERN.matcher(time);
        if (!matcher.matches())
            throw new IllegalArgumentException();

        int hours = Integer.parseInt(matcher.group(1));
        int minutes = Integer.parseInt(matcher.group(2));
        int seconds = Integer.parseInt(matcher.group(3));
        BerlinClockBuilder clockBuilder = new BerlinClockBuilder(hours, minutes, seconds);
        return clockBuilder.build();

    }

    @Override
    public String toString() {

        return String.join("\r\n", secondsBlinkingLamp, fiveHourLamps, oneHourLamp, fiveMinLamps, oneMinLamp);
    }

    static class BerlinClockBuilder {
        private String secondsBlinkingLamp;
        private String fiveHourLamps;
        private String oneHourLamp;
        private String fiveMinLamps;
        private String oneMinLamp;
        private int hours;
        private int minutes;
        private int seconds;

        public BerlinClockBuilder() {
            // set default values for  00:00:00
            secondsBlinkingLamp = "Y";
            fiveHourLamps = "OOOO";
            oneHourLamp = "OOOO";
            fiveMinLamps = "OOOOOOOOOOO";
            oneMinLamp = "OOOO";

        }

        public BerlinClockBuilder(int hours, int minutes, int seconds) {
            this();
            this.hours = hours;
            this.minutes = minutes;
            this.seconds = seconds;

        }


        private BerlinClockBuilder buildOneMinLamp() {
            int n = minutes % 15;
            while (n-- > 0) {
                this.oneMinLamp = this.oneMinLamp.replaceFirst("O", "Y");
            }
            return this;
        }

        public BerlinClockBuilder buildSecondsBlinkingLamp() {
            this.secondsBlinkingLamp = (seconds % 2 == 0) ? "Y" : "O";
            return this;
        }

        public BerlinClockBuilder buildFiveMinLamp() {

            int n = minutes / 5;
            while (n-- > 0) {
                this.fiveMinLamps = this.fiveMinLamps.replaceFirst("O", "Y");
            }

            return denoteQuarters();
        }

        public BerlinClockBuilder denoteQuarters() {
            this.fiveMinLamps = this.fiveMinLamps.replaceAll("YYY", "YYR");
            return this;
        }

        public BerlinClockBuilder buildOneHourLamp() {
            for (int i = 0; i < (hours % 5); i++) {
                this.oneHourLamp = this.oneHourLamp.replaceFirst("O", "R");
            }
            return this;
        }

        public BerlinClockBuilder buildFiveHourLamps() {
            for (int i = 0; i < (hours / 5); i++) {
                this.fiveHourLamps = this.fiveHourLamps.replaceFirst("O", "R");
            }
            return this;
        }

        public BerlinClock build() {
            this.buildFiveHourLamps().buildOneHourLamp().buildFiveMinLamp().buildOneMinLamp().buildSecondsBlinkingLamp();
            return new BerlinClock(this);

        }


    }


}

