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
import com.luis.ravegram.model.SolicitudEstado;
import com.luis.ravegram.model.UsuarioDTO;
import com.luis.ravegram.service.SolicitudService;
import com.luis.ravegram.service.impl.SolicitudServiceImpl;
import com.luis.ravegram.web.controller.util.ActionNames;
import com.luis.ravegram.web.controller.util.AttributeNames;
import com.luis.ravegram.web.controller.util.ParameterNames;
import com.luis.ravegram.web.controller.util.SessionManager;
import com.mysql.cj.protocol.ValueDecoder;


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
				List<SolicitudDTO> solicitudes = solicitudService.solicitudesAMisEventosPendientes(usuario.getId(), usuario.getLatitud(),usuario.getLongitud());
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
			
			String idUsuarioStr = request.getParameter(ParameterNames.ID);
			String idEventoStr = request.getParameter(ParameterNames.ID_DOS); 

			try {
				solicitudService.updateEstadoSolicitudesEvento(Long.valueOf(idUsuarioStr),Long.valueOf(idEventoStr),SolicitudEstado.ACEPTADO);
				
				String json = gson.toJson("OK");

				// si no sale texto/html hay que indicar el tipo de contenido (MIMETYPE)
				response.setContentType("application/json");

				ServletOutputStream sos = response.getOutputStream();
				sos.write(json.getBytes());

				// se indica el final del json y que envie sus datos con flush
				sos.flush();


			} catch (Exception e) {
				logger.error("usuarioId: "+idUsuarioStr, e);
			}								

			
		}else if(ActionNames.SOLICITUD_RECHAZAR.equals(actionStr)) {
			
			String idUsuarioStr = request.getParameter(ParameterNames.ID);
			String idEventoStr = request.getParameter(ParameterNames.ID_DOS); 

			try {
				solicitudService.updateEstadoSolicitudesEvento(Long.valueOf(idUsuarioStr),Long.valueOf(idEventoStr),SolicitudEstado.ACEPTADO);
				
				String json = gson.toJson("OK");

				// si no sale texto/html hay que indicar el tipo de contenido (MIMETYPE)
				response.setContentType("application/json");

				ServletOutputStream sos = response.getOutputStream();
				sos.write(json.getBytes());

				// se indica el final del json y que envie sus datos con flush
				sos.flush();


			} catch (Exception e) {
				logger.error("usuarioId: "+idUsuarioStr, e);
			}					
		}

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}