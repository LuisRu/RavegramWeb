package com.luis.ravegram.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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
import com.luis.ravegram.web.util.ValidationUtils;

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
			//SOLICITUD A EVENTO

			targetView=ViewPaths.HOME;

			UsuarioDTO usuario = (UsuarioDTO) SessionManager.get(request, AttributeNames.USER);
			Long idEvento = ValidationUtils.longTransform(errors, request.getParameter(ParameterNames.ID));
			
			
					try {

				solicitudService.usuarioSolicita(usuario.getId(), idEvento);
			
				targetView = request.getParameter(ParameterNames.URL);
				forward = false;
			
				
			}catch (RequestInvalidStateException rise) {
				logger.error("SolicitudEvento: "+idEvento,rise.getMessage(), rise);
				errors.addCommonError(ErrorsNames.ERROR_REQUEST_INVALID_STATE_EXCEPTION);
			}catch (RequestNotFoundException rnfe) {
				logger.error("SolicitudEvento: "+idEvento,rnfe.getMessage(), rnfe);
				errors.addCommonError(ErrorsNames.ERROR_REQUEST_NOT_FOUND_EXCEPTION);
			} catch (DataException de) {
				logger.error("SolicitudEvento: "+idEvento,de.getMessage(), de);
				errors.addCommonError(ErrorsNames.ERROR_DATA_EXCEPTION);
			}	
			
		}else if(ActionNames.RECHAZA.equalsIgnoreCase(actionName)) {
			//RECHAZA
			
			targetView=ViewPaths.HOME;

			UsuarioDTO usuario = (UsuarioDTO) SessionManager.get(request, AttributeNames.USER);
			Long idEvento = ValidationUtils.longTransform(errors, request.getParameter(ParameterNames.ID));							

			try {

				solicitudService.eventoNoInteresa(usuario.getId(), idEvento);
				
				targetView = request.getParameter(ParameterNames.URL);
				forward = false;
			

			}catch (RequestInvalidStateException rise) {
				logger.error("SolicitudRechaza: "+idEvento,rise.getMessage(), rise);
				errors.addCommonError(ErrorsNames.ERROR_REQUEST_INVALID_STATE_EXCEPTION);
			}catch (RequestNotFoundException rnfe) {
				logger.error("SolicitudRechaza: "+idEvento,rnfe.getMessage(), rnfe);
				errors.addCommonError(ErrorsNames.ERROR_REQUEST_NOT_FOUND_EXCEPTION);
			} catch (DataException de) {
				logger.error("SolicitudRechaza: "+idEvento,de.getMessage(), de);
				errors.addCommonError(ErrorsNames.ERROR_DATA_EXCEPTION);
			}	


		}else if(ActionNames.INVITIACION_USUARIO.equalsIgnoreCase(actionName)) {
			//INVITAR A USUARIOS A MIS EVENTOS
			
			targetView = request.getParameter(ParameterNames.URL);
			
			String idUsuarioStr = request.getParameter(ParameterNames.ID); 
			Long idUsuario = ValidationUtils.longTransform(errors, idUsuarioStr);
			List<Long> idsEventos = (ValidationUtils.longValidator(errors, request, ParameterNames.IDS));
			if(idsEventos==null) {
				errors.addCommonError(ErrorsNames.ERROR_DEBES_SELECCION);
				
			}
			if(!errors.hasErrors()){
			try {
				//TODO hacer en service
				for(Long id: idsEventos) {
					solicitudService.eventoInvita(idUsuario, id);
				}
				
				targetView = request.getParameter(ParameterNames.URL);
				forward = false;
				

			}catch (RequestInvalidStateException rise) {
				logger.error("invitar: ",rise.getMessage(), rise);
				errors.addCommonError(ErrorsNames.ERROR_REQUEST_INVALID_STATE_EXCEPTION);
			}catch (RequestNotFoundException rnfe) {
				logger.error("invitar:: ",rnfe.getMessage(), rnfe);
				errors.addCommonError(ErrorsNames.ERROR_REQUEST_NOT_FOUND_EXCEPTION);
			} catch (DataException de) {
				logger.error("invitar:: ",de.getMessage(), de);
				errors.addCommonError(ErrorsNames.ERROR_DATA_EXCEPTION);
			}	
			}

		}else {
			targetView= ViewPaths.HOME;
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
