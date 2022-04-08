package com.luis.ravegram.web.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.exception.InvalidUserOrPasswordException;
import com.luis.ravegram.exception.MailException;
import com.luis.ravegram.exception.ServiceException;
import com.luis.ravegram.exception.UserAlreadyExistsException;
import com.luis.ravegram.exception.UserDeleteException;
import com.luis.ravegram.model.Results;
import com.luis.ravegram.model.UsuarioCriteria;
import com.luis.ravegram.model.UsuarioDTO;
import com.luis.ravegram.service.EventoService;
import com.luis.ravegram.service.UsuarioService;
import com.luis.ravegram.service.UsuarioSigueService;
import com.luis.ravegram.service.impl.EventoServiceImpl;
import com.luis.ravegram.service.impl.UsuarioServiceImpl;
import com.luis.ravegram.service.impl.UsuarioSigueServiceImpl;
import com.luis.ravegram.web.controller.util.ActionNames;
import com.luis.ravegram.web.controller.util.AttributeNames;
import com.luis.ravegram.web.controller.util.CookieManager;
import com.luis.ravegram.web.controller.util.ParameterNames;
import com.luis.ravegram.web.controller.util.SessionManager;
import com.luis.ravegram.web.controller.util.ViewPaths;

/**
 * Controlador (Servlet) para peticiones de usuario
 */
@WebServlet("/usuario")
public class UsuarioServlet extends HttpServlet {

	private static Logger logger = LogManager.getLogger(UsuarioServlet.class);

	private UsuarioService usuarioService = null;
	private EventoService eventoService = null;
	private UsuarioSigueService usuarioSigueService = null;


