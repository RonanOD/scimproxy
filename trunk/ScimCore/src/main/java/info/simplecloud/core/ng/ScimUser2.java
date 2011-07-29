package info.simplecloud.core.ng;

import info.simplecloud.core.ng.handlers.ComplexHandler;
import info.simplecloud.core.ng.handlers.IHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class ScimUser2 extends ComplexType2 {
    //
    // Static area
    //
    // private static String SCHEMA_LOCATION =
    // System.getProperty("info.simplecloud.core.ng.ScimUser2.SCHEMA_LOCATION");

    private static Map<String, ScimPair<MetaData, Object>> tree = new HashMap<String, ScimPair<MetaData, Object>>();
    static { // Read schema

    }

    private static MetaData metaData = new MetaData(ComplexType2.class, ComplexHandler.INSTANCE, null, "", null, null);

    //
    // Actual user object
    //
    public ScimUser2(String user, String encoding) throws Exception {
        super(tree);
        
        if ("json".equalsIgnoreCase(encoding)) {
            try {
                IHandler handler = metaData.getHandler();
                handler.decode(new JSONObject(user), this, metaData.getInternalMetaData());
            } catch (JSONException e) {
                throw new Exception("Invalid user object", e);
            }
        } else if ("xml".equalsIgnoreCase(encoding)) {
            
        }

        throw new Exception("Unknown encoding, " + encoding);
    }

    public String getUser(String encoding, List<String> includeAttributes) throws Exception {
        if ("json".equalsIgnoreCase(encoding)) {
            try {
                IHandler handler = metaData.getHandler();
                return ((JSONObject)handler.encode(this, metaData.getInternalMetaData(), includeAttributes)).toString(2);
            } catch (JSONException e) {
                throw new RuntimeException("Invalid user object", e);
            }
        } else if ("xml".equalsIgnoreCase(encoding)) {
            throw new RuntimeException("XML getUser is not implemented");
        }
        
        throw new Exception("Unknown encoding, " + encoding);
    }

    public String getUser(String encoding) throws Exception {
        return this.getUser(encoding, null);
    }

    public void patch(String patch, String encoding) throws Exception {
        ScimUser2 patchObject = new ScimUser2(patch, encoding);

        ComplexType2 meta = patchObject.getAttribute("meta", ComplexType2.class);
        List<String> deleteAttributes = meta.getAttribute("", List.class);

        for (String attribute : deleteAttributes) {
            this.setAttribute(attribute, null);
        }

        IHandler handler = metaData.getHandler();
        handler.merge(patchObject, this);
        
    }

}
