package info.simplecloud.scimproxy.authentication;

import info.simplecloud.scimproxy.config.Config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Authenticator {

	public Authenticator() {
		
	}

	public boolean authenticate(HttpServletRequest req, HttpServletResponse resp) {
		boolean authStatus = false;

		// check if BASIC authentication is configured
		if(Config.getInstance().isBasicAuth()) {
			String basicAuth = req.getHeader("Authorization");
			if(basicAuth != null && basicAuth.indexOf("Basic ") == 0) {
				authStatus = new Basic().authenticate(basicAuth);
			}
		}

		return authStatus;
	}
}
