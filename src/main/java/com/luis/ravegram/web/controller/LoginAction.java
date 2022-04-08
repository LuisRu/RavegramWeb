package com.luis.ravegram.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.luis.ravegram.model.EventoDTO;
import com.luis.ravegram.model.UsuarioDTO;
import com.luis.ravegram.service.EventoService;
import com.luis.ravegram.service.UsuarioService;
import com.luis.ravegram.service.impl.EventoServiceImpl;
import com.luis.ravegram.service.impl.UsuarioServiceImpl;
import com.luis.ravegram.web.controller.util.ActionNames;
import com.luis.ravegram.web.controller.util.ViewPaths;

public class LoginAction extends Action {
	
	private UsuarioService usuarioService = null;
	private EventoService eventoService = null;
	
	public LoginAction() {
		super(ActionNames.LOGIN);
		usuarioService = new UsuarioServiceImpl();
		eventoService = new EventoServiceImpl();
	}

	public final String doIt(HttpServletRequest request, HttpServletResponse response) {

		String emailStr = request.getParameter("email");
		String passWordStr = request.getParameter("password");


		try {
			UsuarioDTO usuario = usuarioService.login(emailStr, passWordStr);
			List<EventoDTO> eventos = eventoService.findByCreador(usuario.getId());
			request.setAttribute("usuario" /* AttributeNames.USUARIO */, usuario);
			request.setAttribute("eventos" /* AttributeNames.USUARIO */, eventos);
			
			return ViewPaths.USER_PROFILE;
			
		} catch (Exception e) {
			// TODO
			e.printStackTrace();
			//llevaria a una vista rollo error
			return ViewPaths.USUARIO_LOGIN;
		}		
	}
	
}
