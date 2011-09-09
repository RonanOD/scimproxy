package info.simplecloud.core;

import info.simplecloud.core.exceptions.InvalidUser;
import info.simplecloud.core.exceptions.UnknownAttribute;
import info.simplecloud.core.exceptions.UnknownEncoding;
import info.simplecloud.core.types.PluralType;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

public class GroupTest {

    @Test
    public void create() throws Exception {
        Group group = new Group("ABC123");
        group.setId("123ABC");
        group.setDisplayName("Super group");
        List<PluralType<String>> members = new ArrayList<PluralType<String>>();
        members.add(new PluralType<String>("User1", null, false, false));
        members.add(new PluralType<String>("User2", null, false, false));
        members.add(new PluralType<String>("User3", null, false, false));
        group.setMembers(members);

        Assert.assertEquals("123ABC", group.getId());
        Assert.assertEquals("Super group", group.getDisplayName());
        Assert.assertTrue(group.getMembers().contains(new PluralType<String>("User1", null, false, false)));
        Assert.assertTrue(group.getMembers().contains(new PluralType<String>("User2", null, false, false)));
        Assert.assertTrue(group.getMembers().contains(new PluralType<String>("User3", null, false, false)));

        group.setAttribute("id", "DEF456");
        group.setAttribute("displayName", "Super Mega Group");
        members = new ArrayList<PluralType<String>>();
        members.add(new PluralType<String>("User4", null, false, false));
        members.add(new PluralType<String>("User5", null, false, false));
        members.add(new PluralType<String>("User6", null, false, false));
        group.setAttribute("members", members);

        Assert.assertEquals("DEF456", group.getAttribute("id"));
        Assert.assertEquals("Super Mega Group", group.getAttribute("displayName"));
        members = group.getAttribute("members");
        Assert.assertTrue(members.contains(new PluralType<String>("User4", null, false, false)));
        Assert.assertTrue(members.contains(new PluralType<String>("User5", null, false, false)));
        Assert.assertTrue(members.contains(new PluralType<String>("User6", null, false, false)));

        group.toString();
    }

    @Test
    public void encode() throws Exception {
        Group group = new Group("ABC123");
        group.setId("123ABC");
        group.setDisplayName("Super group");
        List<PluralType<String>> members = new ArrayList<PluralType<String>>();
        members.add(new PluralType<String>("User1", null, false, false));
        members.add(new PluralType<String>("User2", null, false, false));
        members.add(new PluralType<String>("User3", null, false, false));
        group.setMembers(members);

        String json = group.getGroup(Resource.ENCODING_JSON);
        String xml = group.getGroup(Resource.ENCODING_XML);

        Assert.assertTrue(json.contains("User1"));
        Assert.assertTrue(json.contains("User2"));
        Assert.assertTrue(json.contains("User3"));
        Assert.assertTrue(json.contains("123ABC"));
        Assert.assertTrue(json.contains("Super group"));

        Assert.assertTrue(xml.contains("User1"));
        Assert.assertTrue(xml.contains("User2"));
        Assert.assertTrue(xml.contains("User3"));
        Assert.assertTrue(xml.contains("123ABC"));
        Assert.assertTrue(xml.contains("Super group"));
    }

    @Test
    public void encodePartial() throws Exception {
        Group group = new Group("ABC123");
        group.setId("123ABC");
        group.setDisplayName("Super group");
        List<PluralType<String>> members = new ArrayList<PluralType<String>>();
        members.add(new PluralType<String>("User1", null, false, false));
        members.add(new PluralType<String>("User2", null, false, false));
        members.add(new PluralType<String>("User3", null, false, false));
        group.setMembers(members);

        List<String> includeAttributes = new ArrayList<String>();
        includeAttributes.add("displayName");
        String json = group.getGroup(Resource.ENCODING_JSON, includeAttributes);
        String xml = group.getGroup(Resource.ENCODING_XML, includeAttributes);

        Assert.assertFalse(json.contains("User1"));
        Assert.assertFalse(json.contains("User2"));
        Assert.assertFalse(json.contains("User3"));
        Assert.assertTrue(json.contains("123ABC"));
        Assert.assertTrue(json.contains("Super group"));

        Assert.assertFalse(xml.contains("User1"));
        Assert.assertFalse(xml.contains("User2"));
        Assert.assertFalse(xml.contains("User3"));
        Assert.assertTrue(xml.contains("123ABC"));
        Assert.assertTrue(xml.contains("Super group"));
    }

    @Test
    public void decodeXml() throws Exception {
        String groupXml = "<urn:Group xmlns:urn=\"urn:scim:schemas:core:1.0\"><id>123ABC</id><displayName>Super group</displayName><members><member><value>User1</value><primary>false</primary><type xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/></member><member><value>User2</value><primary>false</primary><type xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/></member><member><value>User3</value><primary>false</primary><type xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/></member></members></urn:Group>";
        Group group = new Group(groupXml, Resource.ENCODING_XML);

        Assert.assertEquals("123ABC", group.getAttribute("id"));
        Assert.assertEquals("Super group", group.getAttribute("displayName"));
        List<PluralType<String>> members = group.getAttribute("members");
        Assert.assertTrue(members.contains(new PluralType<String>("User1", null, false, false)));
        Assert.assertTrue(members.contains(new PluralType<String>("User2", null, false, false)));
        Assert.assertTrue(members.contains(new PluralType<String>("User3", null, false, false)));
    }

