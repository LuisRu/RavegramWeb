package com.luis.ravegram.web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.ravegram.model.EventoCriteria;
import com.luis.ravegram.model.EventoDTO;
import com.luis.ravegram.model.UsuarioDTO;
import com.luis.ravegram.service.EventoService;
import com.luis.ravegram.service.impl.EventoServiceImpl;
import com.luis.ravegram.web.controller.util.ActionNames;
import com.luis.ravegram.web.controller.util.AttributeNames;
import com.luis.ravegram.web.controller.util.ParameterNames;
import com.luis.ravegram.web.controller.util.ViewPaths;

/**
 * Controlador (Servlet) para peteciones de eventos
 */
@WebServlet("/evento")
public class EventoServlet extends HttpServlet {

	private static Logger logger = LogManager.getLogger(EventoServlet.class);

	private EventoService eventoService = null;
	

	public EventoServlet() {
		super();
		eventoService = new EventoServiceImpl();
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		String targetView = null;

		String action = request.getParameter(ParameterNames.ACTION);

		if (ActionNames.EVENT_SEARCH.equalsIgnoreCase(action)) {	
			//BUSQUEDA CRITERIA
			String publicPrivadoStr = request.getParameter(ParameterNames.PUBLIC_PRIV);
			String fechaStr = request.getParameter(ParameterNames.FECHA_HORA);
			String edadMinStr = request.getParameter(ParameterNames.EDAD_MIN);
			String edadMaxStr = request.getParameter(ParameterNames.EDAD_MAX);
			String distanciaStr = request.getParameter(ParameterNames.DISTANCIA);
			String tipoTematicaStr = request.getParameter(ParameterNames.TIPO_TEMATICA);
			String tipoMusicaStr = request.getParameter(ParameterNames.TIPO_MUSICA);
			String tipoEstablecimientoStr = request.getParameter(ParameterNames.TIPO_ESTABLECIMIENTO);
			String tipoEstadoEventoStr = request.getParameter(ParameterNames.TIPO_ESTADO_EVENTO);

			EventoCriteria ec = new EventoCriteria();
			ec.setPublicPrivado(null);
			ec.setFecha(null);
			ec.setEdadMax(null);
			ec.setEdadMin(null);
			ec.setDistancia(Integer.valueOf(distanciaStr));
			ec.setTipoTematica(null);
			ec.setTipoMusica(null);
			ec.setTipoEstablecimiento(null);
			ec.setTipoEstadoEvento(null);

			try {
				UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute(AttributeNames.USER);
				List<EventoDTO> eventos = eventoService.findByCriteria(ec, usuario.getId(), usuario.getLatitud(),usuario.getLongitud(), 10, 10);	
				request.setAttribute(AttributeNames.EVENTS, eventos);

				targetView = ViewPaths.EVENT_RESULT;

			}catch (Exception e) {
				logger.error("Search: ",e.getMessage(),e);
				targetView = ViewPaths.HOME;
			}					
		} 
			
		request.getRequestDispatcher(targetView).forward(request, response);

		//Convertir y validar

		//Acceder a la capa de negocio

		//Pintar los resultados

		//EventoDTO evento = eventoService.findById(1L);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
