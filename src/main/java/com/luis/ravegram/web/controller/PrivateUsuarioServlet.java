package com.luis.ravegram.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;

import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.exception.UserNotFoundException;
import com.luis.ravegram.model.EventoDTO;
import com.luis.ravegram.model.Results;
import com.luis.ravegram.model.UsuarioDTO;
import com.luis.ravegram.model.UsuarioEventoPuntuaDTO;
import com.luis.ravegram.model.criteria.EventoCriteria;
import com.luis.ravegram.model.criteria.UsuarioCriteria;
import com.luis.ravegram.model.state.EventoEstado;
import com.luis.ravegram.service.EventoService;
import com.luis.ravegram.service.PuntuacionService;
import com.luis.ravegram.service.UsuarioService;
import com.luis.ravegram.service.UsuarioSigueService;
import com.luis.ravegram.service.impl.EventoServiceImpl;
import com.luis.ravegram.service.impl.PuntuacionServiceImpl;
import com.luis.ravegram.service.impl.UsuarioServiceImpl;
import com.luis.ravegram.service.impl.UsuarioSigueServiceImpl;
import com.luis.ravegram.web.controller.util.ActionNames;
import com.luis.ravegram.web.controller.util.AttributeNames;
import com.luis.ravegram.web.controller.util.ControllerPaths;
import com.luis.ravegram.web.controller.util.CookieManager;
import com.luis.ravegram.web.controller.util.ErrorsNames;
import com.luis.ravegram.web.controller.util.ParameterNames;
import com.luis.ravegram.web.controller.util.SessionManager;
import com.luis.ravegram.web.controller.util.ViewPaths;
import com.luis.ravegram.web.util.ParametersUtil;
import com.luis.ravegram.web.util.ValidationUtils;
import com.luis.ravegram.web.util.WebPaginUtils;

/**
 * Controlador (Servlet) para peticiones de usuario autenticadas.
 */
@WebServlet("/private/usuario")
public class PrivateUsuarioServlet extends HttpServlet {

	private static Logger logger = LogManager.getLogger(PrivateUsuarioServlet.class);

	private UsuarioService usuarioService = null;
	private EventoService eventoService = null;
	private UsuarioSigueService usuarioSigueService = null;
	private PuntuacionService puntuacionService = null;

