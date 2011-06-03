package info.simplecloud.core;

import info.simplecloud.core.decoding.IUserDecoder;
import info.simplecloud.core.decoding.JsonDecoder;
import info.simplecloud.core.decoding.XmlDecoder;
import info.simplecloud.core.encoding.IUserEncoder;
import info.simplecloud.core.encoding.JsonEncoder;
import info.simplecloud.core.encoding.XmlEncoder;
import info.simplecloud.core.execeptions.InvalidUser;
import info.simplecloud.core.execeptions.UnknownAttribute;
import info.simplecloud.core.execeptions.UnknownEncoding;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ScimUser extends ComplexType {
    public static final String               ATTRIBUTE_ID                 = "id";
    public static final String               ATTRIBUTE_EXTERNALID         = "externalId";
    public static final String               ATTRIBUTE_USER_NAME          = "userName";
    public static final String               ATTRIBUTE_DISPLAY_NAME       = "displayName";
    public static final String               ATTRIBUTE_NICK_NAME          = "nickName";
    public static final String               ATTRIBUTE_PROFILE_URL        = "profileUrl";
    public static final String               ATTRIBUTE_EMPLOYEE_NUMBER    = "employeeNumber";
    public static final String               ATTRIBUTE_USER_TYPE          = "userType";
    public static final String               ATTRIBUTE_TITLE              = "title";
    public static final String               ATTRIBUTE_MANAGER            = "manager";
    public static final String               ATTRIBUTE_PREFERRED_LANGUAGE = "preferredLanguage";
    public static final String               ATTRIBUTE_LOCALE             = "locale";
    public static final String               ATTRIBUTE_UTC_OFFSET         = "utcOffset";
    public static final String               ATTRIBUTE_COST_CENTER        = "costCenter";
    public static final String               ATTRIBUTE_ORGANIZATION       = "organization";
    public static final String               ATTRIBUTE_DIVISION           = "division";
    public static final String               ATTRIBUTE_DEPARTMENT         = "department";

    private static Map<String, IUserEncoder> encoders                     = new HashMap<String, IUserEncoder>();
    private static Map<String, IUserDecoder> decoders                     = new HashMap<String, IUserDecoder>();
    static {
        new JsonEncoder().addMe(encoders);
        new XmlEncoder().addMe(encoders);
        new JsonDecoder().addMe(decoders);
        new XmlDecoder().addMe(decoders);
    }

    public ScimUser(String user, String encoding) throws UnknownEncoding, InvalidUser {
        IUserDecoder decoder = decoders.get(encoding);
        if (decoder == null) {
            throw new UnknownEncoding(encoding);
        }

        decoder.decode(user, this);
    }

    public String getUser(String encoding) throws UnknownEncoding {
        IUserEncoder encoder = encoders.get(encoding);
        if (encoder == null) {
            throw new UnknownEncoding(encoding);
        }
        // TODO check if user has mandatory data

        return encoder.encode(this);
    }

    public String getAttribute(String... name) {

        return null;
    }

    // TODO setters for all attributes

    public Comparable getRaw(String attributeId) throws UnknownAttribute {
        Object object = super.getAttribute(attributeId);
        if (object instanceof Comparable) {
            return (Comparable) object;
        }
        return null;
    }

}
