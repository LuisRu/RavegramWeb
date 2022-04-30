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
import com.luis.ravegram.model.TipoEstablecimiento;
import com.luis.ravegram.service.TipoEstablecimientoService;
import com.luis.ravegram.service.impl.TipoEstablecimientoServiceImpl;
import com.luis.ravegram.web.controller.util.ActionNames;
import com.luis.ravegram.web.controller.util.ParameterNames;


@WebServlet("/tipo-establecimiento-service")
public class TipoEstablecimientoWebServiceServlet extends HttpServlet {

	private static Logger logger = LogManager.getLogger(TipoEstablecimientoWebServiceServlet.class);

	private TipoEstablecimientoService tipoEstablecimientoService = null;
	private Gson gson = null; 

	public TipoEstablecimientoWebServiceServlet() {
		super();
		tipoEstablecimientoService = new TipoEstablecimientoServiceImpl();
		gson = new Gson();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		
		String actionStr = request.getParameter(ParameterNames.ACTION);

		if (ActionNames.TIPO_ESTABLECIMIENTO.equals(actionStr)) {


			try {
				List<TipoEstablecimiento> tiposEstablecimiento = tipoEstablecimientoService.findAll();
				String json = gson.toJson(tiposEstablecimiento);

				response.setContentType("application/json");
				response.setCharacterEncoding("ISO-8859-1");
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