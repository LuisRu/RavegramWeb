package com.luis.ravegram.web.controller.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.ravegram.web.controller.PrivateUsuarioServlet;

public class CookieManager {

	private static Logger logger = LogManager.getLogger(CookieManager.class);
	
	public static final String getValue(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies!=null) {
			for (Cookie c: cookies) {
				if (c.getName().equalsIgnoreCase(name)) {
					return c.getValue();
				}
			}
		}
		return null;
	}
	
	public static final void setValue(HttpServletResponse response, String name, String value, int ttl) {
		if (logger.isInfoEnabled()) {
			logger.info("Setting cookie: "+name+" = "+value+" ("+ttl+")");
		}
		Cookie cookie = new Cookie(name, value); 
		cookie.setMaxAge(ttl);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
}
