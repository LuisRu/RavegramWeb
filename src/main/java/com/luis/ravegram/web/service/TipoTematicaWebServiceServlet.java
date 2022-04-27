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
import com.luis.ravegram.model.TipoMusica;
import com.luis.ravegram.model.TipoTematica;
import com.luis.ravegram.model.UsuarioDTO;
import com.luis.ravegram.service.TipoMusicaService;
import com.luis.ravegram.service.TipoTematicaService;
import com.luis.ravegram.service.impl.TipoMusicaServiceImpl;
import com.luis.ravegram.service.impl.TipoTematicaServiceImpl;
import com.luis.ravegram.web.controller.util.ActionNames;
import com.luis.ravegram.web.controller.util.AttributeNames;
import com.luis.ravegram.web.controller.util.ParameterNames;
import com.luis.ravegram.web.controller.util.SessionManager;


@WebServlet("/tipo-tematica-service")
public class TipoTematicaWebServiceServlet extends HttpServlet {

	private static Logger logger = LogManager.getLogger(TipoTematicaWebServiceServlet.class);

	private TipoTematicaService tipoTematicaService = null;
	private Gson gson = null; 

	public TipoTematicaWebServiceServlet() {
		super();
		tipoTematicaService = new TipoTematicaServiceImpl();
		gson = new Gson();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		
		String actionStr = request.getParameter(ParameterNames.ACTION);

		if (ActionNames.TIPO_TEMATICA.equals(actionStr)) {


			try {
				List<TipoTematica> tiposTematica = tipoTematicaService.findAll();
				String json = gson.toJson(tiposTematica);

				response.setContentType("application/json");
				
				ServletOutputStream sos = response.getOutputStream();
				// TODO mimetype
				sos.write(json.getBytes());

				sos.flush();
			} catch (Exception e) {
				logger.error("tipoMusica: "+e);
			}								


		}

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}