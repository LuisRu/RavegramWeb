package com.luis.ravegram.web.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.ravegram.model.Establecimiento;
import com.luis.ravegram.model.EstablecimientoCriteria;
import com.luis.ravegram.model.UsuarioCriteria;
import com.luis.ravegram.model.UsuarioDTO;
import com.luis.ravegram.service.EstablecimientoService;
import com.luis.ravegram.service.impl.EstablecimientoServiceImpl;
import com.luis.ravegram.web.controller.util.ActionNames;
import com.luis.ravegram.web.controller.util.AttributeNames;
import com.luis.ravegram.web.controller.util.ParameterNames;
import com.luis.ravegram.web.controller.util.SessionManager;
import com.luis.ravegram.web.controller.util.ViewPaths;

/**
 * Controlador (Servlet) para peticiones de establecimiento
 */
//@WebServlet("/EstablecimientoServlet")
public class EstablecimientoServlet extends HttpServlet {

	private static Logger logger = LogManager.getLogger(EstablecimientoServlet.class);

	private EstablecimientoService establecimientoService = null;

	public EstablecimientoServlet() {
		super();
		establecimientoService = new EstablecimientoServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//CommandManager.getInstance().doAction(request, response);
		String targetView = null;

		String actionName = request.getParameter(ParameterNames.ACTION);

		if(ActionNames.ESTABLECIMIENTO_SEARCH.equalsIgnoreCase(actionName)) {
			//BUSQUE CRITERIA

			UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute(AttributeNames.USER);

			String distanciaStr = request.getParameter(ParameterNames.DISTANCIA);
			String tipoEstablecimientoStr = request.getParameter(ParameterNames.TIPO_ESTABLECIMIENTO);

			EstablecimientoCriteria ec = new EstablecimientoCriteria();
			ec.setDistancia(null);
			ec.setTipoEstablecimiento(null);


			try {
				List<Establecimiento> establecimientos = establecimientoService.findByCriteria(ec, usuario.getLatitud(), usuario.getLongitud());
				request.setAttribute(AttributeNames.ESTABLECIMIENTOS, establecimientos);

				targetView=ViewPaths.ESTABLECIMIENTO_RESULT;

			}catch (Exception e) {
				logger.error("Search: ",e.getMessage(),e);
			}
		}else if(ActionNames.ESTABLECIMIENTO_DETAIL.equalsIgnoreCase(actionName)) {
			//VER ESTABLECIMIENTO DETALLE

			String establecimientoIdStr = request.getParameter(ParameterNames.ID);

			try {
				Establecimiento establecimiento = establecimientoService.findById(Long.valueOf(establecimientoIdStr));
				request.setAttribute(AttributeNames.ESTABLECIMIENTO, establecimiento);
				targetView = ViewPaths.ESTABLECIMIENTO_DETAIL;
			}catch(Exception e){
				logger.error("Detail: ",e.getMessage(),e);
			}

		}

		logger.info("Redirigiendo a "+targetView);

		request.getRequestDispatcher(targetView).forward(request, response);


	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
