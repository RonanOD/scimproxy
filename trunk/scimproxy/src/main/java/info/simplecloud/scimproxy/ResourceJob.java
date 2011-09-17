package info.simplecloud.scimproxy;



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
	private String etag = "";
	private String type = "";
	private String data = null;

	public ResourceJob() {
		
	}
	
	public ResourceJob(String method, String bulkId, String id, String etag, String type, String data) {
		this.method = method;
		this.bulkId = bulkId;
		this.id = id;
		this.etag = etag;
		this.type = type;
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

	public String toString() {
		String d = "empty";
		if(data != null) {
			d = data;
		}
		return "method:" + method + ", bulkId:" + bulkId + ", id:" + id + ", etag:" + etag + ", type:" + type + ", data:" + d;
	}
}
