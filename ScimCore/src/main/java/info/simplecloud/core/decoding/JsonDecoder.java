package info.simplecloud.core.decoding;

import info.simplecloud.core.Address;
import info.simplecloud.core.Meta;
import info.simplecloud.core.Name;
import info.simplecloud.core.PluralType;
import info.simplecloud.core.ScimUser;
import info.simplecloud.core.execeptions.InvalidUser;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonDecoder implements IUserDecoder {
    private static String[] names = { "json", "JSON" };

    @Override
    public void addMe(Map<String, IUserDecoder> decoders) {
        for (String name : names) {
            decoders.put(name, this);
        }
    }

    @Override
    public void decode(String user, ScimUser scimUser) throws InvalidUser {
        try {
            JSONObject scimUserJson = new JSONObject(user);

            for (String id : ScimUser.simple) {
                
                if(id.equals(ScimUser.ATTRIBUTE_UTC_OFFSET)) {
                    scimUser.setUtcOffset(new GregorianCalendar()); // TODO parse time or something
                }
                readAttribute(scimUserJson, id, scimUser);
            }

            for (String id : ScimUser.plural) {
                if(id.equals(ScimUser.ATTRIBUTE_ADDRESSES)) {
                    continue;
                }
                readPluralAttribute(scimUserJson, id, scimUser);
            }

            try {
                List<PluralType<Address>> list = new ArrayList<PluralType<Address>>();
                JSONArray array = scimUserJson.getJSONArray(ScimUser.ATTRIBUTE_ADDRESSES);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);

                    Address address = new Address();
                    for (String attributeId : Address.simple) {
                        try {
                            address.setAttribute(attributeId, obj.getString(attributeId));
                        } catch (JSONException e) {
                            // Ignore, attribute not found
                        }
                    }

                    list.add(new PluralType<Address>(address, obj.getString("type"), obj.getBoolean("primary")));
                }
                scimUser.setAttribute(ScimUser.ATTRIBUTE_ADDRESSES, list);
            } catch (JSONException e) {
                // Ignore, plural attribute not found
            }

            try {
                JSONObject obj = scimUserJson.getJSONObject(ScimUser.ATTRIBUTE_NAME);
                Name name = new Name();
                for (String attributeId : Name.simple) {
                    try {
                        name.setAttribute(attributeId, obj.getString(attributeId));
                    } catch (JSONException e) {
                        // Ignore, attribute not found
                    }
                }
                scimUser.setAttribute(ScimUser.ATTRIBUTE_NAME, name);
            } catch (JSONException e) {
                // Ignore, name attribute not found
            }
            
            try {
                JSONObject obj = scimUserJson.getJSONObject(ScimUser.ATTRIBUTE_META);
                Meta meta = new Meta();
                for (String attributeId : Name.simple) {
                    try {
                        meta.setAttribute(attributeId, obj.getString(attributeId));
                    } catch (JSONException e) {
                        // Ignore, attribute not found
                    }
                }
                scimUser.setAttribute(ScimUser.ATTRIBUTE_META, meta);
            } catch (JSONException e) {
                // Ignore, meta attribute not found
            }
        } catch (JSONException e) {
            throw new InvalidUser("Failed to parse user", e);
        }
    }

    private void readPluralAttribute(JSONObject scimUserJson, String id, ScimUser scimUser) {
        try {
            List<PluralType<String>> list = new ArrayList<PluralType<String>>();
            JSONArray array = scimUserJson.getJSONArray(id);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                boolean primary;
                try {
                    primary = obj.getBoolean("primary");
                } catch (JSONException e) {
                    primary = false;
                }
                String type;
                try {
                    type = obj.getString("type");
                } catch (JSONException e) {
                    type = "";
                }
                list.add(new PluralType<String>(obj.getString("value"), type, primary));
            }
            scimUser.setAttribute(id, list);
        } catch (JSONException e) {
            // Ignore, plural attribute not found
        }
    }

    private static void readAttribute(JSONObject scimUserJson, String id, ScimUser scimUser) {
        try {
            String attribute = scimUserJson.getString(id);
            scimUser.setAttribute(id, attribute);
        } catch (JSONException e) {
            // Ignore, attribute not found
        }
    }

}
