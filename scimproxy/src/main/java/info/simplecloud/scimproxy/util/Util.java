package info.simplecloud.scimproxy.util;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Util methods that's used by ScimProxy.
 */
public class Util {

	private static SecureRandom random = new SecureRandom();

	/**
	 * Generates a pseudo random string that can be used as a version string in
	 * a SCIM user.
	 * 
	 * @return A pseudo random string.
	 */
	public static String generateVersionString() {
		return new BigInteger(130, random).toString(32) + Long.toString(System.currentTimeMillis());
	}

}
