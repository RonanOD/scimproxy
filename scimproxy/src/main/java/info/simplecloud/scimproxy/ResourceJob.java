package info.simplecloud.scimproxy;



public class ResourceJob {

	public static String METHOD_POST = "post";
	public static String METHOD_PUT = "put";
	public static String METHOD_PATCH = "patch";
	public static String METHOD_DELETE = "delete";
	
	public static String TYPE_USER = "user";
	public static String TYPE_GROUP = "group";
	
	private String method = "";
	private String batchId = "";
	private String location = "";
	private String etag = "";
	private String type = "";
	private String data = null;
	private String id = "";

	public ResourceJob() {
		
	}
	
	public ResourceJob(String method, String batchId, String location, String etag, String type, String data, String id) {
		this.method = method;
		this.batchId = batchId;
		this.location = location;
		this.etag = etag;
		this.type = type;
		this.data = data;
		this.id = id;
	}
	
    public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getEtag() {
		return etag;
	}
	public void setEtag(String etag) {
		this.etag = etag;
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

}
