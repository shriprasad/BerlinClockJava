package com.ubs.opsit.interviews.junit;
import com.ubs.opsit.interviews.BerlinClock;
import com.ubs.opsit.interviews.BerlinClockTimeConverter;
import org.junit.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class BerlinClockConverterTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @Test
    public void testMinValidBerlinClock() {
        new BerlinClockTimeConverter().convertTime("00:00:00");
    }

    @Test
    public void testMaxValidBerlinClock() {
        new BerlinClockTimeConverter().convertTime("23:59:59");
    }

    @Test
    public void testPrintClock() {
       String output= new BerlinClockTimeConverter().convertTime("12:30:30");

        String expected =  String.join("\r\n","Y","RROO","RROO","YYRYYROOOOO","OOOO");

        assertEquals(expected, output);
    }

     @Test(expected = IllegalArgumentException.class)
    public void testUpperInvalidMinutes() {
        new BerlinClockTimeConverter().convertTime("00:60:00");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpperInvalidSeconds() {
        new BerlinClockTimeConverter().convertTime("00:00:60");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLowerInvalidHours() {
        new BerlinClockTimeConverter().convertTime("-01:00:00");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLowerInvalidMinutes() {
        new BerlinClockTimeConverter().convertTime("00:-01:00");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLowerInvalidSeconds() {
        new BerlinClockTimeConverter().convertTime("00:00:-01");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidString() {
        new BerlinClockTimeConverter().convertTime("00:00");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullString() {
        new BerlinClockTimeConverter().convertTime(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyString() {
        new BerlinClockTimeConverter().convertTime("");
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }

}