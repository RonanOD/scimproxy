package info.simplecloud.core;

import info.simplecloud.core.decoding.IUserDecoder;
import info.simplecloud.core.decoding.JsonDecoder;
import info.simplecloud.core.decoding.XmlDecoder;
import info.simplecloud.core.encoding.IUserEncoder;
import info.simplecloud.core.encoding.JsonEncoder;
import info.simplecloud.core.encoding.XmlEncoder;
import info.simplecloud.core.execeptions.EncodingFailed;
import info.simplecloud.core.execeptions.InvalidUser;
import info.simplecloud.core.execeptions.UnknownEncoding;

import java.util.HashMap;
import java.util.List;
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
    public static final String[]             simple                       = { ATTRIBUTE_ID, ATTRIBUTE_EXTERNALID, ATTRIBUTE_USER_NAME,
            ATTRIBUTE_DISPLAY_NAME, ATTRIBUTE_NICK_NAME, ATTRIBUTE_PROFILE_URL, ATTRIBUTE_EMPLOYEE_NUMBER, ATTRIBUTE_USER_TYPE,
            ATTRIBUTE_TITLE, ATTRIBUTE_MANAGER, ATTRIBUTE_PREFERRED_LANGUAGE, ATTRIBUTE_LOCALE, ATTRIBUTE_UTC_OFFSET,
            ATTRIBUTE_COST_CENTER, ATTRIBUTE_ORGANIZATION, ATTRIBUTE_DIVISION, ATTRIBUTE_DEPARTMENT };

    public static final String               ATTRIBUTE_NAME               = "name";
    public static final String               ATTRIBUTE_META               = "meta";
    public static final String[]             complex                      = { ATTRIBUTE_NAME, ATTRIBUTE_META };

    public static final String               ATTRIBUTE_IMS                = "ims";
    public static final String               ATTRIBUTE_EMAILS             = "emails";
    public static final String               ATTRIBUTE_PHOTOS             = "photos";
    public static final String               ATTRIBUTE_GROUPS             = "groups";
    public static final String               ATTRIBUTE_ADDRESSES          = "addresses";
    public static final String[]             plural                       = { ATTRIBUTE_IMS, ATTRIBUTE_EMAILS, ATTRIBUTE_PHOTOS,
            ATTRIBUTE_GROUPS, ATTRIBUTE_ADDRESSES                        };

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

    public ScimUser() {

    }

    public String getUser(String encoding) throws UnknownEncoding, EncodingFailed {
        IUserEncoder encoder = encoders.get(encoding);
        if (encoder == null) {
            throw new UnknownEncoding(encoding);
        }
        // TODO check if user has mandatory data

        return encoder.encode(this);
    }

    public Comparable getComparable(String attributeId) {
        Object object = super.getAttribute(attributeId);
        if (object != null && object instanceof Comparable) {
            return (Comparable) object;
        }
        return null;
    }

    public Name getName() {
        Object name = super.getAttribute(ATTRIBUTE_NAME);
        return (name == null ? null : (Name) name);
    }

    public Meta getMeta() {
        Object meta = super.getAttribute(ATTRIBUTE_META);
        return (meta == null ? null : (Meta) meta);
    }

    public List<PluralType<String>> getEmails() {
        Object emails = super.getAttribute(ATTRIBUTE_EMAILS);
        return (emails == null ? null : (List<PluralType<String>>) emails);
    }

    public List<PluralType<String>> getIms() {
        Object ims = super.getAttribute(ATTRIBUTE_IMS);
        return (ims == null ? null : (List<PluralType<String>>) ims);
    }

    public List<PluralType<String>> getPhotos() {
        Object photos = super.getAttribute(ATTRIBUTE_PHOTOS);
        return (photos == null ? null : (List<PluralType<String>>) photos);
    }

    public List<PluralType<String>> getGroups() {
        Object groups = super.getAttribute(ATTRIBUTE_GROUPS);
        return (groups == null ? null : (List<PluralType<String>>) groups);
    }

    public List<PluralType<Address>> getAddresses() {
        Object addresses = super.getAttribute(ATTRIBUTE_ADDRESSES);
        return (addresses == null ? null : (List<PluralType<Address>>) addresses);
    }

}
