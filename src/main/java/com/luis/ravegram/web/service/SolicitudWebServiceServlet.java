package com.luis.ravegram.web.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.luis.ravegram.model.SolicitudDTO;
import com.luis.ravegram.model.UsuarioDTO;
import com.luis.ravegram.model.state.SolicitudEstado;
import com.luis.ravegram.service.SolicitudService;
import com.luis.ravegram.service.impl.SolicitudServiceImpl;
import com.luis.ravegram.web.controller.Errors;
import com.luis.ravegram.web.controller.util.ActionNames;
import com.luis.ravegram.web.controller.util.AttributeNames;
import com.luis.ravegram.web.controller.util.ParameterNames;
import com.luis.ravegram.web.controller.util.SessionManager;
import com.luis.ravegram.web.util.ValidationUtils;


@WebServlet("/solicitud-service")
public class SolicitudWebServiceServlet extends HttpServlet {

	private static Logger logger = LogManager.getLogger(SolicitudWebServiceServlet.class);

	private SolicitudService solicitudService = null;
	private Gson gson = null; 

	public SolicitudWebServiceServlet() {
		super();
		solicitudService = new SolicitudServiceImpl();
		gson = new Gson();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Errors errors = new Errors();
		request.setAttribute(AttributeNames.ERRORS, errors);

		Map<String, String[]> mapParameter = request.getParameterMap();
		ValidationUtils.setMapParameter(mapParameter);

		String actionName = request.getParameter(ParameterNames.ACTION);
	
		
		if (ActionNames.SOLICITUDES_PENDIENTES.equals(actionName)) {

			UsuarioDTO usuario = (UsuarioDTO)  SessionManager.get(request, AttributeNames.USER);

			try {
				List<SolicitudDTO> solicitudes = solicitudService.findSolicitudesPendientes(usuario.getId());
				String json = gson.toJson(solicitudes);

				// si no sale texto/html hay que indicar el tipo de contenido (MIMETYPE)
				response.setContentType("application/json");
				response.setCharacterEncoding("ISO-8859-1");
				ServletOutputStream sos = response.getOutputStream();
				sos.write(json.getBytes());

				// se indica el final del json y que envie sus datos con flush
				sos.flush();


			} catch (Exception e) {
				logger.error("usuarioId: "+usuario.getId(), e);
			}								


		}else if(ActionNames.SOLICITUD_ACEPTAR.equals(actionName)) {

			Long idUsuario = ValidationUtils.longTransform(errors, request.getParameter(ParameterNames.ID));
			Long idEvento = ValidationUtils.longTransform(errors, request.getParameter(ParameterNames.ID_DOS));

			try {
				solicitudService.updateEstado(idUsuario, idEvento, SolicitudEstado.ACEPTADO);

				String json = gson.toJson("OK");

				
				response.setContentType("application/json");
				response.setCharacterEncoding("ISO-8859-1");
				ServletOutputStream sos = response.getOutputStream();
				sos.write(json.getBytes());

				
				sos.flush();


			} catch (Exception e) {
				logger.error("action: "+ActionNames.SOLICITUD_ACEPTAR+" idEvento :"+idEvento, e);
			}								


		}else if(ActionNames.SOLICITUD_RECHAZAR.equals(actionName)) {

			Long idUsuario = ValidationUtils.longTransform(errors, request.getParameter(ParameterNames.ID));
			Long idEvento = ValidationUtils.longTransform(errors, request.getParameter(ParameterNames.ID_DOS));



			try {
				solicitudService.updateEstado(idUsuario, idEvento, SolicitudEstado.RECHAZADO);

				String json = gson.toJson("OK");

				
				response.setContentType("application/json");
				response.setCharacterEncoding("ISO-8859-1");
				ServletOutputStream sos = response.getOutputStream();
				sos.write(json.getBytes());

				
				sos.flush();


			} catch (Exception e) {
				logger.error("action: "+ActionNames.SOLICITUD_RECHAZAR+" idEvento :"+idEvento, e);
			}
			
			
		}else if(ActionNames.INVITACIONES_PENDIENTES.equals(actionName)) {

			UsuarioDTO usuario = (UsuarioDTO)  SessionManager.get(request, AttributeNames.USER);

			try {
				List<SolicitudDTO> solicitudes = solicitudService.findInvitacionesPendientes(usuario.getId());
				String json = gson.toJson(solicitudes);

				// si no sale texto/html hay que indicar el tipo de contenido (MIMETYPE)
				response.setContentType("application/json");
				response.setCharacterEncoding("ISO-8859-1");
				ServletOutputStream sos = response.getOutputStream();
				sos.write(json.getBytes());

				// se indica el final del json y que envie sus datos con flush
				sos.flush();


			} catch (Exception e) {
				logger.error("usuarioId: "+usuario.getId(), e);
			}								


		}


	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}