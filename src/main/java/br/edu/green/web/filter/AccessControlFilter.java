package br.edu.green.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.edu.green.web.entity.PersonEntity;
import br.edu.green.web.util.Util;

/**
 * AccessControlFilter.java: This class implements filters that are calling to verify the adequate conditions to run the web page.
 * 
 * @author Sergio Lopes Jr.
 * @version 0.1
 * @since 25/08/2015
 * 
 */

@WebFilter(filterName = "AccessControlFilter", urlPatterns = { "*.xhtml" })
public class AccessControlFilter implements Filter {

	/**
	 * Default constructor
	 */
	public AccessControlFilter() {
		super();
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		try {

			// getting http objects
			HttpServletRequest requestHTTP = (HttpServletRequest) request;
			HttpServletResponse responseHTTP = (HttpServletResponse) response;
			HttpSession session = requestHTTP.getSession(false);
			String address = requestHTTP.getRequestURI();

			// System.out.println("--------------------------------------------------------------------------------");
			// if (requestHTTP != null) {
			// System.out.println("Classe: AccessControlFilter " + " metodo: doFilter" + " requestHTTP: " + requestHTTP.toString());
			// }
			// if (responseHTTP != null) {
			// System.out.println("Classe: AccessControlFilter " + " metodo: doFilter" + " responseHTTP: " + responseHTTP.toString());
			// }
			// if (session != null) {
			// System.out.println("Classe: AccessControlFilter " + " metodo: doFilter" + " session: " + session.toString());
			// }
			// if (address != null) {
			// System.out.println("Classe: AccessControlFilter " + " metodo: doFilter" + " address: " + address);
			// }

			// configuring the name of login page
			String loginPage = "/portal/login/login.xhtml";

			// checking validate of session
			if ((session != null && session.getAttribute(Util.className(PersonEntity.class.getName())) != null) || address.indexOf(loginPage) >= 0 || address.indexOf("/public/") >= 0 || address.contains("javax.faces.resource")) {

				// logged person or public URL
				chain.doFilter(request, response);

			} else {

				// calling login page
				responseHTTP.sendRedirect(requestHTTP.getContextPath() + loginPage);
			}
		} catch (Exception e) {
			System.out.println("AccessControlFilter - method doFilter - exception: " + e.toString());
		}
	}

}
