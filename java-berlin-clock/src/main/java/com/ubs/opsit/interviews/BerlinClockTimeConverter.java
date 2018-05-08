package com.ubs.opsit.interviews;

/**
 * Created by Prasad on 08-05-2018.
 */
public class BerlinClockTimeConverter implements TimeConverter {
    @Override
    public String convertTime(String aTime) {
        return BerlinClock.constructBerlinClock(aTime).toString();
    }
}
