package com.luis.ravegram.web.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.luis.ravegram.dao.util.ConfigurationManager;
import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.model.Results;
import com.luis.ravegram.model.UsuarioDTO;
import com.luis.ravegram.model.criteria.UsuarioCriteria;
import com.luis.ravegram.model.state.SolicitudEstado;
import com.luis.ravegram.service.UsuarioService;
import com.luis.ravegram.service.UsuarioSigueService;
import com.luis.ravegram.service.impl.UsuarioServiceImpl;
import com.luis.ravegram.service.impl.UsuarioSigueServiceImpl;
import com.luis.ravegram.web.controller.Errors;
import com.luis.ravegram.web.controller.util.ActionNames;
import com.luis.ravegram.web.controller.util.AttributeNames;
import com.luis.ravegram.web.controller.util.ControllerPaths;
import com.luis.ravegram.web.controller.util.ErrorsNames;
import com.luis.ravegram.web.controller.util.ParameterNames;
import com.luis.ravegram.web.controller.util.SessionManager;
import com.luis.ravegram.web.controller.util.ViewPaths;
import com.luis.ravegram.web.util.ParametersUtil;
import com.luis.ravegram.web.util.ValidationUtils;


@WebServlet("/usuario-service")
public class UsuarioWebServiceServlet extends HttpServlet {

	private static Logger logger = LogManager.getLogger(UsuarioWebServiceServlet.class);


	private static final String CFGM_PFX = "controller.";
	private static final String START_INDEX = CFGM_PFX + "startIndex";
	private static final String PAGE_COUNT = CFGM_PFX + "pageCount";
	private static final String PAGE_SIZE_SEARCH_USUARIO = CFGM_PFX + "pageSizeSearchNombre";


	public static final String WEB_RAVEGRAM_PROPERTIES = "ravegram-config";
	public static final String WEB_RAVEGRAM_WEB_PROPERTIES = "ravegramWeb-config";

	private ConfigurationManager cfgM = ConfigurationManager.getInstance();


	private UsuarioService usuarioService = null;
	private UsuarioSigueService usuarioSigueService = null;
	private Gson gson = null; 

