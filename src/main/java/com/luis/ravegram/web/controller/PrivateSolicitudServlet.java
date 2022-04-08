package com.luis.ravegram.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.digester.CallParamRule;

import com.luis.ravegram.model.UsuarioDTO;
import com.luis.ravegram.service.EventoService;
import com.luis.ravegram.service.SolicitudService;
import com.luis.ravegram.service.UsuarioService;
import com.luis.ravegram.service.UsuarioSigueService;
import com.luis.ravegram.service.impl.EventoServiceImpl;
import com.luis.ravegram.service.impl.SolicitudServiceImpl;
import com.luis.ravegram.service.impl.UsuarioServiceImpl;
import com.luis.ravegram.service.impl.UsuarioSigueServiceImpl;
import com.luis.ravegram.web.controller.util.ActionNames;
import com.luis.ravegram.web.controller.util.AttributeNames;
import com.luis.ravegram.web.controller.util.ControllerPaths;
import com.luis.ravegram.web.controller.util.ParameterNames;
import com.luis.ravegram.web.controller.util.SessionManager;
import com.luis.ravegram.web.controller.util.ViewPaths;

/**
 * Controlador (Servlet) para peticiones de solicitud
 * autenticadas.
 */
@WebServlet("/private/solicitud")
public class PrivateSolicitudServlet extends HttpServlet {
	private static Logger logger = LogManager.getLogger(PrivateUsuarioServlet.class);


	private SolicitudService solicitudService = null;
	
	
    public PrivateSolicitudServlet() {
        super();
        solicitudService = new SolicitudServiceImpl();
		
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

				Errors errors = new Errors();
				request.setAttribute(AttributeNames.ERRORS, errors);
				String targetView = null;
				boolean forward = true;
				String actionName = request.getParameter(ParameterNames.ACTION);
				
				if (logger.isInfoEnabled()) {
					logger.info("Processing action "+actionName);
				}
				
				
				
				if (ActionNames.SOLICITUD_EVENTO.equalsIgnoreCase(actionName)) {
					//solicitud a evento 
					targetView=ViewPaths.HOME;
					
					UsuarioDTO usuario = (UsuarioDTO) SessionManager.get(request, AttributeNames.USER);
					String idEventoStr = request.getParameter(ParameterNames.ID);							
					Long eventoId = Long.valueOf(idEventoStr); // unsafe conversion
					
					try {
						
						solicitudService.usuarioSolicita(usuario.getId(), eventoId);
						
						
					}catch (Exception e) {
						// TODO: handle exception
					}
				}else if(ActionNames.RECHAZA.equalsIgnoreCase(actionName)) {
					//rechaza 
					targetView=ViewPaths.HOME;
					
					UsuarioDTO usuario = (UsuarioDTO) SessionManager.get(request, AttributeNames.USER);
					String idEventoStr = request.getParameter(ParameterNames.ID);							
					Long eventoId = Long.valueOf(idEventoStr); // unsafe conversion
					
					try {
						
						solicitudService.rechazado(usuario.getId(), eventoId);
						
					}catch (Exception e) {
						// TODO: handle exception
					}
					
					
				}
				
				
				
				logger.info("Redirigiendo a "+targetView);
				if (forward) {
					request.getRequestDispatcher(targetView).forward(request, response);
				} else {
					response.sendRedirect(request.getContextPath()+targetView);
				} 
				

	}
	

	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
