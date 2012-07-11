package info.simplecloud.scimproxy.compliance.test;

import java.util.ArrayList;
import java.util.List;

public class ResourceCache<T extends CachedResource> {

	List<T> resources = new ArrayList<T>();

	public ResourceCache() {
		super();
	}

	public void addCachedResource(T cached) {
		this.resources.add(cached);
	}

	public T removeCachedResource() {
		return this.resources.remove(0);
	}

	public int size() {
		return this.resources.size();
	}
}