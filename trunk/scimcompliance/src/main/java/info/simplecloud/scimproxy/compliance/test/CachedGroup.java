package info.simplecloud.scimproxy.compliance.test;

public class CachedGroup {
	private String id;
	private String etag;
	
	public CachedGroup(String id, String etag) {
		this.id = id;
		this.etag = etag;
	}
	public String getEtag() {
		return etag;
	}
	public void setEtag(String etag) {
		this.etag = etag;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
