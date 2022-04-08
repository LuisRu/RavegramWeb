package com.luis.ravegram.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.ravegram.model.UsuarioDTO;
import com.luis.ravegram.service.UsuarioService;
import com.luis.ravegram.service.impl.UsuarioServiceImpl;
import com.luis.ravegram.web.controller.util.AttributeNames;
import com.luis.ravegram.web.controller.util.SessionManager;
import com.luis.ravegram.web.controller.util.ViewPaths;

/**
 * Filtro de autentificacion
 */
// @WebFilter("/*") Si ponemos esto no va a respetar el orden que le ponemos en web.xml
public class AuthenticationFilter extends HttpFilter implements Filter {

	private static Logger logger = LogManager.getLogger(AuthenticationFilter.class);
	private UsuarioService usuarioService = null;

	public AuthenticationFilter() {
		super();
		usuarioService = new UsuarioServiceImpl();
	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		UsuarioDTO usuario = (UsuarioDTO) SessionManager.get(httpRequest, AttributeNames.USER);
		if (usuario==null) {	
			if (logger.isWarnEnabled()) {
				logger.warn("Trying to GET "+httpRequest.getRequestURI());
			}
			httpRequest.getRequestDispatcher(ViewPaths.USER_LOGIN).forward(httpRequest, httpResponse);
		} else {
			chain.doFilter(request, response);
		}

	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

	public void destroy() {
	}


}
