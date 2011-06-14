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

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;

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
    private static final String[]            simple                       = { ATTRIBUTE_ID, ATTRIBUTE_EXTERNALID, ATTRIBUTE_USER_NAME,
            ATTRIBUTE_DISPLAY_NAME, ATTRIBUTE_NICK_NAME, ATTRIBUTE_PROFILE_URL, ATTRIBUTE_EMPLOYEE_NUMBER, ATTRIBUTE_USER_TYPE,
            ATTRIBUTE_TITLE, ATTRIBUTE_MANAGER, ATTRIBUTE_PREFERRED_LANGUAGE, ATTRIBUTE_LOCALE, ATTRIBUTE_UTC_OFFSET,
            ATTRIBUTE_COST_CENTER, ATTRIBUTE_ORGANIZATION, ATTRIBUTE_DIVISION, ATTRIBUTE_DEPARTMENT };

    public static final String               ATTRIBUTE_NAME               = "name";
    public static final String               ATTRIBUTE_META               = "meta";
    private static final String[]            complex                      = { ATTRIBUTE_NAME, ATTRIBUTE_META };

    public static final String               ATTRIBUTE_IMS                = "ims";
    public static final String               ATTRIBUTE_EMAILS             = "emails";
    public static final String               ATTRIBUTE_PHOTOS             = "photos";
    public static final String               ATTRIBUTE_GROUPS             = "groups";
    public static final String               ATTRIBUTE_PHONE_NUMBERS      = "phoneNumbers";
    private static final String[]            plural                       = { ATTRIBUTE_IMS, ATTRIBUTE_EMAILS, ATTRIBUTE_PHOTOS,
            ATTRIBUTE_GROUPS, ATTRIBUTE_PHONE_NUMBERS                    };

    public static final String               ATTRIBUTE_ADDRESSES          = "addresses";
    private static final String[]            complexPlural                = { ATTRIBUTE_ADDRESSES };

    private static Map<String, IUserEncoder> encoders                     = new HashMap<String, IUserEncoder>();
    private static Map<String, IUserDecoder> decoders                     = new HashMap<String, IUserDecoder>();
    static {
        new JsonEncoder().addMe(encoders);
        new XmlEncoder().addMe(encoders);
        new JsonDecoder().addMe(decoders);
        new XmlDecoder().addMe(decoders);
    }

    @Override
    public String[] getSimple() {
        return simple;
    }

    @Override
    public String[] getPlural() {
        return plural;
    }

    @Override
    public String[] getComplex() {
        return complex;
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

    public void patch(String patch, String encoding) throws UnknownEncoding, InvalidUser {
        ScimUser userPatch = new ScimUser(patch, encoding);

        Meta meta = userPatch.getMeta();
        List<String> attributesToDelete = meta.getAttributes();
        for (String id : attributesToDelete) {
            super.removeAttribute(id);
        }

        super.merge(userPatch, simple, plural, complex);
    }

    public String getId() {
        return super.getAttributeString(ATTRIBUTE_ID);
    }

    public String getExternalId() {
        return super.getAttributeString(ATTRIBUTE_EXTERNALID);
    }

    public String getUserName() {
        return super.getAttributeString(ATTRIBUTE_USER_NAME);
    }

    public String getDisplayName() {
        return super.getAttributeString(ATTRIBUTE_DISPLAY_NAME);
    }

    public String getNickName() {
        return super.getAttributeString(ATTRIBUTE_NICK_NAME);
    }

    public String getProfileUrl() {
        return super.getAttributeString(ATTRIBUTE_PROFILE_URL);
    }

    public String getEmployeeNumber() {
        return super.getAttributeString(ATTRIBUTE_EMPLOYEE_NUMBER);
    }

    public String getUserType() {
        return super.getAttributeString(ATTRIBUTE_USER_TYPE);
    }

    public String getTitle() {
        return super.getAttributeString(ATTRIBUTE_TITLE);
    }

    public String getManager() {
        return super.getAttributeString(ATTRIBUTE_MANAGER);
    }

    public String getPreferredLanguage() {
        return super.getAttributeString(ATTRIBUTE_PREFERRED_LANGUAGE);
    }

    public String getLocale() {
        return super.getAttributeString(ATTRIBUTE_LOCALE);
    }

    public Calendar getUtcOffset() {
        return super.getAttributeCalendar(ATTRIBUTE_UTC_OFFSET);
    }

    public String getCostCenter() {
        return super.getAttributeString(ATTRIBUTE_COST_CENTER);
    }

    public String getOrganization() {
        return super.getAttributeString(ATTRIBUTE_ORGANIZATION);
    }

    public String getDivision() {
        return super.getAttributeString(ATTRIBUTE_DIVISION);
    }

    public String getDepartment() {
        return super.getAttributeString(ATTRIBUTE_DEPARTMENT);
    }

    public Name getName() {
        Object name = super.getAttribute(ATTRIBUTE_NAME);
        return (name == null ? null : (Name) name);
    }

    public Meta getMeta() {
        Object meta = super.getAttribute(ATTRIBUTE_META);
        return (meta == null ? null : (Meta) meta);
    }

    public List<PluralType<String>> getPhoneNumbers() {
        Object phoneNumbers = super.getAttribute(ATTRIBUTE_PHONE_NUMBERS);
        return (phoneNumbers == null ? null : (List<PluralType<String>>) phoneNumbers);
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

    public void setId(String id) {
        super.setAttribute(ATTRIBUTE_ID, id);
    }

    public void setExternalId(String externalId) {
        super.setAttribute(ATTRIBUTE_EXTERNALID, externalId);
    }

    public void setUserName(String userName) {
        super.setAttribute(ATTRIBUTE_USER_NAME, userName);
    }

    public void setDisplayName(String displayName) {
        super.setAttribute(ATTRIBUTE_DISPLAY_NAME, displayName);
    }

    public void setNickName(String nickName) {
        super.setAttribute(ATTRIBUTE_NICK_NAME, nickName);
    }

    public void setProfileUrl(String profileUrl) {
        super.setAttribute(ATTRIBUTE_PROFILE_URL, profileUrl);
    }

    public void setEmployeeNumber(String employeeNumber) {
        super.setAttribute(ATTRIBUTE_EMPLOYEE_NUMBER, employeeNumber);
    }

    public void setUserType(String userType) {
        super.setAttribute(ATTRIBUTE_USER_TYPE, userType);
    }

    public void setTitle(String title) {
        super.setAttribute(ATTRIBUTE_TITLE, title);
    }

    public void setManager(String manager) {
        super.setAttribute(ATTRIBUTE_MANAGER, manager);
    }

    public void setPreferredLanguage(String preferredLanguage) {
        super.setAttribute(ATTRIBUTE_PREFERRED_LANGUAGE, preferredLanguage);
    }

    public void setLocale(String locale) {
        super.setAttribute(ATTRIBUTE_LOCALE, locale);
    }

    public void setUtcOffset(Calendar utcOffset) {
        super.setAttribute(ATTRIBUTE_UTC_OFFSET, utcOffset);
    }

    public void setCostCenter(String costCenter) {
        super.setAttribute(ATTRIBUTE_COST_CENTER, costCenter);
    }

    public void setOrganization(String organization) {
        super.setAttribute(ATTRIBUTE_ORGANIZATION, organization);
    }

    public void setDivision(String division) {
        super.setAttribute(ATTRIBUTE_DIVISION, division);
    }

    public void setDepartment(String department) {
        super.setAttribute(ATTRIBUTE_DEPARTMENT, department);
    }

    public void setName(Name name) {
        super.setAttribute(ATTRIBUTE_NAME, name);
    }

    public void setMeta(Meta meta) {
        super.setAttribute(ATTRIBUTE_META, meta);
    }

    public void setPhoneNumbers(List<PluralType<String>> phoneNumbers) {
        super.setAttribute(ATTRIBUTE_PHONE_NUMBERS, phoneNumbers);
    }

    public void setEmails(List<PluralType<String>> emails) {
        super.setAttribute(ATTRIBUTE_EMAILS, emails);
    }

    public void setIms(List<PluralType<String>> ims) {
        super.setAttribute(ATTRIBUTE_IMS, ims);
    }

    public void setPhotos(List<PluralType<String>> photos) {
        super.setAttribute(ATTRIBUTE_PHOTOS, photos);
    }

    public void setGroups(List<PluralType<String>> groups) {
        super.setAttribute(ATTRIBUTE_GROUPS, groups);
    }

    public void setAddresses(List<PluralType<Address>> address) {
        super.setAttribute(ATTRIBUTE_ADDRESSES, address);
    }

    public void addPhoneNumbers(PluralType<String> phoneNumbers) {
        super.addPluralAttribute(ATTRIBUTE_PHONE_NUMBERS, phoneNumbers);
    }

    public void addEmails(PluralType<String> emails) {
        super.addPluralAttribute(ATTRIBUTE_EMAILS, emails);
    }

    public void addIms(PluralType<String> ims) {
        super.addPluralAttribute(ATTRIBUTE_IMS, ims);
    }

    public void addPhotos(PluralType<String> photos) {
        super.addPluralAttribute(ATTRIBUTE_PHOTOS, photos);
    }

    public void addGroups(PluralType<String> groups) {
        super.addPluralAttribute(ATTRIBUTE_GROUPS, groups);
    }

    public void addAddresses(PluralType<Address> address) {
        super.addPluralAttribute(ATTRIBUTE_ADDRESSES, address);
    }
}
