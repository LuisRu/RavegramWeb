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
import com.luis.ravegram.dao.util.ConfigurationManager;
import com.luis.ravegram.dao.util.ConfigurationManager;
import com.luis.ravegram.model.Results;
import com.luis.ravegram.model.UsuarioDTO;
import com.luis.ravegram.model.criteria.UsuarioCriteria;
import com.luis.ravegram.service.UsuarioService;
import com.luis.ravegram.service.impl.UsuarioServiceImpl;
import com.luis.ravegram.web.controller.util.ActionNames;
import com.luis.ravegram.web.controller.util.AttributeNames;
import com.luis.ravegram.web.controller.util.ParameterNames;
import com.luis.ravegram.web.controller.util.SessionManager;


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
	private Gson gson = null; 

	public UsuarioWebServiceServlet() {
		super();
		usuarioService = new UsuarioServiceImpl();
		gson = new Gson();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		
		String actionStr = request.getParameter(ParameterNames.ACTION);

		// TODO rest style
		 if(ActionNames.USER_SEARCH_FOLLOWER.equals(actionStr)) {

			String idUsuarioStr = request.getParameter(ParameterNames.ID);
			
			
			
			Long idUsuario = Long.valueOf(idUsuarioStr);

			try{

				List<UsuarioDTO> usuarios =  usuarioService.findSeguidores(idUsuario);

				String json = gson.toJson(usuarios);

				response.setContentType("application/json");
				
				ServletOutputStream sos = response.getOutputStream();
				// TODO mimetype
				sos.write(json.getBytes());

				sos.flush();

			}catch (Exception e) {
				// TODO: handle exception
			}



		}else if(ActionNames.USER_SEARCH_FOLLOWING.equals(actionStr)) {


			String idUsuarioStr = request.getParameter(ParameterNames.ID);
			Long idUsuario = Long.valueOf(idUsuarioStr);


			try{

				List<UsuarioDTO> usuarios =  usuarioService.findSeguidos(idUsuario);

				String json = gson.toJson(usuarios);

				response.setContentType("application/json");

				ServletOutputStream sos = response.getOutputStream();
				// TODO mimetype
				sos.write(json.getBytes());

				sos.flush();

			}catch (Exception e) {
				// TODO: handle exception
			}

		}else if(ActionNames.USER_SEARCH.equals(actionStr)) {
			
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