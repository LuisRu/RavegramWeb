package com.luis.ravegram.web.service;

import java.io.IOException;
import java.util.List;

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
import com.luis.ravegram.web.controller.util.ActionNames;
import com.luis.ravegram.web.controller.util.AttributeNames;
import com.luis.ravegram.web.controller.util.ParameterNames;
import com.luis.ravegram.web.controller.util.SessionManager;


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


		String actionStr = request.getParameter(ParameterNames.ACTION);

		// TODO rest style
		if (ActionNames.SOLICITUDES_PENDIENTES.equals(actionStr)) {

			UsuarioDTO usuario = (UsuarioDTO)  SessionManager.get(request, AttributeNames.USER);

			try {
				List<SolicitudDTO> solicitudes = solicitudService.findSolicitudesPendientes(usuario.getId());
				String json = gson.toJson(solicitudes);

				// si no sale texto/html hay que indicar el tipo de contenido (MIMETYPE)
				response.setContentType("application/json");

				ServletOutputStream sos = response.getOutputStream();
				sos.write(json.getBytes());

				// se indica el final del json y que envie sus datos con flush
				sos.flush();


			} catch (Exception e) {
				logger.error("usuarioId: "+usuario.getId(), e);
			}								


		}else if(ActionNames.SOLICITUD_ACEPTAR.equals(actionStr)) {

			String idEventoStr = request.getParameter(ParameterNames.ID);

			Long idEvento = Long.valueOf(idEventoStr);

			UsuarioDTO usuario = (UsuarioDTO)  SessionManager.get(request, AttributeNames.USER);


			try {
				solicitudService.updateEstado(usuario.getId(), idEvento, SolicitudEstado.ACEPTADO);

				String json = gson.toJson("OK");

				// si no sale texto/html hay que indicar el tipo de contenido (MIMETYPE)
				response.setContentType("application/json");

				ServletOutputStream sos = response.getOutputStream();
				sos.write(json.getBytes());

				// se indica el final del json y que envie sus datos con flush
				sos.flush();


			} catch (Exception e) {
				logger.error("action: "+ActionNames.SOLICITUD_ACEPTAR+" idEvento :"+idEvento, e);
			}								


		}else if(ActionNames.SOLICITUD_RECHAZAR.equals(actionStr)) {

			String idEventoStr = request.getParameter(ParameterNames.ID);

			Long idEvento = Long.valueOf(idEventoStr);

			UsuarioDTO usuario = (UsuarioDTO)  SessionManager.get(request, AttributeNames.USER);


			try {
				solicitudService.updateEstado(usuario.getId(), idEvento, SolicitudEstado.RECHAZADO);

				String json = gson.toJson("OK");

				// si no sale texto/html hay que indicar el tipo de contenido (MIMETYPE)
				response.setContentType("application/json");

				ServletOutputStream sos = response.getOutputStream();
				sos.write(json.getBytes());

				// se indica el final del json y que envie sus datos con flush
				sos.flush();


			} catch (Exception e) {
				logger.error("action: "+ActionNames.SOLICITUD_RECHAZAR+" idEvento :"+idEvento, e);
			}
		}


	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}