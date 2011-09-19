package info.simplecloud.core;

import info.simplecloud.core.annotations.Attribute;
import info.simplecloud.core.annotations.Extension;
import info.simplecloud.core.coding.ReflectionHelper;
import info.simplecloud.core.coding.decode.IResourceDecoder;
import info.simplecloud.core.coding.decode.JsonDecoder;
import info.simplecloud.core.coding.decode.XmlDecoder;
import info.simplecloud.core.coding.encode.IUserEncoder;
import info.simplecloud.core.coding.encode.JsonEncoder;
import info.simplecloud.core.coding.encode.XmlEncoder;
import info.simplecloud.core.exceptions.InvalidUser;
import info.simplecloud.core.exceptions.PatchingFailed;
import info.simplecloud.core.exceptions.UnknownAttribute;
import info.simplecloud.core.exceptions.UnknownEncoding;
import info.simplecloud.core.exceptions.UnknownExtension;
import info.simplecloud.core.handlers.ComplexHandler;
import info.simplecloud.core.handlers.ListHandler;
import info.simplecloud.core.handlers.StringHandler;
import info.simplecloud.core.types.ComplexType;
import info.simplecloud.core.types.Meta;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Resource extends ComplexType {

	public static final String ENCODING_JSON = "json";
	public static final String ENCODING_XML = "xml";

	private static Map<String, IUserEncoder> encoders = new HashMap<String, IUserEncoder>();
	private static Map<String, IResourceDecoder> decoders = new HashMap<String, IResourceDecoder>();

	static {
		encoders.put(ENCODING_XML, new XmlEncoder());
		encoders.put(ENCODING_JSON, new JsonEncoder());
		decoders.put(ENCODING_XML, new XmlDecoder());
		decoders.put(ENCODING_JSON, new JsonDecoder());
	}

	private static String[] mandatoryAttributes = new String[] { "id",
			"schemas" };

	private List<Object> extensions = new ArrayList<Object>();
	private String id;
	private Meta meta;

	protected Resource(String resource, String encoding,
			List<Class<?>> extensionTypes) throws UnknownEncoding, InvalidUser {
		this.addExtensions(extensionTypes);

		IResourceDecoder decoder = decoders.get(encoding.toLowerCase());
		if (decoder == null) {
			throw new UnknownEncoding(encoding);
		}

		decoder.decode(resource, this);
	}

	protected Resource(String id, List<Class<?>> extensionTypes) {
		this.addExtensions(extensionTypes);

		this.setId(id);
	}

	public String getResource(String encoding, List<String> attributes)
			throws UnknownEncoding {
		IUserEncoder encoder = encoders.get(encoding.toLowerCase());
		if (encoder == null) {
			throw new UnknownEncoding(encoding);
		}

		if (attributes != null) {
			for (String mandatory : mandatoryAttributes) {
				if (!attributes.contains(mandatory)) {
					attributes.add(mandatory);
				}
			}
		}
		// TODO check if resource has mandatory data

		return encoder.encode(this, attributes);

	}

	public static List<Resource> getResources(String users, String encoding,
			List<Resource> resources, Class<?> type) throws UnknownEncoding,
			InvalidUser {
		IResourceDecoder decoder = decoders.get(encoding.toLowerCase());
		if (decoder == null) {
			throw new UnknownEncoding(encoding);
		}

		decoder.decode(users, resources, type);
		return resources;
	}

	public abstract void patch(String patch, String encoding)
			throws UnknownEncoding, InvalidUser, UnknownAttribute;

	protected void patch(Resource patch) throws UnknownEncoding, InvalidUser,
			UnknownAttribute {

		Meta meta = patch.getMeta();
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
					Object patchObj = patch.getExtension(extension.getClass());
					Method patchMethod = patchObj.getClass().getMethod(
							method.getName(), method.getParameterTypes());
					Object patchData = patchMethod.invoke(patchObj);

					String setter = "s" + method.getName().substring(1);
					ReflectionHelper.getMethod(setter, extension.getClass())
							.invoke(extension, patchData);

				} catch (Exception e) {
					throw new PatchingFailed(
							"Failed to patch user, retriving and setting value, method:"
									+ method.getName() + " , class:"
									+ extension.getClass().getName(), e);
				}
			}
		}

		new ComplexHandler().merge(patch, this);
	}

	public <T> T getExtension(Class<?> type) throws UnknownExtension {
		for (int i = 0; i < this.extensions.size(); i++) {
			if (this.extensions.get(i).getClass() == type) {
				return (T) this.extensions.get(i);
			}
		}

		throw new UnknownExtension("Could not find object for type '"
				+ type.getName() + "'");
	}

	public List<Object> getExtensions() {
		return this.extensions;
	}

	private void addExtensions(List<Class<?>> extensionTypes) {
		for (Class<?> clazz : extensionTypes) {
			try {
				extensions.add(clazz.newInstance());
			} catch (InstantiationException e) {
				throw new RuntimeException(
						"Failed to create extension instance from '"
								+ clazz.getName() + "'", e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(
						"Failed to create extension instance from '"
								+ clazz.getName() + "'", e);
			}
		}
	}

	public <T> T getAttribute(String name) throws UnknownAttribute {
		if(name == null)
		{
			throw new IllegalArgumentException("The name argument cannot be null");
		}
		for (Object extension : extensions) {
			Extension extensionAttribute = extension.getClass().getAnnotation(Extension.class);
			if(extensionAttribute == null)
			{
				continue;
			}
			String schema = extensionAttribute.schema();
			if(schema == null)
			{
				continue;
			}
			if(name.startsWith(schema))
			{
				String methodName = name.substring(schema.length());
				if (methodName.contains(".")) {
		            String localName = name.substring(0, methodName.indexOf("."));
		            String nextName = name.substring(methodName.indexOf(".") + 1, methodName.length());
		            Method method = findAttributeMethodOnExtension(localName, extension);
		            ComplexType next = (ComplexType) invokeMethod(method, localName);
		            if (next == null) {
		                return null;
		            }
		            return next.getAttribute(nextName);
		        }
				else
				{
					Method method =  findAttributeMethodOnExtension(methodName, extension);
					return (T) invokeMethod(method, name);
				}
			}

		}
		return super.getAttribute(name);
	}

	private Method findAttributeMethodOnExtension(String name, Object extension) throws UnknownAttribute {
		for (Method method : extension.getClass().getMethods()) {
			if (!method.isAnnotationPresent(Attribute.class)) {
				continue;
			}

			Attribute attribute = method.getAnnotation(Attribute.class);
			if (attribute.name().equals(name)) 
			{
				return method;
			}
		}
		return null;
	}

	public ComplexType setAttribute(String name, Object attribute)
			throws UnknownAttribute {
		if(name == null)
		{
			throw new IllegalArgumentException("The name argument cannot be null");
		}
		for (Object extension : extensions) {
			Extension extensionAttribute = extension.getClass().getAnnotation(Extension.class);
			if(extensionAttribute == null)
			{
				continue;
			}
			String schema = extensionAttribute.schema();
			if(schema == null)
			{
				continue;
			}
			if(name.startsWith(schema))
			{
				String methodName = name.substring(schema.length());
				if (methodName.contains(".")) {
		            String localName = name.substring(0, methodName.indexOf("."));
		            String nextName = name.substring(methodName.indexOf(".") + 1, methodName.length());
		            Method method = findAttributeMethodOnExtension(localName, extension);
		            ComplexType next = (ComplexType) invokeMethod(method, localName);
		            if (next == null) {
		                next = this.getMetaData(localName).newInstance();
		                this.setAttribute(localName, next);
		            }
		            next.setAttribute(nextName, attribute);
		            return this;
				}
				else
				{
					 Method method = findAttributeMethodOnExtension(name, extension);
			            if (method == null) {
			                throw new UnknownAttribute("Has no method for attribute '" + name + "'");
			            }
			            
			            String setter = "s" + method.getName().substring(1);
			            Method setMethod = null;
			            try {
			                setMethod = ReflectionHelper.getMethod(setter, this.getClass());
			                setMethod.invoke(this, attribute);
			            } catch (IllegalArgumentException e) {
			                throw new RuntimeException("Failed to call setter '" + setter + "' on '" + setMethod.getDeclaringClass().getName()
			                        + "' to set attribute '" + name + "'", e);
			            } catch (IllegalAccessException e) {
			                throw new RuntimeException("Failed to call setter '" + setter + "' on '" + setMethod.getDeclaringClass().getName()
			                        + "' to set attribute '" + name + "'", e);
			            } catch (InvocationTargetException e) {
			                throw new RuntimeException("Failed to call setter '" + setter + "' on '" + setMethod.getDeclaringClass().getName()
			                        + "' to set attribute '" + name + "'", e);
			            } catch (NoSuchMethodException e) {
			                throw new RuntimeException("Failed to call setter '" + setter + "' on '" + setMethod.getDeclaringClass().getName()
			                        + "' to set attribute '" + name + "'", e);
			            }

			        return this;
				}
			}
		}
		
		return super.setAttribute(name, attribute);
	}

	@Attribute(name = "id", handler = StringHandler.class)
	public String getId() {
		return this.id;
	}

	@Attribute(name = "schemas", handler = ListHandler.class)
	public List<String> getSchemas() {
		List<String> schemas = new ArrayList<String>();
		if (!this.getClass().isAnnotationPresent(Extension.class)) {
			throw new RuntimeException("Extension class '"
					+ this.getClass().getName() + "' is missing annotation");
		}
		Extension metaData = this.getClass().getAnnotation(Extension.class);
		schemas.add(metaData.schema());

		for (Object extension : this.extensions) {
			if (!extension.getClass().isAnnotationPresent(Extension.class)) {
				throw new RuntimeException("Extension class '"
						+ extension.getClass().getName()
						+ "' is missing annotation");
			}
			metaData = extension.getClass().getAnnotation(Extension.class);
			schemas.add(metaData.schema());
		}

		return schemas;
	}

	@Attribute(name = "meta", handler = ComplexHandler.class, type = Meta.class)
	public Meta getMeta() {
		return this.meta;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setSchemas(List<String> schemas) {
		// Ignore and it will go away (we do not set schemas only read)
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}

	@Override
	public boolean equals(Object otherObj) {
		if (this == otherObj) {
			return true;
		}

		if (!(otherObj instanceof Resource)) {
			return false;
		}
		Resource otherResource = (Resource) otherObj;

		if (otherResource.getExtensions().size() != this.getExtensions().size()) {
			return false;
		}

		try {
			for (Object myExtension : this.extensions) {
				Object otherExtension = otherResource.getExtension(myExtension
						.getClass());
				for (Method myMethod : myExtension.getClass().getMethods()) {
					if (!myMethod.isAnnotationPresent(Attribute.class)) {
						continue;
					}
					Method otherMethod = otherExtension.getClass().getMethod(
							myMethod.getName(), new Class<?>[] {});

					Object myData = myMethod.invoke(myExtension,
							new Object[] {});
					Object otherData = otherMethod.invoke(otherExtension,
							new Object[] {});

					if (myData == otherData) {
						continue;
					}

					if ((myData != null && !myData.equals(otherData))
							|| (otherData != null && !otherData.equals(myData))) {
						return false;
					}
				}
			}
		} catch (UnknownExtension e) {
			return false;
		} catch (SecurityException e) {
			throw new RuntimeException("Internal error, extension comparing", e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("Internal error, extension comparing", e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Internal error, extension comparing", e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Internal error, extension comparing", e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException("Internal error, extension comparing", e);
		}

		return super.equals(otherObj);
	}
	
	private Object invokeMethod(Method method, String name) throws UnknownAttribute {
        try {
            if (method == null) {
                throw new UnknownAttribute("Could not find attribute '" + name + "'");
            }
            return method.invoke(this, new Object[] {});
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Failed to read value for attribute '" + name + "'", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to read value for attribute '" + name + "'", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Failed to read value for attribute '" + name + "'", e);
        }
    }

}
