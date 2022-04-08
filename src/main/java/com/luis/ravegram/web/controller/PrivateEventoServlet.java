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
import com.luis.ravegram.model.UsuarioEventoPuntuaDTO;
import com.luis.ravegram.service.EventoService;
import com.luis.ravegram.service.PuntuacionService;
import com.luis.ravegram.service.UsuarioService;
import com.luis.ravegram.service.impl.EventoServiceImpl;
import com.luis.ravegram.service.impl.PuntuacionServiceImpl;
import com.luis.ravegram.service.impl.UsuarioServiceImpl;
import com.luis.ravegram.web.controller.util.ActionNames;
import com.luis.ravegram.web.controller.util.AttributeNames;
import com.luis.ravegram.web.controller.util.ParameterNames;
import com.luis.ravegram.web.controller.util.SessionManager;
import com.luis.ravegram.web.controller.util.ViewPaths;

/**
 * Controlador (Servlet) para peteciones de eventos que 
 * requieren autentication.
 */
@WebServlet("/private/evento")
public class PrivateEventoServlet extends HttpServlet {

	private static Logger logger = LogManager.getLogger(PrivateEventoServlet.class);

	private EventoService eventoService = null;
	private UsuarioService usuarioService = null;
	private PuntuacionService puntuacionesService = null;

	public PrivateEventoServlet() {
		super();
		eventoService = new EventoServiceImpl();
		usuarioService = new UsuarioServiceImpl();
		puntuacionesService = new PuntuacionServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		String targetView = null;

		String action = request.getParameter(ParameterNames.ACTION);
		
		if (ActionNames.EVENT_DETAIL.equalsIgnoreCase(action)) {
			//VISTA EN DETALLE
			
			
			String updateStr = request.getParameter(ParameterNames.UPDATE);
			String eventoIdStr = request.getParameter(ParameterNames.ID);
			
			
			Boolean update = Boolean.valueOf(updateStr);
			Long id = Long.valueOf(eventoIdStr);
			
			UsuarioDTO usuario = (UsuarioDTO) SessionManager.get(request, AttributeNames.USER);
			
			try {
				
				EventoDTO evento = eventoService.findById(id, usuario.getLatitud(),usuario.getLongitud());
				
				List<UsuarioDTO> usuarios = usuarioService.findAsistentes(Long.valueOf(eventoIdStr));
				List<UsuarioEventoPuntuaDTO> puntuaciones = puntuacionesService.findByEvento(Long.valueOf(eventoIdStr));
				
				request.setAttribute(AttributeNames.PUNTUACIONES, puntuaciones);
				request.setAttribute(AttributeNames.EVENT, evento);
				request.setAttribute(AttributeNames.USERS, usuarios);
				
				
				if(update==true) {
					targetView=ViewPaths.EVENT_UPDATE;
				}else {
					targetView = ViewPaths.EVENT_DETAIL;
				}
				
			} catch (Exception e) {
				logger.error("Detail: ",e.getMessage(),e);
			}			

			
			
		}else if (ActionNames.EVENT_CREATE.equalsIgnoreCase(action)) {
			//CREATE
			
			UsuarioDTO usuario = (UsuarioDTO) SessionManager.get(request, AttributeNames.USER);
			
			
			String nombreStr = request.getParameter(ParameterNames.NOMBRE_EVENTO);
			String descripcionStr = request.getParameter(ParameterNames.DESCRIPCION);
			String fechaHoraStr = request.getParameter(ParameterNames.FECHA_HORA);
			String numAsistentesStr = request.getParameter(ParameterNames.NUM_ASISENTES);
			String edadMaxStr = request.getParameter(ParameterNames.EDAD_MAX);
			String edadMinStr = request.getParameter(ParameterNames.EDAD_MIN);
			String publicPrivadoStr = request.getParameter(ParameterNames.PUBLIC_PRIV);
			String calleStr = request.getParameter(ParameterNames.CALLE);
			String zipStr = request.getParameter(ParameterNames.ZIP);
			String tipoTematicaStr = request.getParameter(ParameterNames.TIPO_TEMATICA);
			String tipoMusicaStr = request.getParameter(ParameterNames.TIPO_MUSICA);
			String tipoEstablecimientoStr = request.getParameter(ParameterNames.TIPO_ESTABLECIMIENTO);
			String localidadStr = request.getParameter(ParameterNames.LOCALIDAD);
			String establecimientoStr = request.getParameter(ParameterNames.ESTABLECIMIENTO);
		

			try {
				EventoDTO evento = new EventoDTO();
				evento.setIdUsuario(usuario.getId());
				evento.setNombre(nombreStr);
				evento.setDescripcion(descripcionStr);
				evento.setFechaHora(null);
				evento.setNumAsistentes(null);
				evento.setEdadeDesde(null);
				evento.setEdadHasta(null);
				evento.setPublicoPrivado(null);
				evento.setCalle(calleStr);
				evento.setZip(zipStr);
				evento.setIdTipoTematica(null);
				evento.setIdTipoEstablecimiento(null);
				evento.setIdLocalidad(null);
				evento.setIdEstablecimiento(null);
				evento.setIdTipoMusica(null);

				eventoService.create(evento);
				
				
				
			} catch (Exception e) {
				logger.error("Create: ",e.getMessage(),e);
			}			
			
			
		}else if(ActionNames.EVENT_UPDATE.equalsIgnoreCase(action)) {
			
			String nombreStr = request.getParameter(ParameterNames.NOMBRE_EVENTO);
			String descripcionStr = request.getParameter(ParameterNames.DESCRIPCION);
			String fechaHoraStr = request.getParameter(ParameterNames.FECHA_HORA);
			String numAsistentesStr = request.getParameter(ParameterNames.NUM_ASISENTES);
			String edadMaxStr = request.getParameter(ParameterNames.EDAD_MAX);
			String edadMinStr = request.getParameter(ParameterNames.EDAD_MIN);
			String publicPrivadoStr = request.getParameter(ParameterNames.PUBLIC_PRIV);
			String calleStr = request.getParameter(ParameterNames.CALLE);
			String zipStr = request.getParameter(ParameterNames.ZIP);
			String tipoTematicaStr = request.getParameter(ParameterNames.TIPO_TEMATICA);
			String tipoMusicaStr = request.getParameter(ParameterNames.TIPO_MUSICA);
			String tipoEstablecimientoStr = request.getParameter(ParameterNames.TIPO_ESTABLECIMIENTO);
			String localidadStr = request.getParameter(ParameterNames.LOCALIDAD);
			String establecimientoStr = request.getParameter(ParameterNames.ESTABLECIMIENTO);
		

			try {
				EventoDTO evento = new EventoDTO();
				evento.setNombre(nombreStr);
				evento.setDescripcion(descripcionStr);
				evento.setFechaHora(null);
				evento.setNumAsistentes(null);
				evento.setEdadeDesde(null);
				evento.setEdadHasta(null);
				evento.setPublicoPrivado(null);
				evento.setCalle(calleStr);
				evento.setZip(zipStr);
				evento.setIdTipoTematica(null);
				evento.setIdTipoEstablecimiento(null);
				evento.setIdLocalidad(null);
				evento.setIdEstablecimiento(null);
				evento.setIdTipoMusica(null);

				eventoService.update(evento);
				
				
				
			} catch (Exception e) {
				logger.error("Create: ",e.getMessage(),e);
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
