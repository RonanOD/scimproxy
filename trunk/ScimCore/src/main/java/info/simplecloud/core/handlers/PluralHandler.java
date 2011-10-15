package info.simplecloud.core.handlers;

import info.simplecloud.core.MetaData;
import info.simplecloud.core.coding.ReflectionHelper;
import info.simplecloud.core.coding.decode.IDecodeHandler;
import info.simplecloud.core.coding.encode.IEncodeHandler;
import info.simplecloud.core.exceptions.InvalidUser;
import info.simplecloud.core.merging.IMerger;
import info.simplecloud.core.types.PluralType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PluralHandler implements IDecodeHandler, IEncodeHandler, IMerger {

    @Override
    public Object decode(Object jsonData, Object me, MetaData internalMetaData) throws InvalidUser {
        try {
            JSONArray jsonArray = (JSONArray) jsonData;
            List<PluralType> result = new ArrayList<PluralType>();
            int nrPrimary = 0;

            IDecodeHandler decoder = internalMetaData.getDecoder();
            Object value = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject internalObj = jsonArray.getJSONObject(i);
                try {
                    value = internalObj.get("value");
                } catch (JSONException e) {
                    value = internalObj;
                }
                Object decodedValue = decoder.decode(value, internalMetaData.newInstance(), internalMetaData.getInternalMetaData());
                String type = (String) getOptional(internalObj, "type");
                String display = (String) getOptional(internalObj, "display");
                Boolean primary = (Boolean) getOptional(internalObj, "primary");
                primary = (primary == null ? false : primary);
                String operation = (String) getOptional(internalObj, "operation");

                nrPrimary += (primary ? 1 : 0);

                result.add(new PluralType(decodedValue, type, display, primary, operation));
            }

            if (nrPrimary > 1) {
                throw new InvalidUser("Only one primary value is allowed");
            }

            return result;

        } catch (JSONException e) {
            throw new RuntimeException("Invalid user object", e);
        }
    }

    @Override
    public Object decodeXml(Object value, Object me, MetaData internalMetaData) throws InvalidUser {
        List<PluralType> result = new ArrayList<PluralType>();

        String name = internalMetaData.getName();
        String getterName = "get";
        getterName += name.substring(0, 1).toUpperCase();
        getterName += name.substring(1);
        getterName += "Array";

        try {
            Method getter = value.getClass().getMethod(getterName, new Class<?>[] {});
            Object[] array = (Object[]) getter.invoke(value, new Object[] {});
            int nrPrimary = 0;

            for (Object obj : array) {
                Object internalValue = null;
                try {
                    // Look and see if this is a simple or complex plural
                    Method getValueMethod = obj.getClass().getMethod("getValue");
                    internalValue = getValueMethod.invoke(obj);
                } catch (NoSuchMethodException e) {
                    // This is okay, we have a plural complex

                    IDecodeHandler decoder = internalMetaData.getDecoder();
                    internalValue = decoder.decodeXml(obj, internalMetaData.newInstance(), internalMetaData.getInternalMetaData());
                }

                String type = (String) this.readXml(obj, "getType");
                String display = (String) this.readXml(obj, "getDisplay");
                Boolean primary = (Boolean) this.readXml(obj, "getPrimary");
                primary = (primary == null ? false : primary);
                String operation = (String) this.readXml(obj, "getOperation");

                nrPrimary += (primary ? 1 : 0);
                result.add(new PluralType(internalValue, type, display, primary, operation));
            }

            if (nrPrimary > 1) {
                throw new InvalidUser("Only one primary value is allowed");
            }
        } catch (SecurityException e) {
            throw new RuntimeException("Internal error, decoding plural", e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Internal error, decoding plural", e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Internal error, decoding plural", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Internal error, decoding plural", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Internal error, decoding plural", e);
        }

        return result;
    }

    @Override
    public Object encode(Object me, List<String> includeAttributes, MetaData internalMetaData) {
        List<PluralType> plural = (List<PluralType>) me;
        JSONArray result = new JSONArray();
        for (PluralType singular : plural) {
            JSONObject jsonObject = new JSONObject();

            IEncodeHandler encoder = internalMetaData.getEncoder();
            Object value = encoder.encode(singular.getValue(), includeAttributes, null);

            try {
                jsonObject.put("value", value);
                jsonObject.put("type", singular.getType());
                jsonObject.put("display", singular.getDisplay());
                if (singular.isPrimary()) {
                    jsonObject.put("primary", singular.isPrimary());
                }
                if (singular.isDelete()) {
                    jsonObject.put("operation", "delete");
                }

                result.put(jsonObject);
            } catch (JSONException e) {
                throw new RuntimeException("Internal error, encoding plural type", e);
            }
        }

        return result;
    }

    @Override
    public Object encodeXml(Object me, List<String> includeAttributes, MetaData internalMetaData, Object xmlObject) {
        List<PluralType> plural = (List<PluralType>) me;

        try {
            for (PluralType singular : plural) {
                Object value = singular.getValue();
                IEncodeHandler encoder = internalMetaData.getEncoder();
                Object internalXmlObject;
                internalXmlObject = HandlerHelper.createInternalXmlObject(xmlObject, internalMetaData.getName());

                Object encodedValue = encoder.encodeXml(value, includeAttributes, internalMetaData, internalXmlObject);
                try {
                    Method setValueMethod = internalXmlObject.getClass().getMethod("setValue", String.class);
                    setValueMethod.invoke(internalXmlObject, encodedValue);
                } catch (NoSuchMethodException e) {
                    // This is okay, value has been assigned internally
                }
                this.writeXml(internalXmlObject, singular.getType(), "setType");
                this.writeXml(internalXmlObject, singular.getDisplay(), "setDisplay");
                if (singular.isPrimary()) {
                    this.writeXml(internalXmlObject, singular.isPrimary(), "setPrimary");
                }
                if (singular.isDelete()) {
                    this.writeXml(internalXmlObject, "delete", "setOperation");
                }
            }
            return xmlObject;
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Internal error, encoding plural xml", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Internal error, encoding plural xml", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Internal error, encoding plural xml", e);
        } catch (SecurityException e) {
            throw new RuntimeException("Internal error, encoding plural xml", e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Internal error, encoding plural xml", e);
        }
    }

    @Override
    public Object merge(Object from, Object to) {
        List<PluralType> toList = (List<PluralType>) to;
        List<PluralType> fromList = (List<PluralType>) from;

        for (PluralType singular : fromList) {
            if (singular.isDelete()) {
                toList.remove(singular);
            } else if (singular.isPrimary()) {
                toList.remove(singular);
                clearPrimary(toList);
                toList.add(singular);
            } else if (toList.contains(singular)) {
                toList.remove(singular);
                toList.add(singular);
            } else {
                toList.add(singular);
            }
        }
        return to;
    }

    private void clearPrimary(List<PluralType> list) {
        for (PluralType singular : list) {
            singular.setPrimary(false);
        }
    }

    private Object getOptional(JSONObject jsonObject, String name) {
        try {
            return jsonObject.get(name);
        } catch (JSONException e) {
            return null;
        }
    }

    private Object readXml(Object obj, String method) throws SecurityException, NoSuchMethodException, IllegalArgumentException,
            IllegalAccessException, InvocationTargetException {
        Method getValueMethod = obj.getClass().getMethod(method, new Class[] {});
        return getValueMethod.invoke(obj, new Object[] {});
    }

    private void writeXml(Object internalXmlObject, Object obj, String methodName) throws SecurityException, NoSuchMethodException,
            IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        Method getType = ReflectionHelper.getMethod(methodName, internalXmlObject.getClass());
        getType.invoke(internalXmlObject, obj);
    }

}
