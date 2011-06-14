package info.simplecloud.core.encoding;

import info.simplecloud.core.Address;
import info.simplecloud.core.ComplexType;
import info.simplecloud.core.Meta;
import info.simplecloud.core.Name;
import info.simplecloud.core.PluralType;
import info.simplecloud.core.ScimUser;
import info.simplecloud.core.execeptions.EncodingFailed;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonEncoder implements IUserEncoder {
    private static String[] names = { "json", "JSON" };

    @Override
    public void addMe(Map<String, IUserEncoder> encoders) {
        for (String name : names) {
            encoders.put(name, this);
        }
    }

    @Override
    public String encode(ScimUser scimUser) throws EncodingFailed {
        try {
            return internalEncode(scimUser).toString(2);
            // TODO change to non pretty-print or check if debug mode
        } catch (JSONException e) {
            throw new EncodingFailed("Failed to encode JSON", e);
        }
    }

    @Override
    public String encode(List<ScimUser> scimUsers) throws EncodingFailed {
        try {
            JSONObject result = new JSONObject();

            if (scimUsers == null) {
                scimUsers = new ArrayList<ScimUser>();
            }
            result.put("totalResults", scimUsers.size());

            // TODO: Should this be done in core? Return the JSON list of more
            // resporces when you send an List into encode method?
            JSONArray users = new JSONArray();

            for (ScimUser scimUser : scimUsers) {
                users.put(internalEncode(scimUser));
            }

            result.put("entry", users);
            // TODO: SPEC: REST: Return meta location. Should location be sent
            // to this method or always include it in storage for each user?

            return result.toString(2);
            // TODO change to non pretty-print or check if debug mode
        } catch (JSONException e) {
            throw new EncodingFailed("Failed to build response user set", e);
        }
    }

    private JSONObject internalEncode(ScimUser scimUser) throws JSONException {
        JSONObject result = new JSONObject();

        for (String id : scimUser.getSimple()) {
            append(result, scimUser, id);
        }

        Name name = scimUser.getName();
        if (name != null) {
            JSONObject jsonName = new JSONObject();
            for (String id : name.getSimple()) {
                append(jsonName, name, id);
            }

            result.put(ScimUser.ATTRIBUTE_NAME, jsonName);
        }

        Meta meta = scimUser.getMeta();
        if (meta != null) {
            JSONObject jsonMeta = new JSONObject();
            for (String id : meta.getSimple()) {
                append(jsonMeta, meta, id);
            }

            result.put(ScimUser.ATTRIBUTE_META, jsonMeta);
        }

        appendPlural(result, scimUser.getIms(), ScimUser.ATTRIBUTE_IMS);
        appendPlural(result, scimUser.getEmails(), ScimUser.ATTRIBUTE_EMAILS);
        appendPlural(result, scimUser.getPhotos(), ScimUser.ATTRIBUTE_PHOTOS);
        appendPlural(result, scimUser.getGroups(), ScimUser.ATTRIBUTE_GROUPS);
        appendPlural(result, scimUser.getGroups(), ScimUser.ATTRIBUTE_PHONE_NUMBERS);

        List<PluralType<Address>> list = scimUser.getAddresses();
        if (list != null) {
            JSONArray pluralArray = new JSONArray();
            for (PluralType<Address> item : list) {
                JSONObject jsonItem = new JSONObject();
                jsonItem.put("type", item.getType());
                jsonItem.put("primary", item.getPrimary());
                for (String id : Address.simple) {
                    append(jsonItem, item.getValue(), id);
                }
                pluralArray.put(jsonItem);
            }
            result.put(ScimUser.ATTRIBUTE_ADDRESSES, pluralArray);
        }

        return result;
    }

    private static void appendPlural(JSONObject result, List<PluralType<String>> list, String id) throws JSONException {
        if (list == null) {
            return;
        }

        JSONArray pluralArray = new JSONArray();

        for (PluralType<String> item : list) {
            JSONObject jsonItem = new JSONObject();

            jsonItem.put("value", item.getValue());
            jsonItem.put("type", item.getType());
            jsonItem.put("primary", item.getPrimary());

            pluralArray.put(jsonItem);
        }

        result.put(id, pluralArray);
    }

    private static void append(JSONObject appendTo, ComplexType complexType, String id) throws JSONException {
        Object appendie = complexType.getAttribute(id);
        if (appendie != null) {
            appendTo.put(id, appendie.toString());
        }
    }

}