	public UsuarioServlet() {
		super();
		usuarioService = new UsuarioServiceImpl();
		eventoService = new EventoServiceImpl();
		usuarioSigueService = new UsuarioSigueServiceImpl();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		String targetView = null;
		boolean forward = false;
		
		//CommandManager.getInstance().doAction(request, response);Errors errors = new Errors();
		Errors errors = new Errors();
		request.setAttribute(AttributeNames.ERRORS, errors);

		
		String actionName = request.getParameter(ParameterNames.ACTION);

		if(ActionNames.USER_SEARCH.equalsIgnoreCase(actionName)) {
			//BUSQUEDA CRITERIA
			UsuarioDTO usuario = (UsuarioDTO) SessionManager.get(request, AttributeNames.USER);

			String edadMinStr = request.getParameter(ParameterNames.EDAD_MIN);
			String edadMaxStr = request.getParameter(ParameterNames.EDAD_MAX);
			String distanciaStr = request.getParameter(ParameterNames.DISTANCIA);


			Integer distanciaKm = Integer.valueOf(distanciaStr);

			UsuarioCriteria uc = new UsuarioCriteria();
			uc.setIdBuscador(usuario.getId());
			uc.setEdadDesde(null);
			uc.setEdadHasta(null);
			uc.setDistanciaKm(distanciaKm);

			try {
				Results<UsuarioDTO> usuarios = usuarioService.findByCriteria(uc,10,10);
				request.setAttribute(AttributeNames.USERS, usuarios);

				targetView =ViewPaths.USER_RESULT;

			}catch (Exception e) {
				logger.error("Search: ",e.getMessage(),e);
			}
			
			
		



		} else if (ActionNames.USER_LOGIN.equalsIgnoreCase(actionName)) {
			
			
			targetView = ViewPaths.USER_LOGIN;
			
			
			//LOGIN
			String emailStr = request.getParameter(ParameterNames.EMAIL);
			String passWordStr = request.getParameter(ParameterNames.PASSWORD);
			String keepAuthenticated = request.getParameter("keep-authenticated");
			
//
//			if (StringUtils.isBlank(emailStr)) {
//				// tratarlo como error
//				
//			}
			try {
				UsuarioDTO usuario = usuarioService.login(emailStr, passWordStr);
				Set <Long> idsSeguidos = usuarioService.findSeguidosIds(usuario.getId());
				
				SessionManager.set(request, AttributeNames.USER, usuario);
				SessionManager.set(request, AttributeNames.FOLLOWING, idsSeguidos);
				
				if (keepAuthenticated!=null) {
					CookieManager.setValue(response, AttributeNames.USER, emailStr, 30*24*60*60); // Agujero!
				} else {
					CookieManager.setValue(response, AttributeNames.USER, emailStr, -1); // Agujero!
				}
				
				targetView = ViewPaths.HOME;
				forward = false;								
				
			}catch (InvalidUserOrPasswordException iue) {
				logger.error("Login: "+emailStr,iue.getMessage(), iue);
				errors.addCommonError("Email o contraseña incorrecto");
			}catch (UserDeleteException ude) {
				logger.error("Login: "+emailStr,ude.getMessage(), ude);
				errors.addCommonError("Este usuario esta eliminado");
			} catch (DataException de) {
				logger.error("Login: "+emailStr,de.getMessage(), de);
				errors.addCommonError("Ha ocurrido un problema al consultas sus datos. Por favor inténtelo mas tarde.");
			}catch (ServiceException iue) {
				logger.error("Login: "+emailStr,iue.getMessage(), iue);
				errors.addCommonError("Ha ocurrido un problema al consultas sus datos. Por favor inténtelo mas tarde.");
			} catch (Exception e) {
				logger.error("Login: ",e.getMessage(),e);
				errors.addCommonError("Se ha producido un problema al autenticarse. Por favor intentelo de nuevo mas tarde.");
			}		


		} else if (ActionNames.USER_CREATE.equalsIgnoreCase(actionName)) {
			
			targetView = ViewPaths.USER_CREATE;
			
			//SINGUP
			String userNameStr = request.getParameter(ParameterNames.USER_NAME);

			if (StringUtils.isBlank(userNameStr)) {
				errors.addParameterError(ParameterNames.USER_NAME,"Campo obligatorio");
			}
			
			
			String emailStr = request.getParameter(ParameterNames.EMAIL);
			String passWordStr = request.getParameter(ParameterNames.PASSWORD);
			if (passWordStr.length()<8) {
				errors.addParameterError(ParameterNames.PASSWORD,"Password invalida. Debe tener ... ");
			}
			String fechaNacimietoStr = request.getParameter(ParameterNames.FECHA_NACIMIENTO);
			String sexoStr = request.getParameter(ParameterNames.SEXO);
			String telefonoStr = request.getParameter(ParameterNames.TELEFONO);
			String biografiaStr = request.getParameter(ParameterNames.BIOGRAFIA);

			Date fechaNacimiento = new Date(); //Date.valueOf(fechaNacimietoStr);


			if (!errors.hasErrors()) {
				try {
					UsuarioDTO usuario = new UsuarioDTO();
					usuario.setUserName(userNameStr);
					usuario.setEmail(emailStr);
					usuario.setContrasena(passWordStr);
					usuario.setFechaNacimiento(fechaNacimiento);
					usuario.setSexo(null);
					usuario.setTelefono(telefonoStr);
					usuario.setBiografia(biografiaStr);


					usuarioService.signUp(usuario);

					targetView = ViewPaths.USER_LOGIN;

				} catch (UserAlreadyExistsException uaee) {
					if (logger.isInfoEnabled()) {
						logger.info(emailStr, uaee);
					}
					errors.addCommonError("El usuario ya existe");
				} catch (MailException me) {
					logger.error(emailStr, me);
					errors.addCommonError("No se ha podido enviar el e-mail. Por favor revise si es correcto o inténtelo mas tarde.");

				} catch (Exception e) {
					logger.error("SingUp: ",e.getMessage(),e);
					errors.addCommonError("Ha ocurrido un problema al guardar los datos. Por favor reinténtelo más tarde.");
				}

			}
		}
		
		logger.info(forward?"Forwarding to "+targetView:"Redirecting to "+targetView);
		
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
