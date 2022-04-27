package com.luis.ravegram.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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

		// CommandManager.getInstance().doAction(request, response);Errors errors = new
		// Errors();

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

			String distanciaStr = request.getParameter(ParameterNames.DISTANCIA);
			String edadMaxStr = request.getParameter(ParameterNames.EDAD_MAX);
			String edadMinStr = request.getParameter(ParameterNames.EDAD_MIN);

			UsuarioCriteria uc = new UsuarioCriteria();
			uc.setDistanciaKm(null);
			uc.setEdadHasta(null);// maxima
			uc.setEdadDesde(null);

			try {
				Results<UsuarioDTO> usuarios = usuarioService.findByCriteria(uc, 1, 10);
				request.setAttribute(AttributeNames.USERS, usuarios.getData());

				targetView = ViewPaths.USER_RESULT;

			} catch (DataException de) {
				logger.error("EventSearch: ", de.getMessage(), de);
				errors.addCommonError(ErrorsNames.ERROR_DATA_EXCEPTION);

			}

		} else if (ActionNames.USER_DELETE.equalsIgnoreCase(actionName)) {
			// delete user

			targetView = ViewPaths.USER_LOGIN;

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
			// my perfil
			UsuarioDTO usuario = (UsuarioDTO) SessionManager.get(request, AttributeNames.USER);

			try {
				// buscar eventos disponibles
				EventoCriteria ec = new EventoCriteria();
				ec.setIdCreador(usuario.getId());
				ec.setTipoEstadoEvento(EventoEstado.APTO);
				ec.setLatitudBuscador(usuario.getLatitud());
				ec.setLongitudBuscador(usuario.getLongitud());
				Results<EventoDTO> resultsEventosDisponibles = eventoService.findByCriteria(ec, 1, 10);

				// buscamos todas las puntuaciones que recibio los eventos un usuario
				Results<UsuarioEventoPuntuaDTO> resultsPuntuaciones = puntuacionService
						.findByEventosUsuario(usuario.getId(), 1, 10);

				request.setAttribute(AttributeNames.EVENTS, resultsEventosDisponibles.getData());
				request.setAttribute(AttributeNames.PUNTUACIONES, resultsPuntuaciones.getData());

				targetView = ViewPaths.USER_MY_PROFILE;

			} catch (DataException de) {
				logger.error("UserMyProfile: ", de.getMessage(), de);
				errors.addCommonError(ErrorsNames.ERROR_DATA_EXCEPTION);
			}

		} else if (ActionNames.USER_FOLLOW.equalsIgnoreCase(actionName)) {
			// follow

			targetView = ViewPaths.HOME;

			String idSeguidoStr = request.getParameter(ParameterNames.ID);
			UsuarioDTO usuario = (UsuarioDTO) SessionManager.get(request, AttributeNames.USER);

			try {

				usuarioSigueService.follow(usuario.getId(), Long.valueOf(idSeguidoStr));

				Set<Long> idsSeguidos = usuarioService.findSeguidosIds(usuario.getId());
				SessionManager.set(request, AttributeNames.FOLLOWING, idsSeguidos);

				Map<String, String> userDetailParams = new HashMap<String, String>();
				userDetailParams.put(ParameterNames.ACTION, ActionNames.USER_DETAIL);
				userDetailParams.put(ParameterNames.ID, idSeguidoStr);

				targetView = ParametersUtil.getURL(ControllerPaths.USER, userDetailParams);
				forward = false;

			} catch (DataException de) {
				logger.error("Follow: " + idSeguidoStr, de.getMessage(), de);
				errors.addCommonError(ErrorsNames.ERROR_DATA_EXCEPTION);
			}

		} else if (ActionNames.USER_UNFOLLOW.equalsIgnoreCase(actionName)) {
			// unfollow

			targetView = ViewPaths.HOME;

			String idSeguidoStr = ParametersUtil.getValue(request, ParameterNames.ID);
			UsuarioDTO usuario = (UsuarioDTO) SessionManager.get(request, AttributeNames.USER);

			try {

				usuarioSigueService.unFollow(usuario.getId(), Long.valueOf(idSeguidoStr));

				Set<Long> idsSeguidos = usuarioService.findSeguidosIds(usuario.getId());
				SessionManager.set(request, AttributeNames.FOLLOWING, idsSeguidos);

				Map<String, String> userDetailParams = new HashMap<String, String>();
				userDetailParams.put(ParameterNames.ACTION, ActionNames.USER_DETAIL);
				userDetailParams.put(ParameterNames.ID, idSeguidoStr);

				targetView = ParametersUtil.getURL(ControllerPaths.USER, userDetailParams);
				forward = false;

			} catch (DataException de) {
				logger.error("UnFollow: " + idSeguidoStr, de.getMessage(), de);
				errors.addCommonError(ErrorsNames.ERROR_DATA_EXCEPTION);
			}

		} else if (ActionNames.USER_LOGOUT.equalsIgnoreCase(actionName)) {
			// LOGOUT

			if (logger.isInfoEnabled()) {
				UsuarioDTO usuario = (UsuarioDTO) SessionManager.get(request, AttributeNames.USER);
				logger.info("Logging out " + usuario.getEmail());
			}

			targetView = ViewPaths.USER_LOGIN;
			forward = false;

			CookieManager.setValue(response, AttributeNames.USER, Strings.EMPTY, -1);
			SessionManager.set(request, AttributeNames.USER, null);

		} else if (ActionNames.USER_UPDATE.equalsIgnoreCase(actionName)) {
			// UPDATE

			UsuarioDTO usuario = (UsuarioDTO) SessionManager.get(request, AttributeNames.USER);

			targetView = ViewPaths.USER_UPDATE;

			UsuarioDTO usuarioCrear = usuario;
			usuarioCrear.setUserName(ValidationUtils.userName(errors, ParameterNames.USER_NAME));
			usuarioCrear.setContrasena(usuario.getContrasena());
			System.out.println(usuarioCrear.getContrasena());
			usuarioCrear.setFechaNacimiento(ValidationUtils.fecha(errors, ParameterNames.FECHA_NACIMIENTO));
			usuarioCrear.setSexo(ValidationUtils.sexo(errors, ParameterNames.SEXO));
			usuarioCrear.setBiografia(ValidationUtils.biografia(errors, ParameterNames.BIOGRAFIA));
			// TODO
			usuarioCrear.setTelefono("698166923");
			usuarioCrear.setLatitud(22.00000D);
			usuarioCrear.setLongitud(22.00000D);
			// usuarioCrear.setEmail(usuario.getEmail());
			// usuarioCrear.setTipoEstadoCuenta(usuario.getTipoEstadoCuenta());

			if (!errors.hasErrors()) {
				try {
					usuarioService.update(usuarioCrear, usuario);
					SessionManager.set(request, AttributeNames.USER, usuarioCrear);
					targetView = ViewPaths.HOME;
				} catch (UserNotFoundException unfe) {
					logger.error("UserUpdate: ", unfe.getMessage(), unfe);
					errors.addCommonError(ErrorsNames.ERROR_USER_NOT_FOUND_EXCEPTION);
				} catch (DataException de) {
					logger.error("UserUpdate: ", de.getMessage(), de);
					errors.addCommonError(ErrorsNames.ERROR_DATA_EXCEPTION);
				}
			}
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
