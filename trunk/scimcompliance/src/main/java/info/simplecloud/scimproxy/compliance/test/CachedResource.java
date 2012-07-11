package info.simplecloud.scimproxy.compliance.test;

public class CachedResource {

	protected String id;
	protected String etag;

	public CachedResource() {
		super();
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