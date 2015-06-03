package info.simplecloud.core;

import info.simplecloud.core.annotations.Attribute;
import info.simplecloud.core.annotations.Complex;
import info.simplecloud.core.annotations.Extension;
import info.simplecloud.core.exceptions.InvalidUser;
import info.simplecloud.core.exceptions.UnknownAttribute;
import info.simplecloud.core.exceptions.UnknownEncoding;
import info.simplecloud.core.handlers.Base64BinaryHandler;
import info.simplecloud.core.handlers.BooleanHandler;
import info.simplecloud.core.handlers.ComplexHandler;
import info.simplecloud.core.handlers.MultiValueHandler;
import info.simplecloud.core.handlers.StringHandler;
import info.simplecloud.core.types.Address;
import info.simplecloud.core.types.Name;
import info.simplecloud.core.types.MultiValuedType;

import java.util.ArrayList;
import java.util.List;

import x0.scimSchemasCore1.UserDocument;

@Extension(schema = "urn:scim:schemas:core:1.0")
@Complex(xmlType = x0.scimSchemasCore1.User.class, xmlDoc = UserDocument.class)
public class User extends Resource {

    private static List<Class<?>> extensionTypes = new ArrayList<Class<?>>();
    static {
        String extensionsString = System.getProperty("info.simplecloud.core.user.extensions");
        if (extensionsString != null) {
            String[] extensionNames = extensionsString.split(";");
            for (String extensionName : extensionNames) {
                try {
                    extensionTypes.add(Class.forName(extensionName));
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException("Failed reading user extensions", e);
                }
            }
        }
    }

    public static void registerExtension(Class<?> clazz) {
        if (!extensionTypes.contains(clazz)) {
            extensionTypes.add(clazz);
        }
    }

    private String                         userName;
    private String                         displayName;
    private String                         nickName;
    private String                         profileUrl;
    private String                         userType;
    private String                         title;
    private String                         preferredLanguage;
    private String                         locale;
    private String                         password;
    private String                         timezone;
    private boolean                        active = true;
    private Name                           name;
    private List<MultiValuedType<String>>  phoneNumbers;
    private List<MultiValuedType<String>>  emails;
    private List<MultiValuedType<String>>  ims;
    private List<MultiValuedType<String>>  photos;
    private List<MultiValuedType<String>>  groups;
    private List<MultiValuedType<Address>> addresses;
    private List<MultiValuedType<String>>  entitlements;
    private List<MultiValuedType<String>>  roles;
    private List<MultiValuedType<String>>  x509Certificates;

    public User(String user, String encoding) throws UnknownEncoding, InvalidUser {
        super(user, encoding, extensionTypes);
    }

    public User() {
        this("");
    }

    public User(String id) {
        super(id, extensionTypes);
    }

    public String getUser(String encoding) throws UnknownEncoding {
        return super.getResource(encoding, null);
    }

    public String getUser(String encoding, List<String> includeAttributes) throws UnknownEncoding {
        return super.getResource(encoding, includeAttributes);
    }

    @Override
    public void patch(String patch, String encoding) throws UnknownEncoding, InvalidUser, UnknownAttribute {
        User userPatch = new User(patch, encoding);
        super.patch(userPatch);
    }

    public static List<User> getUsers(String users, String encoding) throws UnknownEncoding, InvalidUser {
        List<Resource> userlist = new ArrayList<Resource>();
        List<User> resultlist = new ArrayList<User>();
        // TODO This is ugly
        Resource.getResources(users, encoding, userlist, User.class);
        for (Resource r : userlist) {
            if (r instanceof User) {
                resultlist.add((User) r);
            }
        }
        return resultlist;
    }

    @Override
    public boolean equals(Object otherObj) {
        if (otherObj == this) {
            return true;
        }

        if (!(otherObj instanceof User)) {
            return false;
        }
        User otherScimUser = (User) otherObj;

        return super.equals(otherScimUser);
    }

    @Attribute(name = "userName", handler = StringHandler.class)
    public String getUserName() {
        return this.userName;
    }

    @Attribute(name = "displayName", handler = StringHandler.class)
    public String getDisplayName() {
        return this.displayName;
    }

