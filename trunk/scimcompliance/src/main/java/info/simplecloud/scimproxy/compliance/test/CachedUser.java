package info.simplecloud.scimproxy.compliance.test;

public class CachedUser {

    private String id;
    private String etag;

    public CachedUser(String id, String etag) {
        this.id = id;
        this.etag = etag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }
}
