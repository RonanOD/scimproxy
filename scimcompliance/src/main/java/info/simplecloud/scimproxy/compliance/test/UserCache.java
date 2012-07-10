package info.simplecloud.scimproxy.compliance.test;

import java.util.ArrayList;
import java.util.List;

public class UserCache {
    private List<CachedUser> cachedUsers = new ArrayList<CachedUser>();
    
    public CachedUser removeCashedUser() {
        return this.cachedUsers.remove(0);
    }
    
    public void addCachedUser(CachedUser cachedUser) {
        this.cachedUsers.add(cachedUser);
    }
}
