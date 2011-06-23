package info.simplecloud.core;

import info.simplecloud.core.coding.ReflectionHelper;
import info.simplecloud.core.coding.decode.IUserDecoder;
import info.simplecloud.core.coding.decode.JsonDecoder;
import info.simplecloud.core.coding.decode.XmlDecoder;
import info.simplecloud.core.coding.encode.IUserEncoder;
import info.simplecloud.core.coding.encode.JsonEncoder;
import info.simplecloud.core.coding.encode.XmlEncoder;
import info.simplecloud.core.coding.handlers.ComplexTypeHandler;
import info.simplecloud.core.coding.handlers.IntegerHandler;
import info.simplecloud.core.coding.handlers.PluralComplexListTypeHandler;
import info.simplecloud.core.coding.handlers.PluralSimpleListTypeHandler;
import info.simplecloud.core.coding.handlers.StringHandler;
import info.simplecloud.core.execeptions.EncodingFailed;
import info.simplecloud.core.execeptions.FailedToGetValue;
import info.simplecloud.core.execeptions.FailedToSetValue;
import info.simplecloud.core.execeptions.InvalidUser;
import info.simplecloud.core.execeptions.UnhandledAttributeType;
import info.simplecloud.core.execeptions.UnknowExtension;
import info.simplecloud.core.execeptions.UnknownEncoding;
import info.simplecloud.core.execeptions.UnknownType;
import info.simplecloud.core.exstensions.EnterpriseAttributes;
import info.simplecloud.core.types.Address;
import info.simplecloud.core.types.ComplexType;
import info.simplecloud.core.types.Meta;
import info.simplecloud.core.types.Name;
import info.simplecloud.core.types.PluralType;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
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
    public static final String               ATTRIBUTE_USER_TYPE          = "userType";
    public static final String               ATTRIBUTE_TITLE              = "title";
    public static final String               ATTRIBUTE_PREFERRED_LANGUAGE = "preferredLanguage";
    public static final String               ATTRIBUTE_LOCALE             = "locale";
    public static final String               ATTRIBUTE_UTC_OFFSET         = "utcOffset";
    public static final String               ATTRIBUTE_NAME               = "name";
    public static final String               ATTRIBUTE_META               = "meta";
    public static final String               ATTRIBUTE_IMS                = "ims";
    public static final String               ATTRIBUTE_EMAILS             = "emails";
    public static final String               ATTRIBUTE_PHOTOS             = "photos";
    public static final String               ATTRIBUTE_MEMBER_OF          = "groups";
    public static final String               ATTRIBUTE_PHONE_NUMBERS      = "phoneNumbers";
    public static final String               ATTRIBUTE_ADDRESSES          = "addresses";

    public static final String               ENCODING_JSON                = "json";

    private List<Object>                     extensions                   = new ArrayList<Object>();
    {
        extensions.add(this);
        extensions.add(new EnterpriseAttributes());
    };

    private static Map<String, IUserEncoder> encoders                     = new HashMap<String, IUserEncoder>();
    private static Map<String, IUserDecoder> decoders                     = new HashMap<String, IUserDecoder>();
    static {
        new JsonEncoder().addMe(encoders);
        new XmlEncoder().addMe(encoders);
        new JsonDecoder().addMe(decoders);
        new XmlDecoder().addMe(decoders);
    }

    public ScimUser(String user, String encoding) throws UnknownEncoding, InvalidUser, UnhandledAttributeType, FailedToSetValue,
            UnknownType, InstantiationException, IllegalAccessException, ParseException {
        IUserDecoder decoder = decoders.get(encoding);
        if (decoder == null) {
            throw new UnknownEncoding(encoding);
        }

        decoder.decode(user, this);
    }

    public ScimUser() {

    }

    public String getUser(String encoding) throws UnknownEncoding, EncodingFailed, FailedToGetValue, UnhandledAttributeType {
        IUserEncoder encoder = encoders.get(encoding);
        if (encoder == null) {
            throw new UnknownEncoding(encoding);
        }
        // TODO check if user has mandatory data

        return encoder.encode(this);
    }

    public String getUser(String encoding, List<String> attributes) throws UnknownEncoding, EncodingFailed, FailedToGetValue, UnhandledAttributeType {
        IUserEncoder encoder = encoders.get(encoding);
        if (encoder == null) {
            throw new UnknownEncoding(encoding);
        }
        // TODO check if user has mandatory data

        return encoder.encode(this, attributes);
    }

    public Comparable getComparable(String attributeId) {
        Object object = super.getAttribute(attributeId);
        if (object != null && object instanceof Comparable) {
            return (Comparable) object;
        }
        return null;
    }

    public void patch(String patch, String encoding) throws UnknownEncoding, InvalidUser, UnhandledAttributeType, FailedToSetValue,
            UnknownType, InstantiationException, IllegalAccessException, FailedToGetValue, UnknowExtension, ParseException {
        ScimUser userPatch = new ScimUser(patch, encoding);

        Meta meta = userPatch.getMeta();
        if (meta != null) {
            List<String> attributesToDelete = meta.getAttributes();
            if (attributesToDelete != null) {
                for (String id : attributesToDelete) {
                    super.removeAttribute(id);
                }
            }
        }

        for (Object extension : this.extensions) {
            if (extension == this) {
                continue;
            }

            for (Method method : extension.getClass().getMethods()) {
                if (!method.isAnnotationPresent(Attribute.class)) {
                    continue;
                }

                try {
                    Object otherObj = userPatch.getExtension(extension.getClass());
                    Method otherMethod = otherObj.getClass().getMethod(method.getName(), method.getParameterTypes());
                    Object data = otherMethod.invoke(otherObj);

                    // TODO think of something smarter
                    String setter = "s" + method.getName().substring(1);
                    ReflectionHelper.getMethod(setter, extension.getClass()).invoke(extension, data);

                } catch (Exception e) {
                    // TODO select a good exception to throw
                    e.printStackTrace();
                }
            }
        }

        super.merge(userPatch);
    }

    @Override
    public boolean equals(Object otherObj) {
        if (otherObj == this) {
            return true;
        }

        if (!(otherObj instanceof ScimUser)) {
            return false;
        }
        ScimUser otherScimUser = (ScimUser) otherObj;

        for (Object exstension : this.getExtensions()) {
            for (Method method : exstension.getClass().getMethods()) {
                if (method.isAnnotationPresent(Attribute.class)) {
                    try {
                        Method otherMethod = otherScimUser.getExtension(exstension.getClass()).getClass().getMethod(method.getName());
                        Object obj = method.invoke(exstension);
                        Object other = otherMethod.invoke(otherScimUser.getExtension(exstension.getClass()));

                        if ((obj != null && !obj.equals(other)) || ((other != null && !other.equals(obj)))) {
                            return false;
                        }

                    } catch (Exception e) {
                        throw new RuntimeException("ScimUser equals failed", e);
                    }
                }
            }
        }

        return true;
    }

    @SuppressWarnings("unchecked")
    public <T> T getExtension(Class type) throws UnknowExtension {
        for (int i = 0; i < this.extensions.size(); i++) {
            if (this.extensions.get(i).getClass() == type) {
                return (T) this.extensions.get(i);
            }
        }

        throw new UnknowExtension("Could not find object for type '" + type.getName() + "'");
    }

    public List<Object> getExtensions() {
        return this.extensions;
    }

    @Attribute(schemaName = "id", codingHandler = StringHandler.class)
    public String getId() {
        return super.getAttributeString(ATTRIBUTE_ID);
    }

    @Attribute(schemaName = "externalId", codingHandler = StringHandler.class)
    public String getExternalId() {
        return super.getAttributeString(ATTRIBUTE_EXTERNALID);
    }

    @Attribute(schemaName = "userName", codingHandler = StringHandler.class)
    public String getUserName() {
        return super.getAttributeString(ATTRIBUTE_USER_NAME);
    }

    @Attribute(schemaName = "displayName", codingHandler = StringHandler.class)
    public String getDisplayName() {
        return super.getAttributeString(ATTRIBUTE_DISPLAY_NAME);
    }

    @Attribute(schemaName = "nickName", codingHandler = StringHandler.class)
    public String getNickName() {
        return super.getAttributeString(ATTRIBUTE_NICK_NAME);
    }

    @Attribute(schemaName = "profileUrl", codingHandler = StringHandler.class)
    public String getProfileUrl() {
        return super.getAttributeString(ATTRIBUTE_PROFILE_URL);
    }

    @Attribute(schemaName = "userType", codingHandler = StringHandler.class)
    public String getUserType() {
        return super.getAttributeString(ATTRIBUTE_USER_TYPE);
    }

    @Attribute(schemaName = "title", codingHandler = StringHandler.class)
    public String getTitle() {
        return super.getAttributeString(ATTRIBUTE_TITLE);
    }

    @Attribute(schemaName = "preferredLanguage", codingHandler = StringHandler.class)
    public String getPreferredLanguage() {
        return super.getAttributeString(ATTRIBUTE_PREFERRED_LANGUAGE);
    }

    @Attribute(schemaName = "locale", codingHandler = StringHandler.class)
    public String getLocale() {
        return super.getAttributeString(ATTRIBUTE_LOCALE);
    }

    @Attribute(schemaName = "utcOffset", codingHandler = IntegerHandler.class)
    public Integer getUtcOffset() {
        return super.getAttributeInteger(ATTRIBUTE_UTC_OFFSET);
    }

    @Attribute(schemaName = "name", codingHandler = ComplexTypeHandler.class)
    public Name getName() {
        Object name = super.getAttribute(ATTRIBUTE_NAME);
        return (name == null ? null : (Name) name);
    }

    @Attribute(schemaName = "meta", codingHandler = ComplexTypeHandler.class)
    public Meta getMeta() {
        Object meta = super.getAttribute(ATTRIBUTE_META);
        return (meta == null ? null : (Meta) meta);
    }

    @Attribute(schemaName = "phoneNumbers", codingHandler = PluralSimpleListTypeHandler.class)
    public List<PluralType<String>> getPhoneNumbers() {
        Object phoneNumbers = super.getAttribute(ATTRIBUTE_PHONE_NUMBERS);
        return (phoneNumbers == null ? null : (List<PluralType<String>>) phoneNumbers);
    }

    @Attribute(schemaName = "emails", codingHandler = PluralSimpleListTypeHandler.class)
    public List<PluralType<String>> getEmails() {
        Object emails = super.getAttribute(ATTRIBUTE_EMAILS);
        return (emails == null ? null : (List<PluralType<String>>) emails);
    }

    @Attribute(schemaName = "ims", codingHandler = PluralSimpleListTypeHandler.class)
    public List<PluralType<String>> getIms() {
        Object ims = super.getAttribute(ATTRIBUTE_IMS);
        return (ims == null ? null : (List<PluralType<String>>) ims);
    }

    @Attribute(schemaName = "photos", codingHandler = PluralSimpleListTypeHandler.class)
    public List<PluralType<String>> getPhotos() {
        Object photos = super.getAttribute(ATTRIBUTE_PHOTOS);
        return (photos == null ? null : (List<PluralType<String>>) photos);
    }

    @Attribute(schemaName = "memberOf", codingHandler = PluralSimpleListTypeHandler.class)
    public List<PluralType<String>> getMemberOf() {
        Object groups = super.getAttribute(ATTRIBUTE_MEMBER_OF);
        return (groups == null ? null : (List<PluralType<String>>) groups);
    }

    @Attribute(schemaName = "addresses", codingHandler = PluralComplexListTypeHandler.class)
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

    public void setUserType(String userType) {
        super.setAttribute(ATTRIBUTE_USER_TYPE, userType);
    }

    public void setTitle(String title) {
        super.setAttribute(ATTRIBUTE_TITLE, title);
    }

    public void setPreferredLanguage(String preferredLanguage) {
        super.setAttribute(ATTRIBUTE_PREFERRED_LANGUAGE, preferredLanguage);
    }

    public void setLocale(String locale) {
        super.setAttribute(ATTRIBUTE_LOCALE, locale);
    }

    public void setUtcOffset(Integer utcOffset) {
        super.setAttribute(ATTRIBUTE_UTC_OFFSET, utcOffset);
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
        super.setAttribute(ATTRIBUTE_MEMBER_OF, groups);
    }

    public void setAddresses(List<PluralType<Address>> address) {
        super.setAttribute(ATTRIBUTE_ADDRESSES, address);
    }

}
