package com.luis.ravegram.web.filter;

import java.io.IOException;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.ravegram.exception.ServiceException;
import com.luis.ravegram.model.UsuarioDTO;
import com.luis.ravegram.service.UsuarioService;
import com.luis.ravegram.service.impl.UsuarioServiceImpl;
import com.luis.ravegram.web.controller.util.AttributeNames;
import com.luis.ravegram.web.controller.util.CookieManager;
import com.luis.ravegram.web.controller.util.SessionManager;

/**
 * Servlet Filter implementation class KeepAuthenticatedFilter
 */
public class KeepAuthenticatedFilter extends HttpFilter implements Filter {
	private static Logger logger = LogManager.getLogger(KeepAuthenticatedFilter.class);
	private UsuarioService usuarioService = null;
   
    public KeepAuthenticatedFilter() {
        super();
        usuarioService = new UsuarioServiceImpl();
    }


    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    	HttpServletRequest httpRequest = (HttpServletRequest) request;

    	UsuarioDTO usuario = (UsuarioDTO) SessionManager.get(httpRequest, AttributeNames.USER);
    	if (usuario==null) {
    		// No esta autenticado, miro la cookie
    		String email = CookieManager.getValue(httpRequest, AttributeNames.USER);
    		if (!StringUtils.isBlank(email)) {
    			// Tenemos cookie de usuario (RECORDAR QUE ESTO ES UN AGUJERO DE SEGURIDAD)
    			try {
    				usuario = usuarioService.findByEmail(email);
    				SessionManager.set(httpRequest, AttributeNames.USER, usuario);
    				Set <Long> idsSeguidos = usuarioService.findSeguidosIds(usuario.getId());
    				SessionManager.set(httpRequest, AttributeNames.FOLLOWING, idsSeguidos);


    				if (logger.isInfoEnabled()) {
    					logger.info("User "+email+" authenticated form cookie");
    				}
    			} catch (ServiceException se) {
    				logger.error("Trying to login by cookie: "+email, se);
    			}
    		}
    	} // else ya está autenticado
    	
    	chain.doFilter(request, response);
    }


	public void init(FilterConfig fConfig) throws ServletException {
	}

}
