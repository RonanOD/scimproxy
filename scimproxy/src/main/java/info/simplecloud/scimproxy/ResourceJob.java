package info.simplecloud.scimproxy;

import info.simplecloud.scimproxy.util.Util;



public class ResourceJob {

	public static String METHOD_POST = "post";
	public static String METHOD_PUT = "put";
	public static String METHOD_PATCH = "patch";
	public static String METHOD_DELETE = "delete";
	
	public static String TYPE_USER = "user";
	public static String TYPE_GROUP = "group";
	
	private String method = "";
	private String bulkId = "";
	private String id = "";
	private String path = "";
	private String version = "";
	private String type = "";
	private String data = null;

	public ResourceJob() {
		
	}
	
	public ResourceJob(String method, String bulkId, String path, String version, String data) {
		this.method = method;
		this.bulkId = bulkId;
		this.path = path;

		// if type is not sent it's in the resource value, extract it.
		if(path != null && path.indexOf("/v1/User") > -1) {
			this.type = TYPE_USER;
		}
		if(path != null && path.indexOf("/v1/Group") > -1) {
			this.type = TYPE_GROUP;
		}
		
		if("user".equalsIgnoreCase(this.type)) {
			this.id = Util.getUserIdFromUri(path);
		}
		if("group".equalsIgnoreCase(this.type)) {
			this.id = Util.getGroupIdFromUri(path);
		}

		this.version = version;
		this.data = data;
	}
	
    public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getBulkId() {
		return bulkId;
	}
	public void setBulkId(String bulkId) {
		this.bulkId = bulkId;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public String toString() {
		String d = "empty";
		if(data != null) {
			d = data;
		}
		return "method:" + method + ", bulkId:" + bulkId + ", id:" + id + ", resource:" + path + ", version:" + version + ", type:" + type + ", data:" + d;
	}

}
