package info.simplecloud.scimproxy.compliance.test;

public class CachedUser extends CachedResource{
    public CachedUser(String id, String etag) {
        this.id = id;
        this.etag = etag;
    }
}