	public PrivateUsuarioServlet() {
		super();
		usuarioService = new UsuarioServiceImpl();
		eventoService = new EventoServiceImpl();
		usuarioSigueService = new UsuarioSigueServiceImpl();
		puntuacionService = new PuntuacionServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String targetView = null;
		boolean forward = true;

		Errors errors = new Errors();
		request.setAttribute(AttributeNames.ERRORS, errors);

		Map<String, String[]> mapParameter = request.getParameterMap();
		ValidationUtils.setMapParameter(mapParameter);

		String actionName = request.getParameter(ParameterNames.ACTION);

		if (logger.isInfoEnabled()) {
			logger.info("Processing action " + actionName);
		}

		
		
		if (ActionNames.USER_SEARCH.equalsIgnoreCase(actionName)) {
			// BUSQUEDA CRITERIA

			UsuarioDTO usuario = (UsuarioDTO) SessionManager.get(request, AttributeNames.USER);
			targetView=ViewPaths.HOME;
			
			UsuarioCriteria uc = new UsuarioCriteria();
			uc.setEdadHasta(ValidationUtils.fechaEdad(errors, ParameterNames.EDAD_MAX));// maxima
			uc.setEdadDesde(ValidationUtils.fechaEdad(errors, ParameterNames.EDAD_MIN));
			uc.setIdBuscador(usuario.getId());

			try {
				Integer currentPage = WebPaginUtils.getCurrentPage(request);

				Results<UsuarioDTO> results = usuarioService.findByCriteria(uc, (currentPage-1)*1+1, 1);
				request.setAttribute(AttributeNames.USERS, results.getData());
				


				// Atributos para paginacion
				Integer totalPages = WebPaginUtils.getTotalPages(results.getTotal(), 1);
				request.setAttribute(AttributeNames.TOTAL_PAGES, totalPages);
				request.setAttribute(AttributeNames.CURRENT_PAGE, currentPage);
				//el 1 es el numero de paginas q 
				request.setAttribute(AttributeNames.PAGING_FROM, WebPaginUtils.getPageFrom(currentPage, 1, totalPages));
				request.setAttribute(AttributeNames.PAGING_TO, WebPaginUtils.getPageTo(currentPage, 1, totalPages));


				targetView = ViewPaths.USER_RESULT;

			} catch (DataException de) {
				logger.error("EventSearch: ", de.getMessage(), de);
				errors.addCommonError(ErrorsNames.ERROR_DATA_EXCEPTION);

			}

		} else if (ActionNames.USER_DELETE.equalsIgnoreCase(actionName)) {
			// DELETE USER

			targetView = ViewPaths.HOME;

			UsuarioDTO usuario = (UsuarioDTO) SessionManager.get(request, AttributeNames.USER);

			try {

				usuarioService.deleteAll(usuario.getId());

				CookieManager.setValue(response, AttributeNames.USER, Strings.EMPTY, -1);
				SessionManager.set(request, AttributeNames.USER, null);
				

			} catch (UserNotFoundException unfe) {
				logger.error("UserDelete: ", unfe.getMessage(), unfe);
				errors.addCommonError(ErrorsNames.ERROR_DATA_EXCEPTION);
			} catch (DataException de) {
				logger.error("UserDelete: ", de.getMessage(), de);
				errors.addCommonError(ErrorsNames.ERROR_DATA_EXCEPTION);
			}

			
			
		} else if (ActionNames.USER_MY_PROFILE.equalsIgnoreCase(actionName)) {
			// MY PROFILE
			UsuarioDTO usuario = (UsuarioDTO) SessionManager.get(request, AttributeNames.USER);

			try {
				// buscar eventos disponibles
				EventoCriteria ec = new EventoCriteria();
				ec.setIdCreador(usuario.getId());
				ec.setTipoEstadoEvento(EventoEstado.APTO);
				ec.setLatitudBuscador(usuario.getLatitud());
				ec.setLongitudBuscador(usuario.getLongitud());
				
				//eventos dispo
				Results<EventoDTO> resultsEventosDisponibles = eventoService.findByCriteria(ec, 1, 10);
				
				//eventos finalizados
				ec.setTipoEstadoEvento(EventoEstado.FINALIZADO);
				Results<EventoDTO> resultsHistorialResultados = eventoService.findByCriteria(ec, 1, 10);
				

				// buscamos todas las puntuaciones que recibio los eventos un usuario
				Results<UsuarioEventoPuntuaDTO> resultsPuntuaciones = puntuacionService.findByEventosUsuario(usuario.getId(), 1, 10);

				request.setAttribute(AttributeNames.EVENTS, resultsEventosDisponibles.getData());
				request.setAttribute(AttributeNames.EVENTS_DISPO, resultsHistorialResultados.getData());
				request.setAttribute(AttributeNames.PUNTUACIONES, resultsPuntuaciones.getData());

				targetView = ViewPaths.USER_MY_PROFILE;

			} catch (DataException de) {
				logger.error("UserMyProfile: ", de.getMessage(), de);
				errors.addCommonError(ErrorsNames.ERROR_DATA_EXCEPTION);
			}
			
		
		} else if (ActionNames.USER_LOGOUT.equalsIgnoreCase(actionName)) {
			// LOGOUT
			targetView = ViewPaths.HOME;

			//sacamos de la sesion y quitamos cookie
			CookieManager.setValue(response, AttributeNames.USER, Strings.EMPTY, -1);
			SessionManager.set(request, AttributeNames.USER, null);
			
			targetView=ViewPaths.USER_LOGIN;

		} else if (ActionNames.USER_UPDATE.equalsIgnoreCase(actionName)) {
			// UPDATE

			targetView = ViewPaths.USER_UPDATE;
			
			UsuarioDTO usuario = (UsuarioDTO) SessionManager.get(request, AttributeNames.USER);

			//seteo los datos 
			UsuarioDTO usuarioCrear = usuario;
			usuarioCrear.setUserName(ValidationUtils.userName(errors, ParameterNames.USER_NAME));
			usuarioCrear.setContrasena(usuario.getContrasena());
			usuarioCrear.setFechaNacimiento(ValidationUtils.fecha(errors, ParameterNames.FECHA_NACIMIENTO));
			usuarioCrear.setSexo(ValidationUtils.sexo(errors, ParameterNames.SEXO));
			usuarioCrear.setBiografia(ValidationUtils.biografia(errors, ParameterNames.BIOGRAFIA));
			usuarioCrear.setTelefono(ValidationUtils.telefono(errors, ParameterNames.TELEFONO));
			
			

			if (!errors.hasErrors()) {
				try {
					usuarioService.update(usuarioCrear, usuario);
					
					SessionManager.set(request, AttributeNames.USER, usuarioCrear);
					

					//redirecciono a la misma vista
					Map<String, String> userDetailParams = new HashMap<String, String>();
					userDetailParams.put(ParameterNames.ACTION, ActionNames.USER_MY_PROFILE);

					targetView = ParametersUtil.getURL(ControllerPaths.PRIVATE_USER, userDetailParams);
					forward = false;
					
				} catch (UserNotFoundException unfe) {
					logger.error("UserUpdate: ", unfe.getMessage(), unfe);
					errors.addCommonError(ErrorsNames.ERROR_USER_NOT_FOUND_EXCEPTION);
				} catch (DataException de) {
					logger.error("UserUpdate: ", de.getMessage(), de);
					errors.addCommonError(ErrorsNames.ERROR_DATA_EXCEPTION);
				}
			}
		}else {
			targetView = ViewPaths.HOME;
		}

		
		
		logger.info("Redirigiendo a " + targetView);
		if (forward) {
			request.getRequestDispatcher(targetView).forward(request, response);
		} else {
			response.sendRedirect(request.getContextPath() + targetView);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
