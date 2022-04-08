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
import com.luis.ravegram.model.UsuarioDTO;
import com.luis.ravegram.service.EventoService;
import com.luis.ravegram.service.UsuarioService;
import com.luis.ravegram.service.UsuarioSigueService;
import com.luis.ravegram.service.impl.EventoServiceImpl;
import com.luis.ravegram.service.impl.UsuarioServiceImpl;
import com.luis.ravegram.service.impl.UsuarioSigueServiceImpl;
import com.luis.ravegram.web.controller.util.ActionNames;
import com.luis.ravegram.web.controller.util.AttributeNames;
import com.luis.ravegram.web.controller.util.ControllerPaths;
import com.luis.ravegram.web.controller.util.CookieManager;
import com.luis.ravegram.web.controller.util.Estados;
import com.luis.ravegram.web.controller.util.ParameterNames;
import com.luis.ravegram.web.controller.util.SessionManager;
import com.luis.ravegram.web.controller.util.ViewPaths;
import com.luis.ravegram.web.util.ParametersUtil;

/**
 * Controlador (Servlet) para peticiones de usuario
 * autenticadas.
 */
@WebServlet("/private/usuario")
public class PrivateUsuarioServlet extends HttpServlet {

	private static Logger logger = LogManager.getLogger(PrivateUsuarioServlet.class);

	private UsuarioService usuarioService = null;
	private EventoService eventoService = null;
	private UsuarioSigueService usuarioSigueService = null;


