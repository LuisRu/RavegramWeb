package com.luis.ravegram.web.controller;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.exception.InvalidUserOrPasswordException;
import com.luis.ravegram.exception.MailException;
import com.luis.ravegram.exception.UserAlreadyExistsException;
import com.luis.ravegram.exception.UserDeleteException;
import com.luis.ravegram.model.EventoDTO;
import com.luis.ravegram.model.Results;
import com.luis.ravegram.model.UsuarioDTO;
import com.luis.ravegram.model.UsuarioEventoPuntuaDTO;
import com.luis.ravegram.model.criteria.EventoCriteria;
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
import com.luis.ravegram.web.controller.util.CookieManager;
import com.luis.ravegram.web.controller.util.ErrorsNames;
import com.luis.ravegram.web.controller.util.ParameterNames;
import com.luis.ravegram.web.controller.util.SessionManager;
import com.luis.ravegram.web.controller.util.ViewPaths;
import com.luis.ravegram.web.util.ValidationUtils;

/**
 * Controlador (Servlet) para peticiones de usuario
 */
@WebServlet("/usuario")
public class UsuarioServlet extends HttpServlet {

	private static Logger logger = LogManager.getLogger(UsuarioServlet.class);

	private UsuarioService usuarioService = null;
	private EventoService eventoService = null;
	private UsuarioSigueService usuarioSigueService = null;
	private PuntuacionService puntuacionService = null;


	public UsuarioServlet() {
		super();
		usuarioService = new UsuarioServiceImpl();
		eventoService = new EventoServiceImpl();
		usuarioSigueService = new UsuarioSigueServiceImpl();
		puntuacionService = new PuntuacionServiceImpl();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		String targetView = null;
		boolean forward = true;

		Errors errors = new Errors();
		request.setAttribute(AttributeNames.ERRORS, errors);
		
		Map<String, String[]> mapParameter = request.getParameterMap();
		ValidationUtils.setMapParameter(mapParameter);

		String actionName = request.getParameter(ParameterNames.ACTION);
		

		if (logger.isInfoEnabled()) {
			logger.info("Processing action "+actionName);
		}

		if (ActionNames.USER_DETAIL.equalsIgnoreCase(actionName)) {
			// detail
			
			String idUsuarioStr = request.getParameter(ParameterNames.ID);							
			Long idUsuario = Long.valueOf(idUsuarioStr); // unsafe conversion
			
			UsuarioDTO usuario = (UsuarioDTO) SessionManager.get(request, AttributeNames.USER);

			try {			
				//buscamos el usuario
				UsuarioDTO usuarioBusqueda = usuarioService.findById(idUsuario);
				
				//buscamos sus eventos disponibles
				EventoCriteria ec = new EventoCriteria();
				ec.setIdCreador(idUsuario);
				ec.setTipoEstadoEvento(EventoEstado.APTO);
				if(usuario!=null) {
					ec.setLatitudBuscador(usuario.getLatitud());
					ec.setLongitudBuscador(usuario.getLongitud());
				}else {
					//TODO ñapa para rellenar latitud y longitud aun que no exista sesion(NO SE MUESTRA)
					ec.setLatitudBuscador(22.343430D);
					ec.setLongitudBuscador(22.000000D);
				}
				Results<EventoDTO> resultsEventosDisponibles = eventoService.findByCriteria(ec, 1, 10);
				
				
				//buscamos sus valoraciones
				Results<UsuarioEventoPuntuaDTO> resultsPuntuaciones = puntuacionService.findByEventosUsuario(idUsuario, 1, 10);

				request.setAttribute(AttributeNames.USER, usuarioBusqueda);
				request.setAttribute(AttributeNames.EVENTS, resultsEventosDisponibles.getData());
				request.setAttribute(AttributeNames.PUNTUACIONES, resultsPuntuaciones.getData());
				request.setAttribute(AttributeNames.ID, idUsuario);
				
				targetView = ViewPaths.USER_PROFILE;
				
			} catch (DataException de) {
				logger.error("UserDetail: ",de.getMessage(),de);
				errors.addCommonError(ErrorsNames.ERROR_DATA_EXCEPTION);
			}	

			

		} else if (ActionNames.USER_LOGIN.equalsIgnoreCase(actionName)) {


			targetView = ViewPaths.USER_LOGIN;


			//LOGIN
			String emailStr = ValidationUtils.email(errors, ParameterNames.EMAIL);
			String passWordStr = request.getParameter(ParameterNames.PASSWORD);
			String keepAuthenticated = request.getParameter("keep-authenticated");

	
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
				errors.addCommonError(ErrorsNames.ERROR_INVALID_USER_PASSWORD_EXCEPTION);
			}catch (UserDeleteException ude) {
				logger.error("Login: "+emailStr,ude.getMessage(), ude);
				errors.addCommonError(ErrorsNames.ERROR_USER_DELETE_EXCEPTION);
			} catch (DataException de) {
				logger.error("Login: "+emailStr,de.getMessage(), de);
				errors.addCommonError(ErrorsNames.ERROR_DATA_EXCEPTION);
			} 

		} else if (ActionNames.USER_CREATE.equalsIgnoreCase(actionName)) {

			targetView = ViewPaths.USER_CREATE;
			
			//SINGUP
			UsuarioDTO usuario = new UsuarioDTO();
			
			usuario.setUserName(ValidationUtils.userName(errors,ParameterNames.USER_NAME));
			usuario.setEmail(ValidationUtils.email(errors, ParameterNames.EMAIL));
			usuario.setContrasena(ValidationUtils.contrasena(errors, ParameterNames.PASSWORD,false));
			usuario.setFechaNacimiento(ValidationUtils.fecha(errors, ParameterNames.FECHA_NACIMIENTO));
			usuario.setSexo(ValidationUtils.sexo(errors, ParameterNames.SEXO));
			usuario.setTelefono(ValidationUtils.telefono(errors, ParameterNames.TELEFONO));
			usuario.setBiografia(ValidationUtils.biografia(errors, ParameterNames.BIOGRAFIA));
			//TODO
			usuario.setLatitud(22.00000D);
			usuario.setLongitud(22.000000D);


			if (!errors.hasErrors()) {
				try {
				
					usuarioService.signUp(usuario);

					targetView = ViewPaths.USER_LOGIN;

				} catch (UserAlreadyExistsException uaee) {
					logger.error("UserCreate: "+mapParameter.get(ParameterNames.EMAIL)[0],uaee.getMessage(), uaee);
					errors.addCommonError("El usuario ya existe");
				} catch (MailException me) {
					logger.error("UserCreate: "+mapParameter.get(ParameterNames.EMAIL)[0],me.getMessage(), me);
					errors.addCommonError(ErrorsNames.ERROR_MAIL_EXCEPTION);
				} catch (DataException de) {
					logger.error("UserCreate: "+mapParameter.get(ParameterNames.EMAIL)[0],de.getMessage(), de);
					errors.addCommonError(ErrorsNames.ERROR_DATA_EXCEPTION);
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
