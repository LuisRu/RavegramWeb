package com.luis.ravegram.web.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.luis.ravegram.exception.EventoNotFoundException;
import com.luis.ravegram.exception.MailException;
import com.luis.ravegram.model.EventoDTO;
import com.luis.ravegram.model.Results;
import com.luis.ravegram.model.UsuarioDTO;
import com.luis.ravegram.model.UsuarioEventoPuntuaDTO;
import com.luis.ravegram.model.criteria.EventoCriteria;
import com.luis.ravegram.model.criteria.PuntuacionCriteria;
import com.luis.ravegram.model.criteria.UsuarioCriteria;
import com.luis.ravegram.model.state.EventoEstado;
import com.luis.ravegram.model.state.SolicitudEstado;
import com.luis.ravegram.service.EventoService;
import com.luis.ravegram.service.PuntuacionService;
import com.luis.ravegram.service.SolicitudService;
import com.luis.ravegram.service.UsuarioService;
import com.luis.ravegram.service.impl.EventoServiceImpl;
import com.luis.ravegram.service.impl.PuntuacionServiceImpl;
import com.luis.ravegram.service.impl.SolicitudServiceImpl;
import com.luis.ravegram.service.impl.UsuarioServiceImpl;
import com.luis.ravegram.web.controller.util.ActionNames;
import com.luis.ravegram.web.controller.util.AttributeNames;
import com.luis.ravegram.web.controller.util.ControllerPaths;
import com.luis.ravegram.web.controller.util.ErrorsNames;
import com.luis.ravegram.web.controller.util.ParameterNames;
import com.luis.ravegram.web.controller.util.SessionManager;
import com.luis.ravegram.web.controller.util.ViewPaths;
import com.luis.ravegram.web.util.ParametersUtil;
import com.luis.ravegram.web.util.ValidationUtils;

/**
 * Controlador (Servlet) para peteciones de eventos que requieren autentication.
 */
@WebServlet("/private/evento")
public class PrivateEventoServlet extends HttpServlet {
	
	private static final String CFGM_PFX = "controller.";
    private static final String WEB_SERVICE = CFGM_PFX + "eventoUsuario.";
    private static final String PAGE_SIZE = WEB_SERVICE + "pageSize";
    private static final String PAGE_COUNT = WEB_SERVICE + "pageCount";
    
    public static final String WEB_RAVEGRAM_PROPERTIES = "ravegramWeb-config";
    
    ConfigurationManager cfgM = ConfigurationManager.getInstance();
    

	private static Logger logger = LogManager.getLogger(PrivateEventoServlet.class);

	private EventoService eventoService = null;
	private UsuarioService usuarioService = null;
	private PuntuacionService puntuacionesService = null;
	private SolicitudService solicitudService = null;

