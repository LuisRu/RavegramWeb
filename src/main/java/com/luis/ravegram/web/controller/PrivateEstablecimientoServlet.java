package com.luis.ravegram.web.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.ravegram.dao.util.ConfigurationManager;
import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.model.EstablecimientoDTO;
import com.luis.ravegram.model.Results;
import com.luis.ravegram.model.criteria.EstablecimientoCriteria;
import com.luis.ravegram.service.EstablecimientoService;
import com.luis.ravegram.service.impl.EstablecimientoServiceImpl;
import com.luis.ravegram.web.controller.util.ActionNames;
import com.luis.ravegram.web.controller.util.AttributeNames;
import com.luis.ravegram.web.controller.util.ErrorsNames;
import com.luis.ravegram.web.controller.util.ParameterNames;
import com.luis.ravegram.web.controller.util.ViewPaths;
import com.luis.ravegram.web.util.ValidationUtils;
import com.luis.ravegram.web.util.WebPaginUtils;

/**
 * Controlador (Servlet) para peticiones de usuario autenticadas.
 */
@WebServlet("/private/establecimiento")
public class PrivateEstablecimientoServlet extends HttpServlet {
	
	
	
	private static final String CFGM_PFX = "controller.";
    private static final String WEB_SERVICE = CFGM_PFX + "establecimiento.";
    private static final String PAGE_SIZE = WEB_SERVICE + "pageSize";
    private static final String PAGE_COUNT = WEB_SERVICE + "pageCount";
    
    public static final String WEB_RAVEGRAM_PROPERTIES = "ravegramWeb-config";
    
    ConfigurationManager cfgM = ConfigurationManager.getInstance();

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
				

				Results<EstablecimientoDTO> results = establecimientoService.findByCriteria(ec,(currentPage-1)*Integer.valueOf(cfgM.getParameter(WEB_RAVEGRAM_PROPERTIES, PAGE_SIZE)) +1, Integer.valueOf(cfgM.getParameter(WEB_RAVEGRAM_PROPERTIES, PAGE_SIZE)));
				request.setAttribute(AttributeNames.ESTABLECIMIENTOS, results.getData());


				// Atributos para paginacion
				Integer totalPages = WebPaginUtils.getTotalPages(results.getTotal(), Integer.valueOf(cfgM.getParameter(WEB_RAVEGRAM_PROPERTIES, PAGE_SIZE)));
				request.setAttribute(AttributeNames.TOTAL_PAGES, totalPages);
				request.setAttribute(AttributeNames.CURRENT_PAGE, currentPage);
				//el 3 son las paginas de abajo
				request.setAttribute(AttributeNames.PAGING_FROM, WebPaginUtils.getPageFrom(currentPage,Integer.valueOf(cfgM.getParameter(WEB_RAVEGRAM_PROPERTIES, PAGE_COUNT)), totalPages));
				request.setAttribute(AttributeNames.PAGING_TO, WebPaginUtils.getPageTo(currentPage, Integer.valueOf(cfgM.getParameter(WEB_RAVEGRAM_PROPERTIES, PAGE_COUNT)), totalPages));


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
