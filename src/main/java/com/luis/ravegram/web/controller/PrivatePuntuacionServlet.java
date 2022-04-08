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

		String action = request.getParameter(ParameterNames.ACTION);
		
		if (ActionNames.PUNTUACION_CREATE.equalsIgnoreCase(action)) {
			//VISTA EN DETALLE
			
			UsuarioDTO usuario = (UsuarioDTO) SessionManager.get(request, AttributeNames.USER);
			String comentarioStr = request.getParameter(ParameterNames.COMENTARIO);
			String valoracionStr = request.getParameter(ParameterNames.VALORACION);
			String idEventoStr = request.getParameter(ParameterNames.ID);
			
			
			try {
				targetView=ViewPaths.HOME;
				
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
