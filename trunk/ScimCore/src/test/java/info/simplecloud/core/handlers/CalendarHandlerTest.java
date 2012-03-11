package info.simplecloud.core.handlers;

import info.simplecloud.core.exceptions.InvalidUser;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Test;

public class CalendarHandlerTest {
    private static CalendarHandler ch = new CalendarHandler();

    @Test(expected = InvalidUser.class)
    public void encodeInvalideDate() throws InvalidUser {
        String date = "2011-12-33T22:22:22T3";
        ch.decode(date, null, null);
    }

    @Test
    public void encode() throws InvalidUser {
        TimeZone tz = TimeZone.getTimeZone("GMT+00:00");
        Calendar cal = Calendar.getInstance(tz);
        cal.setTime(new Date(System.currentTimeMillis()));
        ch.encode(cal, null, null, null);

        // TODO check answer
    }

    @Test
    public void decode() throws InvalidUser {
        String date = "2008-01-23T04:56:22Z";
        ch.decode(date, null, null);

        // TODO check answer
    }
}