	public PrivateUsuarioServlet() {
		super();
		usuarioService = new UsuarioServiceImpl();
		eventoService = new EventoServiceImpl();
		usuarioSigueService = new UsuarioSigueServiceImpl();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//CommandManager.getInstance().doAction(request, response);Errors errors = new Errors();
		Errors errors = new Errors();
		request.setAttribute(AttributeNames.ERRORS, errors);
		String targetView = null;
		boolean forward = true;
		String actionName = request.getParameter(ParameterNames.ACTION);

		if (logger.isInfoEnabled()) {
			logger.info("Processing action "+actionName);
		}
		if (ActionNames.USER_DETAIL.equalsIgnoreCase(actionName)) {
			// Ver el perfil de un usuario desde otro 


			String idUsuarioStr = request.getParameter(ParameterNames.ID);							
			Long usuarioId = Long.valueOf(idUsuarioStr); // unsafe conversion
			
			try {			
				UsuarioDTO usuarioBusqueda = usuarioService.findById(usuarioId);
				request.setAttribute(AttributeNames.USER, usuarioBusqueda);
				request.setAttribute(AttributeNames.ACTION, actionName);
				request.setAttribute(AttributeNames.ID, usuarioId);
			} catch (Exception e) {
				logger.error("Profile: ",e.getMessage(),e);
			}

			targetView = ViewPaths.USER_PROFILE;

		} else if (ActionNames.USER_MY_PROFILE.equalsIgnoreCase(actionName)) {

	
			request.setAttribute(AttributeNames.USER, SessionManager.get(request, AttributeNames.USER));
			request.setAttribute(AttributeNames.ACTION, actionName);
			targetView = ViewPaths.USER_PROFILE;

			
		}else if (ActionNames.USER_DELETE.equalsIgnoreCase(actionName)) {	
			
			targetView=ViewPaths.USER_LOGIN;
			
			
			UsuarioDTO usuario = (UsuarioDTO) SessionManager.get(request, AttributeNames.USER);
			
			
			try {
				usuarioService.updateEstado(usuario.getId(),Estados.USER_DELETE);
				
				CookieManager.setValue(response, AttributeNames.USER, Strings.EMPTY, -1);
				SessionManager.set(request, AttributeNames.USER, null);
				
			}catch (Exception e) {
				// TODO: handle exception
			}
			
		}else if (ActionNames.USER_FOLLOW.equalsIgnoreCase(actionName)) {

			targetView = ViewPaths.HOME;

			String idSeguidoStr = request.getParameter(ParameterNames.ID);
			UsuarioDTO usuario = (UsuarioDTO) SessionManager.get(request, AttributeNames.USER);


			try {

				usuarioSigueService.follow(usuario.getId(), Long.valueOf(idSeguidoStr));

				//actualizamos la lista de seguidos
				Set <Long> idsSeguidos = usuarioService.findSeguidosIds(usuario.getId());
				SessionManager.set(request, AttributeNames.FOLLOWING, idsSeguidos);

				// redirect al detalle del usuario
				targetView = ControllerPaths.PRIVATE_USER+"?action="+ActionNames.USER_DETAIL+"&"
								+ParameterNames.ID+"="+idSeguidoStr;
				forward = false;
				
			} catch (DataException de) {
				logger.error("Follow: "+idSeguidoStr,de.getMessage(), de);
				errors.addCommonError("Ha ocurrido un problema al consultar sus datos. Por favor inténtelo mas tarde.");
			} catch (Exception e) {
				logger.error("Follow: ",e.getMessage(),e);
				errors.addCommonError("Se ha producido un problema. Por favor intentelo de nuevo mas tarde.");
			}	

		}else if (ActionNames.USER_UNFOLLOW.equalsIgnoreCase(actionName)) {

			targetView = ViewPaths.HOME;

			String idSeguidoStr = ParametersUtil.getValue(request, ParameterNames.ID);
			UsuarioDTO usuario = (UsuarioDTO) SessionManager.get(request, AttributeNames.USER);


			try {

				usuarioSigueService.unFollow(usuario.getId(),Long.valueOf(idSeguidoStr) );

				//actualizamos la lista de seguidos
				Set <Long> idsSeguidos = usuarioService.findSeguidosIds(usuario.getId());
				SessionManager.set(request, AttributeNames.FOLLOWING, idsSeguidos);
				
				// redirect al detalle del usuario
				
				Map<String, String> userDetailParams = new HashMap<String, String>();
				userDetailParams.put(ParameterNames.ACTION, ActionNames.USER_DETAIL);
				userDetailParams.put(ParameterNames.ID, idSeguidoStr);
												
				targetView = ParametersUtil.getURL(ControllerPaths.PRIVATE_USER, userDetailParams); 
				forward = false;
	
				
			} catch (DataException de) {
				logger.error("Follow: "+idSeguidoStr,de.getMessage(), de);
				errors.addCommonError("Ha ocurrido un problema al consultas sus datos. Por favor inténtelo mas tarde.");
			} catch (Exception e) {
				logger.error("Follow: ",e.getMessage(),e);
				errors.addCommonError("Se ha producido un problema. Por favor intentelo de nuevo mas tarde.");
			}	
		

		} else if (ActionNames.USER_LOGOUT.equalsIgnoreCase(actionName)) {
			//LOGOUT
			if (logger.isInfoEnabled()) {
				UsuarioDTO usuario = (UsuarioDTO) SessionManager.get(request, AttributeNames.USER);
				logger.info("Logging out "+usuario.getEmail());
			}
			targetView = ViewPaths.USER_LOGIN;
			forward = false;
			
			CookieManager.setValue(response, AttributeNames.USER, Strings.EMPTY, -1);
			SessionManager.set(request, AttributeNames.USER, null);
			
			
			
		} else if (ActionNames.USER_UPDATE.equalsIgnoreCase(actionName)) {
			//UPDATE
			targetView = ViewPaths.USER_UPDATE;
			
			String userNameStr = request.getParameter(ParameterNames.USER_NAME);
			String contraseñaStr = request.getParameter(ParameterNames.PASSWORD);
			String fechaNacimiento = request.getParameter(ParameterNames.FECHA_NACIMIENTO);
			String sexoStr = request.getParameter(ParameterNames.SEXO);
			String biografiaStr = request.getParameter(ParameterNames.BIOGRAFIA);
			
			UsuarioDTO usuario = new UsuarioDTO();
			usuario.setUserName(userNameStr);
			usuario.setContrasena(null);
			usuario.setFechaNacimiento(null);
			usuario.setSexo(null);
			usuario.setBiografia(biografiaStr);
			
			
			try {
				usuarioService.update(usuario);
				SessionManager.set(request, AttributeNames.USER, usuario);
			}catch (Exception e) {
				// TODO: handle exception
			}
			
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
