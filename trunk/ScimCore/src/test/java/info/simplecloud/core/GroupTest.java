package info.simplecloud.core;

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
    public void decode() throws Exception {
        // TODO implement test
    }

    @Test
    public void patch() {
        // TODO implement test
    }

    @Test
    public void sort() {
        // TODO implement test
    }

}
