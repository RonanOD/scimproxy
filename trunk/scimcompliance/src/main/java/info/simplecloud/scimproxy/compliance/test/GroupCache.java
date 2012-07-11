package info.simplecloud.scimproxy.compliance.test;

import java.util.ArrayList;
import java.util.List;

public class GroupCache {
	
	List<CachedGroup> groups = new ArrayList<CachedGroup>();

	public void addCachedGroup(CachedGroup cachedGroup) {
		this.groups.add(cachedGroup);
	}
	
	public CachedGroup removeCachedGroup() {
		return this.groups.remove(0);
	}
	
	public int size() {
    	return this.groups.size();
    }

}
