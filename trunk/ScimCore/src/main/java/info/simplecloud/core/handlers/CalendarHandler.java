package info.simplecloud.core.handlers;

import info.simplecloud.core.MetaData;
import info.simplecloud.core.coding.decode.IDecodeHandler;
import info.simplecloud.core.coding.encode.IEncodeHandler;
import info.simplecloud.core.exceptions.InvalidUser;
import info.simplecloud.core.merging.IMerger;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class CalendarHandler implements IDecodeHandler, IEncodeHandler, IMerger {

    @Override
    public Object decode(Object jsonData, Object me, MetaData internalMetaData) throws InvalidUser {
        String timeString = (String) jsonData;
        return this.parse(timeString);
    }

    @Override
    public Object decodeXml(Object value, Object newInstance, MetaData internalMetaData) {

        return HandlerHelper.typeCheck(value, Calendar.class);
    }

    @Override
    public Object encode(Object me, List<String> includeAttributes, MetaData internalMetaData) {
        Calendar cal = (Calendar) me;
        return this.getDateTime(cal);
    }

    @Override
    public Object encodeXml(Object me, List<String> includeAttributes, MetaData internalMetaData, Object xmlObject) {
        return HandlerHelper.typeCheck(me, Calendar.class);
    }

    @Override
    public Object merge(Object from, Object to) {
        return HandlerHelper.typeCheck(from, Calendar.class);
    }

    private Calendar parse(String dt) throws InvalidUser {
        String[] dateTime = dt.split("T");
        if (dateTime.length != 2) {
            throw new InvalidUser("Invalid time value, failed to split into date and time");
        }
        String date = dateTime[0];
        String time = dateTime[1];
        String[] ymd = date.split("-");
        if (ymd.length != 3) {
            throw new InvalidUser("Invalid time value, failed to split date");
        }
        int year = Integer.parseInt(ymd[0]);
        int month = Integer.parseInt(ymd[1]) - 1;
        int day = Integer.parseInt(ymd[2]);
        String[] hms = time.split(":");
        if (hms.length != 3) {
            throw new InvalidUser("Invalid time value, failed to split time");
        }
        int hour = Integer.parseInt(hms[0]);
        int minutes = Integer.parseInt(hms[1]);
        int seconds = Integer.parseInt(hms[2].substring(0, 2));
        TimeZone tz = TimeZone.getTimeZone("GMT+00:00");
        Calendar cal = Calendar.getInstance(tz);
        cal.set(year, month, day, hour, minutes, seconds);
        return cal;
    }

    private String getDateTime(Calendar cal) {
        int year = cal.get(Calendar.YEAR);
        String monthString = pad(cal.get(Calendar.MONTH) + 1);
        String dayString = pad(cal.get(Calendar.DAY_OF_MONTH));
        String hourString = pad(cal.get(Calendar.HOUR_OF_DAY));
        String minutesString = pad(cal.get(Calendar.MINUTE));
        String secondsString = pad(cal.get(Calendar.SECOND));

        StringBuilder sb = new StringBuilder();

        return sb.append(year).append("-").append(monthString).append("-").append(dayString).append("T").append(hourString).append(":")
                .append(minutesString).append(":").append(secondsString).append("Z").toString();
    }

    private static String pad(int value) {
        return (value > 9 ? "" : "0") + String.valueOf(value);
    }
}
