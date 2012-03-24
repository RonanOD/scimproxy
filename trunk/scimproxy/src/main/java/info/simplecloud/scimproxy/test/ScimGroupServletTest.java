package info.simplecloud.scimproxy.test;

import info.simplecloud.scimproxy.ScimGroupServlet;
import info.simplecloud.scimproxy.config.Config;
import info.simplecloud.scimproxy.storage.StorageDelegator;

public class ScimGroupServletTest extends ScimGroupServlet{

	private static final long serialVersionUID = 1L;

    protected StorageDelegator getUserDelegator(String session) {
    	return StorageDelegator.getInstance(session, Config.UNIT_TEST_STORAGE_TYPE);
    }

}

