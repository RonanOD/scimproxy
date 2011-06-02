package info.simplecloud.core;

import info.simplecloud.core.decoding.IUserDecoder;
import info.simplecloud.core.decoding.JsonDecoder;
import info.simplecloud.core.decoding.XmlDecoder;
import info.simplecloud.core.encoding.IUserEncoder;
import info.simplecloud.core.encoding.JsonEncoder;
import info.simplecloud.core.encoding.XmlEncoder;
import info.simplecloud.core.execeptions.InvalidUserException;
import info.simplecloud.core.execeptions.UnknowEncodingException;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ScimUser {
    private static final String              ATTRIBUTE_ID                 = "id";
    private static final String              ATTRIBUTE_EXTERNALID         = "externalId";
    private static final String              ATTRIBUTE_USER_NAME          = "userName";
    private static final String              ATTRIBUTE_DISPLAY_NAME       = "displayName";
    private static final String              ATTRIBUTE_NICK_NAME          = "nickName";
    private static final String              ATTRIBUTE_PROFILE_URL        = "profileUrl";
    private static final String              ATTRIBUTE_EMPLOYEE_NUMBER    = "employeeNumber";
    private static final String              ATTRIBUTE_USER_TYPE          = "userType";
    private static final String              ATTRIBUTE_TITLE              = "title";
    private static final String              ATTRIBUTE_MANAGER            = "manager";
    private static final String              ATTRIBUTE_PREFERRED_LANGUAGE = "preferredLanguage";
    private static final String              ATTRIBUTE_LOCALE             = "locale";
    private static final String              ATTRIBUTE_UTC_OFFSET         = "utcOffset";
    private static final String              ATTRIBUTE_COST_CENTER        = "costCenter";
    private static final String              ATTRIBUTE_ORGANIZATION       = "organization";
    private static final String              ATTRIBUTE_DIVISION           = "division";
    private static final String              ATTRIBUTE_DEPARTMENT         = "department";
    
    private static Map<String, IUserEncoder> encoders                     = new HashMap<String, IUserEncoder>();
    private static Map<String, IUserDecoder> decoders                     = new HashMap<String, IUserDecoder>();
    static {
        new JsonEncoder().addMe(encoders);
        new XmlEncoder().addMe(encoders);
        new JsonDecoder().addMe(decoders);
        new XmlDecoder().addMe(decoders);
    }

    private Map<String, Comparable>          data                         = new HashMap<String, Comparable>();

    public ScimUser(String user, String encoding) throws UnknowEncodingException, InvalidUserException {
        IUserDecoder decoder = decoders.get(encoding);
        if (decoder == null) {
            throw new UnknowEncodingException(encoding);
        }

        decoder.decode(user, this);
    }

    public String getUser(String encoding) throws UnknowEncodingException {
        IUserEncoder encoder = encoders.get(encoding);
        if (encoder == null) {
            throw new UnknowEncodingException(encoding);
        }

        // TODO check if user has mandatory data

        return encoder.encode(this);
    }

    public void setId(String id) {
        this.data.put(ATTRIBUTE_ID, id);
    }

    public String getId() {
        return (String) this.data.get(ATTRIBUTE_ID);
    }

    public String getExternalId() {
        return (String) this.data.get(ATTRIBUTE_EXTERNALID);
    }

    public String getUserName() {
        return (String) this.data.get(ATTRIBUTE_USER_NAME);
    }

    public String getName() {
        return null; // TODO complex type
    }

    public String getDisplayName() {
        return (String) this.data.get(ATTRIBUTE_DISPLAY_NAME);
    }

    public String getNickName() {
        return (String) this.data.get(ATTRIBUTE_NICK_NAME);
    }

    public String getProfileUrl() {
        return (String) this.data.get(ATTRIBUTE_PROFILE_URL);
    }

    public String getEmployeeNumber() {
        return (String) this.data.get(ATTRIBUTE_EMPLOYEE_NUMBER);
    }

    public String getUserType() {
        return (String) this.data.get(ATTRIBUTE_USER_TYPE);
    }

    public String getTitle() {
        return (String) this.data.get(ATTRIBUTE_TITLE);
    }

    public String getManager() {
        return (String) this.data.get(ATTRIBUTE_MANAGER);
    }

    public String getPreferredLanguage() {
        return (String) this.data.get(ATTRIBUTE_PREFERRED_LANGUAGE);
    }

    public String getLocale() {
        return (String) this.data.get(ATTRIBUTE_LOCALE);
    }

    public Calendar getUtcOffset() {
        return (Calendar) this.data.get(ATTRIBUTE_UTC_OFFSET);
    }

    public String getCostCenter() {
        return (String) this.data.get(ATTRIBUTE_COST_CENTER);
    }

    public String getOrganization() {
        return (String) this.data.get(ATTRIBUTE_ORGANIZATION);
    }

    public String getDivision() {
        return (String) this.data.get(ATTRIBUTE_DIVISION);
    }

    public String getDepartment() {
        return (String) this.data.get(ATTRIBUTE_DEPARTMENT);
    }

    public String getMeta() {
        return null; // TODO complex type
    }

    public String getEmails() {
        return null; // TODO plural type
    }

    public String getPhoneNumbers() {
        return null; // TODO plural type
    }

    public String getIms() {
        return null; // TODO plural type
    }

    public String getPhotos() {
        return null; // TODO plural type
    }

    public String getGroups() {
        return null; // TODO plural type
    }

    public String getAddresses() {
        return null; // TODO plural type
    }

    // TODO setters for all attributes

    public Comparable getRaw(String attributeId) {
        return data.get(attributeId);
    }

}
