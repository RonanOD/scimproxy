package info.simplecloud.core.coding.handlers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.json.JSONException;
import org.json.JSONObject;

public class CalendarHandler implements ITypeHandler {
    private static final DateTimeFormatter XML_DATE_TIME_FORMAT = ISODateTimeFormat.dateTimeNoMillis();
    private SimpleDateFormat               simpleDateFormat     = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

    @Override
    public Object decode(JSONObject scimUserJson, String attributeId) throws JSONException {
        Calendar result = new GregorianCalendar();
        String stringDate = scimUserJson.getString(attributeId);
        DateTime dt = XML_DATE_TIME_FORMAT.parseDateTime(stringDate);
        Date date = dt.toDate();
        result.setTime(date);
        return result;
    }

    @Override
    public void encode(JSONObject scimUserJson, String attributeId, Object object) {
        if (attributeId == null) {
            throw new IllegalArgumentException("The attribute key may not be null");
        }

        try {
            Calendar cal = (Calendar) object;
            String value = simpleDateFormat.format(cal.getTime());
            scimUserJson.put(attributeId, value);
        } catch (JSONException e) {
            // Should not happen since we did the null check earlier
        }
    }

}
