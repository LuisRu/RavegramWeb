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
import com.luis.ravegram.model.EventoDTO;
import com.luis.ravegram.model.Results;
import com.luis.ravegram.model.TipoEstadoEvento;
import com.luis.ravegram.model.UsuarioDTO;
import com.luis.ravegram.model.criteria.EventoCriteria;
import com.luis.ravegram.model.state.EventoEstado;
import com.luis.ravegram.service.EventoService;
import com.luis.ravegram.service.TipoEstadoEventoService;
import com.luis.ravegram.service.impl.EventoServiceImpl;
import com.luis.ravegram.service.impl.TipoEstadoEventoServiceImpl;
import com.luis.ravegram.web.controller.util.ActionNames;
import com.luis.ravegram.web.controller.util.AttributeNames;
import com.luis.ravegram.web.controller.util.ParameterNames;
import com.luis.ravegram.web.controller.util.SessionManager;


@WebServlet("/evento-service")
public class EventoWebServiceServlet extends HttpServlet {

	private static Logger logger = LogManager.getLogger(EventoWebServiceServlet.class);

	private TipoEstadoEventoService tipoEstadoEventoService = null;
	private EventoService eventoService = null;
	private Gson gson = null; 

	public EventoWebServiceServlet() {
		super();
		tipoEstadoEventoService = new TipoEstadoEventoServiceImpl();
		eventoService = new EventoServiceImpl();
		gson = new Gson();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		String actionStr = request.getParameter(ParameterNames.ACTION);

		// TODO rest style
		if (ActionNames.TIPO_ESTADO_EVENTO.equals(actionStr)) {
			
			List<TipoEstadoEvento> tipoEstadoEvento = null;

			try {
				tipoEstadoEvento = tipoEstadoEventoService.findAll();
				String json = gson.toJson(tipoEstadoEvento);

				// si no sale texto/html hay que indicar el tipo de contenido (MIMETYPE)
				response.setContentType("application/json");
				response.setCharacterEncoding("ISO-8859-1");
				ServletOutputStream sos = response.getOutputStream();
				sos.write(json.getBytes());

				// se indica el final del json y que envie sus datos con flush
				sos.flush();


			} catch (Exception e) {
				logger.error("TiposEstadoEvento: ", e);
			}								

			

		}else if(ActionNames.EVENT_DISPO.equals(actionStr)) {
			
			UsuarioDTO usuario = (UsuarioDTO) SessionManager.get(request,AttributeNames.USER);
			try {
				EventoCriteria ec = new EventoCriteria();
				ec.setIdCreador(usuario.getId());
				ec.setTipoEstadoEvento(EventoEstado.APTO);
				ec.setLatitudBuscador(usuario.getLatitud());
				ec.setLongitudBuscador(usuario.getLongitud());
				Results<EventoDTO> results= eventoService.findByCriteria(ec, 1, 10);
				String json = gson.toJson(results.getData());

				// si no sale texto/html hay que indicar el tipo de contenido (MIMETYPE)
				response.setContentType("application/json");
				response.setCharacterEncoding("ISO-8859-1");
				ServletOutputStream sos = response.getOutputStream();
				sos.write(json.getBytes());

				// se indica el final del json y que envie sus datos con flush
				sos.flush();


			} catch (Exception e) {
				logger.error("EventosDisponibles: ", e);
			}								
		}

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}