    @Test
    public void decodeJson() throws Exception {
        String groupJson = "{\"id\": \"123ABC\",\"schemas\": [\"urn:scim:schemas:core:1.0\"],\"displayName\": \"Super group\", \"members\": [{\"value\": \"User1\"},{\"value\": \"User2\"},{\"value\": \"User3\"}]}";
        Group group = new Group(groupJson, Resource.ENCODING_JSON);

        Assert.assertEquals("123ABC", group.getAttribute("id"));
        Assert.assertEquals("Super group", group.getAttribute("displayName"));
        List<PluralType<String>> members = group.getAttribute("members");
        Assert.assertTrue(members.contains(new PluralType<String>("User1", null, false, false)));
        Assert.assertTrue(members.contains(new PluralType<String>("User2", null, false, false)));
        Assert.assertTrue(members.contains(new PluralType<String>("User3", null, false, false)));
    }

    @Test
    public void jsonPatch() throws UnknownEncoding, InvalidUser, UnknownAttribute {
        // change displayName
        Group group = new Group("ABC123");
        group.setDisplayName("Super Group");
        String patch = "{\"displayName\":\"Super Mega Group\"}";
        group.patch(patch, Resource.ENCODING_JSON);
        Assert.assertEquals("Super Mega Group", group.getDisplayName());
        // delete displayName
        patch = "{\"meta\":{\"attributes\":[\"displayName\"]}}";
        group.patch(patch, Resource.ENCODING_JSON);
        Assert.assertNull(group.getDisplayName());
        // delete members
        List<PluralType<String>> members = new ArrayList<PluralType<String>>();
        members.add(new PluralType<String>("user1", "user", "Adam", false));
        members.add(new PluralType<String>("user2", "user", "Bertil", false));
        members.add(new PluralType<String>("user3", "user", "Cesar", false));
        group.setMembers(members);
        patch = "{\"meta\":{\"attributes\":[\"members\"]}}";
        group.patch(patch, Resource.ENCODING_JSON);
        Assert.assertNull(group.getMembers());
        // delete one member
        group.setMembers(members);
        patch = "{\"members\":[{\"value\":\"user1\", \"delete\":true}]}";
        group.patch(patch, Resource.ENCODING_JSON);
        members = group.getMembers();
        Assert.assertFalse(members.contains(new PluralType<String>("user1", "user", "Adam", false)));
        Assert.assertTrue(members.contains(new PluralType<String>("user2", "user", "Bertil", false)));
        Assert.assertTrue(members.contains(new PluralType<String>("user3", "user", "Cesar", false)));
        // add primary
        patch = "{\"members\":[{\"value\":\"user1\", \"primary\":true, \"type\":\"user\"}]}";
        group.patch(patch, Resource.ENCODING_JSON);
        members = group.getMembers();
        Assert.assertTrue(members.contains(new PluralType<String>("user1", "user", "Adam", false)));
        Assert.assertTrue(members.contains(new PluralType<String>("user2", "user", "Bertil", false)));
        Assert.assertTrue(members.contains(new PluralType<String>("user3", "user", "Cesar", false)));
        for(PluralType<String> singular: members){
            if("user1".equals(singular.getValue())){
                Assert.assertTrue(singular.isPrimary());
            } else {
                Assert.assertFalse(singular.isPrimary());
            }
        }
        // change primary member
        patch = "{\"members\":[{\"value\":\"user2\", \"primary\":true}]}";
        group.patch(patch, Resource.ENCODING_JSON);
        members = group.getMembers();
        Assert.assertTrue(members.contains(new PluralType<String>("user1", "user", "Adam", false)));
        Assert.assertTrue(members.contains(new PluralType<String>("user2", "user", "Bertil", false)));
        Assert.assertTrue(members.contains(new PluralType<String>("user3", "user", "Cesar", false)));
        for(PluralType<String> singular: members){
            if("user2".equals(singular.getValue())){
                Assert.assertTrue(singular.isPrimary());
            } else {
                Assert.assertFalse(singular.isPrimary());
            }
        }
        // update one member
        patch = "{\"members\":[{\"value\":\"user2\", \"type\":\"Super User\"}]}";
        group.patch(patch, Resource.ENCODING_JSON);
        members = group.getMembers();
        Assert.assertTrue(members.contains(new PluralType<String>("user1", "user", "Adam", false)));
        Assert.assertTrue(members.contains(new PluralType<String>("user2", "Super User", "Bertil", false)));
        Assert.assertTrue(members.contains(new PluralType<String>("user3", "user", "Cesar", false)));
        for(PluralType<String> singular: members){
            if("user2".equals(singular.getValue())){
                Assert.assertEquals("Super User", singular.getType());
            } else {
                Assert.assertEquals("user", singular.getType());
            }
        }
        // change members
        patch = "{\"members\":[{\"value\":\"user4\"},{\"value\":\"user5\"},{\"value\":\"user6\"}], \"meta\":{\"attributes\":[\"members\"]}}";
        group.patch(patch, Resource.ENCODING_JSON);
        members = group.getMembers();
        Assert.assertTrue(members.contains(new PluralType<String>("user4", null, null, false)));
        Assert.assertTrue(members.contains(new PluralType<String>("user5", null, null, false)));
        Assert.assertTrue(members.contains(new PluralType<String>("user6", null, null, false)));
    }

    @Test
    public void xmlPatch() {
        // TODO implement test

        // delete displayName
        // delete members
        // delete one member
        // change primary member
        // update one member
    }

    @Test
    public void sort() {
        // TODO implement test

        // sort on displayName ascending/descending
        // sort on member ascending/descending with primary
        // sort on member ascending/descending without primary
    }
}
