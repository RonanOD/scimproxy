package info.simplecloud.core.coding.handlers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import info.simplecloud.core.execeptions.FailedToGetValue;
import info.simplecloud.core.execeptions.FailedToSetValue;
import info.simplecloud.core.execeptions.UnhandledAttributeType;
import info.simplecloud.core.execeptions.UnknownType;

import org.json.JSONException;
import org.json.JSONObject;

public class CalendarHandler implements ITypeHandler {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

    @Override
    public Object decode(JSONObject scimUserJson, String attributeId) throws JSONException, UnhandledAttributeType, FailedToSetValue,
            UnknownType, InstantiationException, IllegalAccessException, ParseException {
        Calendar result = new GregorianCalendar();
        String stringDate = scimUserJson.getString(attributeId);
        Date date = simpleDateFormat.parse(stringDate);
        result.setTime(date);
        return result;
    }

    @Override
    public void encode(JSONObject scimUserJson, String attributeId, Object object) throws JSONException, UnhandledAttributeType,
            FailedToSetValue, UnknownType, InstantiationException, IllegalAccessException, FailedToGetValue {
        Calendar cal = (Calendar) object;
        String value = simpleDateFormat.format(cal.getTime());
        scimUserJson.put(attributeId, value);
    }

}