    @Attribute(name = "nickName", handler = StringHandler.class)
    public String getNickName() {
        return this.nickName;
    }

    @Attribute(name = "profileUrl", handler = StringHandler.class)
    public String getProfileUrl() {
        return this.profileUrl;
    }

    @Attribute(name = "userType", handler = StringHandler.class)
    public String getUserType() {
        return this.userType;
    }

    @Attribute(name = "title", handler = StringHandler.class)
    public String getTitle() {
        return this.title;
    }

    @Attribute(name = "preferredLanguage", handler = StringHandler.class)
    public String getPreferredLanguage() {
        return this.preferredLanguage;
    }

    @Attribute(name = "locale", handler = StringHandler.class)
    public String getLocale() {
        return this.locale;
    }

    @Attribute(name = "password", handler = StringHandler.class)
    public String getPassword() {
        return this.password;
    }

    @Attribute(name = "timezone", handler = StringHandler.class)
    public String getTimezone() {
        return timezone;
    }

    @Attribute(name = "active", handler = BooleanHandler.class)
    public boolean getActive() {
        return active;
    }

    @Attribute(name = "name", handler = ComplexHandler.class, type = Name.class)
    public Name getName() {
        return this.name;
    }

    @Attribute(name = "phoneNumbers", handler = MultiValueHandler.class, internalName = "phoneNumber", internalHandler = StringHandler.class)
    public List<MultiValuedType<String>> getPhoneNumbers() {
        return this.phoneNumbers;
    }

    @Attribute(name = "emails", handler = MultiValueHandler.class, internalName = "email", internalHandler = StringHandler.class)
    public List<MultiValuedType<String>> getEmails() {
        return this.emails;
    }

    @Attribute(name = "ims", handler = MultiValueHandler.class, internalName = "im", internalHandler = StringHandler.class)
    public List<MultiValuedType<String>> getIms() {
        return this.ims;
    }

    @Attribute(name = "photos", handler = MultiValueHandler.class, internalName = "photo", internalHandler = StringHandler.class)
    public List<MultiValuedType<String>> getPhotos() {
        return this.photos;
    }

    @Attribute(name = "groups", handler = MultiValueHandler.class, internalName = "group", internalHandler = StringHandler.class)
    public List<MultiValuedType<String>> getGroups() {
        return this.groups;
    }

    @Attribute(name = "addresses", handler = MultiValueHandler.class, internalName = "address", internalHandler = ComplexHandler.class, internalType = Address.class)
    public List<MultiValuedType<Address>> getAddresses() {
        return this.addresses;
    }

    @Attribute(name = "entitlements", handler = MultiValueHandler.class, internalName = "entitlement", internalHandler = StringHandler.class)
    public List<MultiValuedType<String>> getEntitlements() {
        return this.entitlements;
    }

    @Attribute(name = "roles", handler = MultiValueHandler.class, internalName = "role", internalHandler = StringHandler.class)
    public List<MultiValuedType<String>> getRoles() {
        return this.roles;
    }

    @Attribute(name = "x509Certificates", handler = MultiValueHandler.class, internalName = "x509Certificate", internalHandler = Base64BinaryHandler.class)
    public List<MultiValuedType<String>> getX509Certificates() {
        return this.x509Certificates;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setPhoneNumbers(List<MultiValuedType<String>> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public void setEmails(List<MultiValuedType<String>> emails) {
        this.emails = emails;
    }

    public void setIms(List<MultiValuedType<String>> ims) {
        this.ims = ims;
    }

    public void setPhotos(List<MultiValuedType<String>> photos) {
        this.photos = photos;
    }

    public void setGroups(List<MultiValuedType<String>> groups) {
        this.groups = groups;
    }

    public void setAddresses(List<MultiValuedType<Address>> addresses) {
        this.addresses = addresses;
    }

    public void setEntitlements(List<MultiValuedType<String>> entitlements) {
        this.entitlements = entitlements;
    }

    public void setRoles(List<MultiValuedType<String>> roles) {
        this.roles = roles;
    }

    public void setX509Certificates(List<MultiValuedType<String>> x509Certificates) {
        this.x509Certificates = x509Certificates;
    }

}
