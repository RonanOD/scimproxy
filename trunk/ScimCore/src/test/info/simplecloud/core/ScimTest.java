package info.simplecloud.core;

import java.util.GregorianCalendar;

import x0.scimSchemasCore1.AddressType;
import x0.scimSchemasCore1.AddressesType;
import x0.scimSchemasCore1.EmailType;
import x0.scimSchemasCore1.EmailsType;
import x0.scimSchemasCore1.GroupType;
import x0.scimSchemasCore1.GroupsType;
import x0.scimSchemasCore1.ImType;
import x0.scimSchemasCore1.ImsType;
import x0.scimSchemasCore1.MetaType;
import x0.scimSchemasCore1.NameType;
import x0.scimSchemasCore1.PhoneNumberType;
import x0.scimSchemasCore1.PhoneNumbersType;
import x0.scimSchemasCore1.PhotoType;
import x0.scimSchemasCore1.PhotosType;
import x0.scimSchemasCore1.ScimUserDocument;
import x0.scimSchemasCore1.ScimUserType;

public class ScimTest {

    public static void main(String[] args) {

        System.out.println("------------ Full User Start ------------");
        System.out.println(createFullUser());
        System.out.println("------------- Full User end -------------");

        System.out.println("------------ Minimal User Start ------------");
        System.out.println(createMinimalUser());
        System.out.println("------------- Minimal User end -------------");
    }

    private static String createMinimalUser() {
        ScimUserType scimUser = ScimUserType.Factory.newInstance();
        scimUser.setId("005D0000001Az1u");
        scimUser.setUserName("bjensen@example.com");

        ScimUserDocument doc = ScimUserDocument.Factory.newInstance();
        doc.setScimUser(scimUser);
        return doc.xmlText();
    }

    private static String createFullUser() {
        ScimUserType scimUser = ScimUserType.Factory.newInstance();
        scimUser.setId("005D0000001Az1u");
        scimUser.setExternalId("701984");
        scimUser.setUserName("bjensen@example.com");
        NameType name = scimUser.addNewName();
        name.setFormatted("Ms. Babs J Jensen III");
        name.setFamilyName("Jensen");
        name.setGivenName("Barbara");
        name.setMiddleName("Jane");
        name.setHonorificPrefix("Ms.");
        name.setHonorificSuffix("III");
        scimUser.setDisplayName("Babs Jensen");
        scimUser.setNickName("Babs");
        scimUser.setProfileUrl("https://login.example.com/bjensen");
        EmailsType emails = scimUser.addNewEmails();
        EmailType emailWork = emails.addNewEmail();
        emailWork.setPrimary(true);
        emailWork.setType("work");
        emailWork.setStringValue("bjensen@example.com");
        EmailType emailHome = emails.addNewEmail();
        emailHome.setType("home");
        emailHome.setStringValue("babs@jensen.com");
        AddressesType addresses = scimUser.addNewAddresses();
        AddressType workAddress = addresses.addNewAddress();
        workAddress.setType("work");
        workAddress.setPrimary(true);
        workAddress.setFormatted("100 Universal City Plaza\nHollywood, CA 91608 USA");
        workAddress.setStreetAddress("100 Universal City Plaza");
        workAddress.setLocality("Hollywood");
        workAddress.setPostalCode("91608");
        workAddress.setCountry("USA");
        AddressType homeAddress = addresses.addNewAddress();
        homeAddress.setType("home");
        homeAddress.setFormatted("456 Hollywood Blvd\nHollywood, CA 91608 USA");
        homeAddress.setStreetAddress("456 Hollywood Blvd");
        homeAddress.setLocality("San Francisco");
        homeAddress.setPostalCode("91608");
        homeAddress.setCountry("USA");
        homeAddress.setRegion("CA");
        PhoneNumbersType phoneNumbers = scimUser.addNewPhoneNumbers();
        PhoneNumberType workPhone = phoneNumbers.addNewPhoneNumber();
        workPhone.setType("work");
        workPhone.setStringValue("800-864-8377");
        PhoneNumberType mobilePhone = phoneNumbers.addNewPhoneNumber();
        mobilePhone.setType("mobile");
        mobilePhone.setStringValue("818-123-4567");
        ImsType ims = scimUser.addNewIms();
        ImType aimIm = ims.addNewIm();
        aimIm.setType("aim");
        aimIm.setStringValue("someaimhandle");
        PhotosType photos = scimUser.addNewPhotos();
        PhotoType photo = photos.addNewPhoto();
        photo.setPrimary(true);
        photo.setType("photo");
        PhotoType thumbnail = photos.addNewPhoto();
        thumbnail.setType("thumbnail");
        scimUser.setEmployeeNumber("701984");
        scimUser.setUserType("Employee");
        scimUser.setTitle("Tour Guide");
        scimUser.setManager("Mandy Pepperidge");
        scimUser.setPreferredLanguage("en_US");
        scimUser.setLocale("en_US");
        scimUser.setUtcOffset(new GregorianCalendar());
        scimUser.setCostCenter("4130");
        scimUser.setOrganization("Universal Studios");
        scimUser.setDivision("Theme Park");
        scimUser.setDepartment("Tour Operations");
        GroupsType groups = scimUser.addNewGroups();
        GroupType group = groups.addNewGroup();
        group.setPrimary(true);
        group.setStringValue("Tour Guides");
        group = groups.addNewGroup();
        group.setStringValue("Employees");
        group = groups.addNewGroup();
        group.setStringValue("US Employees");
        MetaType meta = scimUser.addNewMeta();
        meta.setCreated(new GregorianCalendar());
        meta.setLastModified(new GregorianCalendar());

        ScimUserDocument doc = ScimUserDocument.Factory.newInstance();
        doc.setScimUser(scimUser);
        return doc.xmlText();
    }
}