	public PrivateEventoServlet() {
		super();
		eventoService = new EventoServiceImpl();
		usuarioService = new UsuarioServiceImpl();
		puntuacionesService = new PuntuacionServiceImpl();
		solicitudService = new SolicitudServiceImpl();
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

		
		
		if (ActionNames.EVENT_DETAIL.equalsIgnoreCase(actionName)) {
			// VISTA EN DETALLE

			targetView = ViewPaths.HOME;

			UsuarioDTO usuario = (UsuarioDTO) SessionManager.get(request, AttributeNames.USER);

			Boolean create = ValidationUtils.booleanTransform(errors, request.getParameter(ParameterNames.CREATE));

			try {
				if (create != null) {

					List<UsuarioDTO> usuarios = usuarioService.findSeguidores(usuario.getId());
					request.setAttribute(AttributeNames.USERS, usuarios);
					targetView = ViewPaths.EVENT_CREATE;

				} else {
					Boolean update = ValidationUtils.booleanTransform(errors,request.getParameter(ParameterNames.UPDATE));
					Long idEvento = ValidationUtils.longTransform(errors, request.getParameter(ParameterNames.ID));

					EventoDTO evento = eventoService.findById(idEvento, usuario.getLatitud(), usuario.getLongitud());

					// buscar los asistentes
					UsuarioCriteria uc = new UsuarioCriteria();
					uc.setIdEvento(idEvento);
					uc.setTipoEstadoSolicitud(SolicitudEstado.ACEPTADO);
					Results<UsuarioDTO> resultsAsistentes = usuarioService.findByCriteria(uc, 1, 100);

					// para poder reutilizar la misma accion para update y detail
					if (update != null) {
						// buscar los usuarios que me siguen y no estan aceptados en el evento
						List<UsuarioDTO> usuarios = usuarioService.findSeguidoresNoAceptadoEvento(usuario.getId(),idEvento);
						
						request.setAttribute(AttributeNames.USERS, usuarios);
						
						targetView = ViewPaths.EVENT_UPDATE;
					} else {
						// buscar las valoraciones por evento
						PuntuacionCriteria pc = new PuntuacionCriteria();
						pc.setIdEvento(idEvento);
						Results<UsuarioEventoPuntuaDTO> resultsPuntuaciones = puntuacionesService.findByCriteria(pc, 1,10);
						
						request.setAttribute(AttributeNames.PUNTUACIONES, resultsPuntuaciones.getData());
						
						targetView = ViewPaths.EVENT_DETAIL;
					}

					request.setAttribute(AttributeNames.EVENT, evento);
					request.setAttribute(AttributeNames.ASISTENTES, resultsAsistentes.getData());
				}

			} catch (DataException de) {
				logger.error("EventDetail: ", de.getMessage(), de);
				errors.addCommonError(ErrorsNames.ERROR_DATA_EXCEPTION);
			}

			
			
		} else if (ActionNames.EVENT_SEARCH.equalsIgnoreCase(actionName)) {
			// BUSQUEDA CRITERIA
			
			targetView= ViewPaths.HOME;
			
			UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute(AttributeNames.USER);

			EventoCriteria ec = new EventoCriteria();
			// datos que introduce el usuario en el formulario
			ec.setPublicPrivado(ValidationUtils.booleanTransform(errors, request.getParameter(ParameterNames.ES_PRIVADO)));
			ec.setDistancia(ValidationUtils.intTransform(errors, request.getParameter(ParameterNames.DISTANCIA)));
			ec.setTipoTematica(ValidationUtils.longTransform(errors, request.getParameter(ParameterNames.TIPO_TEMATICA)));
			ec.setTipoMusica(ValidationUtils.longTransform(errors, request.getParameter(ParameterNames.TIPO_MUSICA)));
			ec.setTipoEstablecimiento(ValidationUtils.longTransform(errors, request.getParameter(ParameterNames.TIPO_ESTABLECIMIENTO)));
			ec.setOrderBy(ValidationUtils.orderByTransform(errors, request.getParameter(ParameterNames.ORDER_BY)));

			// para concretar la query que quiero hacer
			ec.setDescartarInteractuados(true);
			ec.setIdBuscador(usuario.getId());
			ec.setLatitudBuscador(usuario.getLongitud());
			ec.setLongitudBuscador(usuario.getLongitud());

			try {
				Results<EventoDTO> resultsEvento = eventoService.findByCriteria(ec, Integer.valueOf(cfgM.getParameter(WEB_RAVEGRAM_PROPERTIES, PAGE_SIZE)), Integer.valueOf(cfgM.getParameter(WEB_RAVEGRAM_PROPERTIES, PAGE_SIZE)));
				
				
				request.setAttribute(AttributeNames.EVENTS, resultsEvento.getData());

				targetView = ViewPaths.EVENT_RESULT;

			} catch (DataException de) {
				logger.error("EventSearch: ", de.getMessage(), de);
				errors.addCommonError(ErrorsNames.ERROR_DATA_EXCEPTION);
			}

			
			
		}else if (ActionNames.EVENT_SHARE.equalsIgnoreCase(actionName)) {
				// COMPARTIR
				
				UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute(AttributeNames.USER);
				
				String idEventoStr = request.getParameter(ParameterNames.ID);
				Long idEvento = ValidationUtils.longTransform(errors,idEventoStr);
				List<Long> idsCompartir = ValidationUtils.longValidator(errors, request, ParameterNames.IDS);
				
				try {
					Map<String, String> userDetailParams = new HashMap<String, String>();
					userDetailParams.put(ParameterNames.ACTION, ActionNames.EVENT_DETAIL);
					userDetailParams.put(ParameterNames.ID, idEventoStr);
					String URL = ParametersUtil.getURL(ControllerPaths.PRIVATE_EVENTO, userDetailParams);
					URL = request.getScheme()+"://"+request.getServerName()+":"+request.getLocalPort()+request.getContextPath()+URL;
					
					eventoService.compartir(usuario.getId(),idEvento, usuario.getLatitud(), usuario.getLongitud(), idsCompartir,URL);

					targetView = request.getParameter(ParameterNames.URL);
					forward=false;
				} catch (MailException me) {
					logger.error("eventShare: ", me.getMessage(), me);
					errors.addCommonError(ErrorsNames.ERROR_MAIL_EXCEPTION);
				} catch (DataException de) {
					logger.error("eventShare: ", de.getMessage(), de);
					errors.addCommonError(ErrorsNames.ERROR_DATA_EXCEPTION);
				}

				
		} else if (ActionNames.EVENT_CREATE.equalsIgnoreCase(actionName)) {
			// CREATE
			
			targetView = ViewPaths.EVENT_CREATE;

			UsuarioDTO usuario = (UsuarioDTO) SessionManager.get(request, AttributeNames.USER);
			
			EventoDTO evento = new EventoDTO();
			evento.setIdUsuario(usuario.getId());
			evento.setIdTipoEstadoEvento(EventoEstado.APTO);
			evento.setFechaHora(ValidationUtils.fechaHora(errors, ParameterNames.FECHA_HORA));
			evento.setIdTipoTematica(ValidationUtils.longValidator(errors, ParameterNames.TIPO_TEMATICA, 1, 10, false));
			evento.setIdTipoEstablecimiento(ValidationUtils.longValidator(errors, ParameterNames.TIPO_ESTABLECIMIENTO, 1, 10, true));
			evento.setIdTipoMusica(ValidationUtils.longValidator(errors, ParameterNames.TIPO_MUSICA, 1, 10, false));
			evento.setPublicoPrivado(ValidationUtils.boleanValidator(errors, ParameterNames.ES_PRIVADO, true));
			evento.setCalle(ValidationUtils.vacio(errors, ParameterNames.CALLE));
			evento.setNumAsistentes(ValidationUtils.integerValidator(errors, ParameterNames.NUM_ASISENTES, 1, 50, false));
			evento.setNombre(ValidationUtils.vacio(errors, ParameterNames.NOMBRE_EVENTO));
			evento.setDescripcion(ValidationUtils.vacio(errors, ParameterNames.DESCRIPCION));
			evento.setLatitud(ValidationUtils.doubleValidator(errors, ParameterNames.LATITUD));
			evento.setLongitud(ValidationUtils.doubleValidator(errors, ParameterNames.LONGITUD));
			// TODO descativarlos de obligatorio
			evento.setIdLocalidad(1L);
			evento.setZip("111111");
			
			
			// asistentes
			List<Long> idsAsistentes = (ValidationUtils.longValidator(errors, request, ParameterNames.IDS_ASISTENTES));

			if (!errors.hasErrors()) {
				try {

					eventoService.create(evento, idsAsistentes);

					// para que vaya a su perfil
					Map<String, String> userDetailParams = new HashMap<String, String>();
					userDetailParams.put(ParameterNames.ACTION, ActionNames.USER_MY_PROFILE);

					targetView = ParametersUtil.getURL(ControllerPaths.PRIVATE_USER, userDetailParams);
					forward = false;

				} catch (DataException de) {
					logger.error("EventoCreate: ", de.getMessage(), de);
					errors.addCommonError(ErrorsNames.ERROR_DATA_EXCEPTION);
				}
			}

			
			
		} else if (ActionNames.EVENT_UPDATE.equalsIgnoreCase(actionName)) {
			// UPDATE
			
			targetView = ViewPaths.EVENT_UPDATE;
			
			String idEventoStr = request.getParameter(ParameterNames.ID);

			UsuarioDTO usuario = (UsuarioDTO) SessionManager.get(request, AttributeNames.USER);
			EventoDTO evento = new EventoDTO();
			evento.setId(ValidationUtils.longTransform(errors, idEventoStr));
			evento.setIdUsuario(usuario.getId());			
			evento.setIdTipoEstadoEvento(ValidationUtils.longTransform(errors, request.getParameter(ParameterNames.TIPO_ESTADO_EVENTO)));
			evento.setFechaHora(ValidationUtils.fechaHora(errors, ParameterNames.FECHA_HORA));
			evento.setIdTipoTematica(ValidationUtils.longValidator(errors, ParameterNames.TIPO_TEMATICA, 1, 10, false));
			evento.setIdTipoEstablecimiento(ValidationUtils.longValidator(errors, ParameterNames.TIPO_ESTABLECIMIENTO, 1, 10, true));
			evento.setIdTipoMusica(ValidationUtils.longValidator(errors, ParameterNames.TIPO_MUSICA, 1, 10, false));
			evento.setPublicoPrivado(ValidationUtils.boleanValidator(errors, ParameterNames.ES_PRIVADO, true));
			evento.setCalle(ValidationUtils.vacio(errors, ParameterNames.CALLE));
			evento.setNumAsistentes(ValidationUtils.integerValidator(errors, ParameterNames.NUM_ASISENTES, 1, 50, false));
			evento.setNombre(ValidationUtils.eventName(errors, ParameterNames.NOMBRE_EVENTO));
			evento.setDescripcion(ValidationUtils.vacio(errors, ParameterNames.DESCRIPCION));
			evento.setLatitud(ValidationUtils.doubleValidator(errors, ParameterNames.LATITUD));
			evento.setLongitud(ValidationUtils.doubleValidator(errors, ParameterNames.LONGITUD));
			// TODO QUITAR
			evento.setEdadeDesde(new Date());
			evento.setEdadHasta(new Date());
			evento.setZip("12323");
			evento.setIdLocalidad(1L);

			// asistentes
			List<Long> idsAsistentes = (ValidationUtils.longValidator(errors, request, ParameterNames.IDS_ASISTENTES));

			if (!errors.hasErrors()) {
				try {
					eventoService.update(evento, idsAsistentes);

					Map<String, String> userDetailParams = new HashMap<String, String>();
					userDetailParams.put(ParameterNames.ACTION, ActionNames.EVENT_DETAIL);
					userDetailParams.put(ParameterNames.ID, idEventoStr);

					targetView = ParametersUtil.getURL(ControllerPaths.PRIVATE_EVENTO, userDetailParams);
					forward = false;

				} catch (EventoNotFoundException enfe) {
					logger.error("EventUpdate: ", enfe.getMessage(), enfe);
					errors.addCommonError(ErrorsNames.ERROR_EVENTO_NOT_FOUND_EXCEPTION);
				} catch (DataException de) {
					logger.error("EventUpdate: ", de.getMessage(), de);
					errors.addCommonError(ErrorsNames.ERROR_DATA_EXCEPTION);
				}

			}
			
			

		} else if (ActionNames.EVENT_DELETE.equalsIgnoreCase(actionName)) {
			// DELETE
			
			targetView = ViewPaths.HOME;

			String idEventoStr = request.getParameter(ParameterNames.ID);
			Long idEvento = ValidationUtils.longTransform(errors, idEventoStr);

			if (!errors.hasErrors()) {
				try {

					eventoService.delete(idEvento);
					
					Map<String, String> userDetailParams = new HashMap<String, String>();
					userDetailParams.put(ParameterNames.ACTION, ActionNames.USER_MY_PROFILE);

					targetView = ParametersUtil.getURL(ControllerPaths.PRIVATE_USER, userDetailParams);
					forward = false;

				} catch (DataException de) {
					logger.error("EventDelete: ", de.getMessage(), de);
					errors.addCommonError(ErrorsNames.ERROR_DATA_EXCEPTION);
				}
			}
			
		}else if (ActionNames.EVENT_FINALIZAR.equalsIgnoreCase(actionName)) {
					// DELETE
					
					targetView = ViewPaths.HOME;

					String idEventoStr = request.getParameter(ParameterNames.ID);
					Long idEvento = ValidationUtils.longTransform(errors, idEventoStr);

					if (!errors.hasErrors()) {
						try {

							eventoService.updateEstado(idEvento, EventoEstado.FINALIZADO);
							
							Map<String, String> userDetailParams = new HashMap<String, String>();
							userDetailParams.put(ParameterNames.ACTION, ActionNames.EVENT_DETAIL);
							userDetailParams.put(ParameterNames.ID, idEvento.toString());

							targetView = ParametersUtil.getURL(ControllerPaths.PRIVATE_EVENTO, userDetailParams);
							forward = false;
							
						} catch (EventoNotFoundException enfe) {
							logger.error("EventFinalizar: ", enfe.getMessage(), enfe);
							errors.addCommonError(ErrorsNames.ERROR_EVENTO_NOT_FOUND_EXCEPTION);
						} catch (DataException de) {
							logger.error("EventFinalizar: ", de.getMessage(), de);
							errors.addCommonError(ErrorsNames.ERROR_DATA_EXCEPTION);
						}
					}
			
			
		}else {
			targetView = ViewPaths.HOME;
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
