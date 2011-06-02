package info.simplecloud.core.decoding;

import info.simplecloud.core.ScimUser;
import info.simplecloud.core.execeptions.InvalidUserException;

import java.util.Map;

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
    public void decode(String user, ScimUser scimUser) throws InvalidUserException {
        try {
            JSONObject scimUserJson = new JSONObject(user);
            scimUserJson.get("id");
            // TODO put all elements in to scimUser with getters and setters
        } catch (JSONException e) {
            throw new InvalidUserException("Failed to parse user", e);
        }
    }
}
