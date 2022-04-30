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
import com.luis.ravegram.model.EstablecimientoDTO;
import com.luis.ravegram.model.EventoDTO;
import com.luis.ravegram.model.Results;
import com.luis.ravegram.model.UsuarioDTO;
import com.luis.ravegram.model.UsuarioEventoPuntuaDTO;
import com.luis.ravegram.model.criteria.EstablecimientoCriteria;
import com.luis.ravegram.model.criteria.EventoCriteria;
import com.luis.ravegram.model.criteria.UsuarioCriteria;
import com.luis.ravegram.model.state.EventoEstado;
import com.luis.ravegram.service.EstablecimientoService;
import com.luis.ravegram.service.EventoService;
import com.luis.ravegram.service.PuntuacionService;
import com.luis.ravegram.service.UsuarioService;
import com.luis.ravegram.service.UsuarioSigueService;
import com.luis.ravegram.service.impl.EstablecimientoServiceImpl;
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
@WebServlet("/private/establecimiento")
public class PrivateEstablecimientoServlet extends HttpServlet {

	private static Logger logger = LogManager.getLogger(PrivateEstablecimientoServlet.class);

	private EstablecimientoService establecimientoService = null;

	public PrivateEstablecimientoServlet() {
		super();
		establecimientoService = new EstablecimientoServiceImpl();

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



		if (ActionNames.ESTABLECIMIENTO_SEARCH.equalsIgnoreCase(actionName)) {
			// BUSQUEDA CRITERIA

			targetView=ViewPaths.HOME;

			EstablecimientoCriteria ec = new EstablecimientoCriteria();
			ec.setIdTipoEstablecimiento(ValidationUtils.intTransform(errors, request.getParameter(ParameterNames.TIPO_ESTABLECIMIENTO)));

			try {
				Integer currentPage = WebPaginUtils.getCurrentPage(request);
				

				Results<EstablecimientoDTO> results = establecimientoService.findByCriteria(ec,(currentPage-1)*5+1, 5);
				request.setAttribute(AttributeNames.ESTABLECIMIENTOS, results.getData());


				// Atributos para paginacion
				Integer totalPages = WebPaginUtils.getTotalPages(results.getTotal(), 5);
				request.setAttribute(AttributeNames.TOTAL_PAGES, totalPages);
				request.setAttribute(AttributeNames.CURRENT_PAGE, currentPage);
				//el 3 son las paginas de abajo
				request.setAttribute(AttributeNames.PAGING_FROM, WebPaginUtils.getPageFrom(currentPage,3, totalPages));
				request.setAttribute(AttributeNames.PAGING_TO, WebPaginUtils.getPageTo(currentPage, 3, totalPages));


				// Dirigir a...
				targetView =ViewPaths.ESTABLECIMIENTO_RESULT;
				forward = true;
				
				
				
	

			} catch (DataException de) {
				logger.error("EstablecimientoSearch: ", de.getMessage(), de);
				errors.addCommonError(ErrorsNames.ERROR_DATA_EXCEPTION);

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
