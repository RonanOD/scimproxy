package info.simplecloud.scimproxy.filter;

import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletRequestWrapper;

/**
* @author Filippo De Luca
*
* @version $Id$
*/
public class MethodOverrideHttpServletRequestDecorator extends
		HttpServletRequestWrapper {

	public static final String DEFAULT_HEADER = "X-HTTP-Method-Override";

	private final String methodOverrideHeader;

	private transient String method;

	/**
	 * @param request
	 */
	public MethodOverrideHttpServletRequestDecorator(HttpServletRequest request, String methodOverrideHeader) {
		super(request);
		this.methodOverrideHeader = methodOverrideHeader;
	}

	@Override
	public String getMethod() {

		if(method==null) {
			method = resolveMethod();
		}

		return method;
	}

	protected String resolveMethod() {

		String headerValue = getHeader(methodOverrideHeader);

		if(headerValue!=null) {
			return headerValue;
		}
		else {
			return super.getMethod();
		}
	}

}
