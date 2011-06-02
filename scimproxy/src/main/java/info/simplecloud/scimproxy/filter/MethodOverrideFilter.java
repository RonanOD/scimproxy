package info.simplecloud.scimproxy.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author Filippo De Luca
 * 
 * @version $Id$
 */
public class MethodOverrideFilter implements Filter {

	public static final String HEADER_PARAM = "methodOverrideHeader";

	public static final String DEFAULT_HEADER = "X-HTTP-Method-Override";

	private String header = DEFAULT_HEADER;

	public void init(FilterConfig filterConfig) throws ServletException {

		header = filterConfig.getInitParameter(HEADER_PARAM);
		if (StringUtils.isBlank(header)) {
			header = DEFAULT_HEADER;
		}

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		ServletRequest filteredRequest = request;

		if (request instanceof HttpServletRequest) {

			HttpServletRequest httpRequest = (HttpServletRequest) request;
			filteredRequest = new MethodOverrideHttpServletRequestDecorator(
					httpRequest, header);
		}

		chain.doFilter(filteredRequest, response);
	}

	public void destroy() {
		// Empty
	}

}
