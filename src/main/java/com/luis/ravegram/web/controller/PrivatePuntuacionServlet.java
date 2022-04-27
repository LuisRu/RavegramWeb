package com.luis.ravegram.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.ravegram.model.UsuarioDTO;
import com.luis.ravegram.model.UsuarioEventoPuntuaDTO;
import com.luis.ravegram.service.PuntuacionService;
import com.luis.ravegram.service.impl.PuntuacionServiceImpl;
import com.luis.ravegram.web.controller.util.ActionNames;
import com.luis.ravegram.web.controller.util.AttributeNames;
import com.luis.ravegram.web.controller.util.ControllerPaths;
import com.luis.ravegram.web.controller.util.ParameterNames;
import com.luis.ravegram.web.controller.util.SessionManager;
import com.luis.ravegram.web.controller.util.ViewPaths;
import com.luis.ravegram.web.util.ParametersUtil;

/**
 * Controlador (Servlet) para peteciones de eventos que 
 * requieren autentication.
 */
@WebServlet("/private/puntuacion")
public class PrivatePuntuacionServlet extends HttpServlet {

	private static Logger logger = LogManager.getLogger(PrivatePuntuacionServlet.class);


	private PuntuacionService puntuacionesService = null;

	public PrivatePuntuacionServlet() {
		super();
		puntuacionesService = new PuntuacionServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		String targetView = null;
		boolean forward = true;
		
		Errors errors = new Errors();
		request.setAttribute(AttributeNames.ERRORS, errors);
		
		String actionName = request.getParameter(ParameterNames.ACTION);

		if (logger.isInfoEnabled()) {
			logger.info("Processing action "+actionName);
		}
		
		if (ActionNames.PUNTUACION_CREATE.equalsIgnoreCase(actionName)) {
			//VISTA EN DETALLE
			
			UsuarioDTO usuario = (UsuarioDTO) SessionManager.get(request, AttributeNames.USER);
			
			String comentarioStr = request.getParameter(ParameterNames.COMENTARIO);
			String valoracionStr = request.getParameter(ParameterNames.VALORACION);
			String idEventoStr = request.getParameter(ParameterNames.ID);
			
			
			Map<String, String> userDetailParams = new HashMap<String, String>();
			userDetailParams.put(ParameterNames.ACTION, ActionNames.EVENT_DETAIL);
			userDetailParams.put(ParameterNames.ID, idEventoStr);
			

			targetView = ParametersUtil.getURL(ControllerPaths.USER, userDetailParams); 
			forward = false;

			
			try {
				UsuarioEventoPuntuaDTO puntuacion = new UsuarioEventoPuntuaDTO();
				
				puntuacion.setValoracion(Integer.valueOf(valoracionStr));
				puntuacion.setComentario(comentarioStr);
				puntuacion.setIdUsuario(usuario.getId());
				puntuacion.setIdEvento(Long.valueOf(idEventoStr));
				
				puntuacionesService.create(puntuacion);
			
			} catch (Exception e) {
				logger.error("Detail: ",e.getMessage(),e);
			}			

			
			
		};


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