	public UsuarioWebServiceServlet() {
		super();
		usuarioService = new UsuarioServiceImpl();
		usuarioSigueService = new UsuarioSigueServiceImpl();
		gson = new Gson();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



		Errors errors = new Errors();
		request.setAttribute(AttributeNames.ERRORS, errors);

		Map<String, String[]> mapParameter = request.getParameterMap();
		ValidationUtils.setMapParameter(mapParameter);

		String actionName = request.getParameter(ParameterNames.ACTION);


		if(ActionNames.USER_SEARCH_FOLLOWER.equals(actionName)) {

			Long idUsuario = ValidationUtils.longTransform(errors, request.getParameter(ParameterNames.ID));

			try{

				List<UsuarioDTO> usuarios =  usuarioService.findSeguidores(idUsuario);

				String json = gson.toJson(usuarios);

				response.setContentType("application/json");
				response.setCharacterEncoding("ISO-8859-1");
				ServletOutputStream sos = response.getOutputStream();


				sos.write(json.getBytes());

				sos.flush();

			}catch (Exception e) {
				// TODO: handle exception
			}



		}else if(ActionNames.USER_SEARCH_FOLLOWING.equals(actionName)) {


			String idUsuarioStr = request.getParameter(ParameterNames.ID);
			Long idUsuario = Long.valueOf(idUsuarioStr);


			try{

				List<UsuarioDTO> usuarios =  usuarioService.findSeguidos(idUsuario);

				String json = gson.toJson(usuarios);

				response.setContentType("application/json");
				response.setCharacterEncoding("ISO-8859-1");
				ServletOutputStream sos = response.getOutputStream();
				// TODO mimetype
				sos.write(json.getBytes());

				sos.flush();

			}catch (Exception e) {
				// TODO: handle exception
			}

		}else if(ActionNames.USER_FOLLOWER_NOT_ACCEPT.equals(actionName)) {


			String idUsuarioStr = request.getParameter(ParameterNames.ID_DOS);
			Long idUsuario = Long.valueOf(idUsuarioStr);
			String idEventoStr = request.getParameter(ParameterNames.ID);
			Long idEvento = Long.valueOf(idEventoStr);


			try{

				List<UsuarioDTO> usuarios =  usuarioService.findSeguidoresNoAceptadoEvento(idUsuario,idEvento);

				String json = gson.toJson(usuarios);

				response.setContentType("application/json");
				response.setCharacterEncoding("ISO-8859-1");
				ServletOutputStream sos = response.getOutputStream();
				// TODO mimetype
				sos.write(json.getBytes());

				sos.flush();

			}catch (Exception e) {
				// TODO: handle exception
			}

		}else if(ActionNames.USER_UPDATE_UBICACION.equals(actionName)) {


			String latitudStr = request.getParameter(ParameterNames.LATITUD);
			Double latitud = Double.valueOf(latitudStr);
			String longitudStr = request.getParameter(ParameterNames.LONGITUD);
			Double longitud  = Double.valueOf(longitudStr);
			UsuarioDTO usuario = (UsuarioDTO) SessionManager.get(request, AttributeNames.USER);

			try{

				usuarioService.updateUbicacion(latitud, longitud, usuario.getId());

				String json = gson.toJson("OK");

				response.setContentType("application/json");
				response.setCharacterEncoding("ISO-8859-1");
				ServletOutputStream sos = response.getOutputStream();
				// TODO mimetype
				sos.write(json.getBytes());

				sos.flush();

			}catch (Exception e) {
				// TODO: handle exception
			}

		} else if (ActionNames.USER_FOLLOW.equalsIgnoreCase(actionName)) {
			// FOLLOW

			String idSeguidoStr = request.getParameter(ParameterNames.ID);
			Long idSeguido = ValidationUtils.longTransform(errors, request.getParameter(ParameterNames.ID));
					
			UsuarioDTO usuario = (UsuarioDTO) SessionManager.get(request, AttributeNames.USER);

			try {
				//lo seguimos
				usuarioSigueService.follow(usuario.getId(), idSeguido);

				//actualizo a la lista de seguidos en sesion
				Set<Long> idsSeguidos = usuarioService.findSeguidosIds(usuario.getId());
				SessionManager.set(request, AttributeNames.FOLLOWING, idsSeguidos);
				
				String json = gson.toJson("OK");

				response.setContentType("application/json");
				response.setCharacterEncoding("ISO-8859-1");
				ServletOutputStream sos = response.getOutputStream();
				// TODO mimetype
				sos.write(json.getBytes());

				sos.flush();




			} catch (DataException de) {
				logger.error("Follow: " + idSeguidoStr, de.getMessage(), de);
				errors.addCommonError(ErrorsNames.ERROR_DATA_EXCEPTION);
			}

		} else if (ActionNames.USER_UNFOLLOW.equalsIgnoreCase(actionName)) {
			// UNFOLLOW


			Long idSeguido = ValidationUtils.longTransform(errors, request.getParameter(ParameterNames.ID));
			UsuarioDTO usuario = (UsuarioDTO) SessionManager.get(request, AttributeNames.USER);

			try {
				//lo dejo de seguir
				usuarioSigueService.unFollow(usuario.getId(), idSeguido);

				//actualizo a la lista de seguidos en sesion 
				Set<Long> idsSeguidos = usuarioService.findSeguidosIds(usuario.getId());
				SessionManager.set(request, AttributeNames.FOLLOWING, idsSeguidos);
				
				String json = gson.toJson("OK");

				response.setContentType("application/json");
				response.setCharacterEncoding("ISO-8859-1");
				ServletOutputStream sos = response.getOutputStream();
				// TODO mimetype
				sos.write(json.getBytes());

				sos.flush();


			} catch (DataException de) {
				logger.error("UnFollow: " + idSeguido, de.getMessage(), de);
				errors.addCommonError(ErrorsNames.ERROR_DATA_EXCEPTION);
			}

			
			
		}else if(ActionNames.USER_SEARCH_ASSISTANTS.equals(actionName)) {


			String idEventoStr = request.getParameter(ParameterNames.ID);
			Long idEvento = Long.valueOf(idEventoStr);


			try{

				UsuarioCriteria uc = new UsuarioCriteria();
				uc.setIdEvento(idEvento);
				uc.setTipoEstadoSolicitud(SolicitudEstado.ACEPTADO);
				Results<UsuarioDTO> resultsAsistentes = usuarioService.findByCriteria(uc, 1, 100);

				String json = gson.toJson(resultsAsistentes.getData());

				response.setContentType("application/json");
				response.setCharacterEncoding("ISO-8859-1");
				ServletOutputStream sos = response.getOutputStream();
				// TODO mimetype
				sos.write(json.getBytes());

				sos.flush();

			}catch (Exception e) {
				// TODO: handle exception
			}



		}else if(ActionNames.USER_SEARCH.equals(actionName)) {

			String buscadorStr = request.getParameter(ParameterNames.BUSCADOR);

			UsuarioDTO usuario = (UsuarioDTO) SessionManager.get(request, AttributeNames.USER);

			try {
				Results<UsuarioDTO> results = null;
				UsuarioCriteria uc = new UsuarioCriteria();

				//si esta logueado no se encuetra a si mismo
				if(usuario!=null) {
					uc.setIdBuscador(usuario.getId());
				}

				uc.setBusqueda(buscadorStr);

				//TODO PAGINACION
				results = usuarioService.findByCriteria(uc,Integer.valueOf(cfgM.getParameter(WEB_RAVEGRAM_WEB_PROPERTIES, START_INDEX)) , Integer.valueOf(cfgM.getParameter(WEB_RAVEGRAM_WEB_PROPERTIES,PAGE_SIZE_SEARCH_USUARIO)));


				String json = gson.toJson(results.getData());

				response.setContentType("application/json");
				response.setCharacterEncoding("ISO-8859-1");
				ServletOutputStream sos = response.getOutputStream();
				// TODO mimetype
				sos.write(json.getBytes());

				sos.flush();



			}catch (Exception e) {
				// TODO: handle exception
			}


		}

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}