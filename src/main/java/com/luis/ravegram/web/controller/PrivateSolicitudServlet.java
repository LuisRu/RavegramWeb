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

import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.exception.RequestInvalidStateException;
import com.luis.ravegram.exception.RequestNotFoundException;
import com.luis.ravegram.model.UsuarioDTO;
import com.luis.ravegram.service.SolicitudService;
import com.luis.ravegram.service.impl.SolicitudServiceImpl;
import com.luis.ravegram.web.controller.util.ActionNames;
import com.luis.ravegram.web.controller.util.AttributeNames;
import com.luis.ravegram.web.controller.util.ControllerPaths;
import com.luis.ravegram.web.controller.util.ErrorsNames;
import com.luis.ravegram.web.controller.util.ParameterNames;
import com.luis.ravegram.web.controller.util.SessionManager;
import com.luis.ravegram.web.controller.util.ViewPaths;
import com.luis.ravegram.web.util.ParametersUtil;

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
			
			String distancia = request.getParameter(ParameterNames.DISTANCIA);
			String esPrivado = request.getParameter(ParameterNames.ES_PRIVADO);
			String tipoEstablecimiento = request.getParameter(ParameterNames.TIPO_ESTABLECIMIENTO);
			String tipoTematica =request.getParameter(ParameterNames.TIPO_TEMATICA); 
			String tipoMusica = request.getParameter(ParameterNames.TIPO_MUSICA);

			try {

				solicitudService.usuarioSolicita(usuario.getId(), eventoId);
				
				Map<String, String> userDetailParams = new HashMap<String, String>();
				userDetailParams.put(ParameterNames.ACTION, ActionNames.EVENT_SEARCH);
				if(request.getParameter(ParameterNames.DISTANCIA)!=null){
					userDetailParams.put(ParameterNames.DISTANCIA, request.getParameter(ParameterNames.DISTANCIA));
				}
				if(request.getParameter(ParameterNames.ES_PRIVADO)!=null){
				userDetailParams.put(ParameterNames.ES_PRIVADO, request.getParameter(ParameterNames.ES_PRIVADO));
				}
				if(request.getParameter(ParameterNames.TIPO_ESTABLECIMIENTO)!=null){
					userDetailParams.put(ParameterNames.TIPO_ESTABLECIMIENTO, request.getParameter(ParameterNames.TIPO_ESTABLECIMIENTO));	
				}
				if(request.getParameter(ParameterNames.TIPO_TEMATICA)!=null){
					userDetailParams.put(ParameterNames.TIPO_TEMATICA, request.getParameter(ParameterNames.TIPO_TEMATICA));	
				}
				if(request.getParameter(ParameterNames.TIPO_MUSICA)!=null){
					userDetailParams.put(ParameterNames.TIPO_MUSICA, request.getParameter(ParameterNames.TIPO_MUSICA));	
				}
				

				targetView = ParametersUtil.getURL(ControllerPaths.PRIVATE_EVENTO, userDetailParams);
				System.out.println(targetView);
				forward = false;
				
			}catch (RequestInvalidStateException rise) {
				logger.error("SolicitudEvento: "+idEventoStr,rise.getMessage(), rise);
				errors.addCommonError(ErrorsNames.ERROR_REQUEST_INVALID_STATE_EXCEPTION);
			}catch (RequestNotFoundException rnfe) {
				logger.error("SolicitudEvento: "+idEventoStr,rnfe.getMessage(), rnfe);
				errors.addCommonError(ErrorsNames.ERROR_REQUEST_NOT_FOUND_EXCEPTION);
			} catch (DataException de) {
				logger.error("SolicitudEvento: "+idEventoStr,de.getMessage(), de);
				errors.addCommonError(ErrorsNames.ERROR_DATA_EXCEPTION);
			}	
			
		}else if(ActionNames.RECHAZA.equalsIgnoreCase(actionName)) {
			//rechaza 
			
			targetView=ViewPaths.HOME;

			UsuarioDTO usuario = (UsuarioDTO) SessionManager.get(request, AttributeNames.USER);
			String idEventoStr = request.getParameter(ParameterNames.ID);							
			Long idEvento = Long.valueOf(idEventoStr); // unsafe conversion

			try {

				solicitudService.eventoNoInteresa(usuario.getId(), idEvento);

			}catch (RequestInvalidStateException rise) {
				logger.error("SolicitudRechaza: "+idEventoStr,rise.getMessage(), rise);
				errors.addCommonError(ErrorsNames.ERROR_REQUEST_INVALID_STATE_EXCEPTION);
			}catch (RequestNotFoundException rnfe) {
				logger.error("SolicitudRechaza: "+idEventoStr,rnfe.getMessage(), rnfe);
				errors.addCommonError(ErrorsNames.ERROR_REQUEST_NOT_FOUND_EXCEPTION);
			} catch (DataException de) {
				logger.error("SolicitudRechaza: "+idEventoStr,de.getMessage(), de);
				errors.addCommonError(ErrorsNames.ERROR_DATA_EXCEPTION);
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
