package com.luis.ravegram.web.controller.util;

public class ErrorsNames {

	public static final String ERROR_DATA_EXCEPTION = "Ha ocurrido un problema al consultas sus datos. Por favor inténtelo mas tarde.";
	
	//usuarios
	public static final String ERROR_USER_DELETE_EXCEPTION = "Este usuario fue eliminado,porfavor porngase en contacto con nosotros para recuperar su cuenta";
	public static final String ERROR_ALREADY_EXISTS_EXCEPTION = "El usuario que esta tratando de crear ya existe, intentelo con otro correo";
	public static final String ERROR_INVALID_USER_PASSWORD_EXCEPTION = "El email o la contraseña son incorrectos, porfavor intentelo de nuevo";
	public static final String ERROR_USER_NOT_FOUND_EXCEPTION = "El usuario que esta tratando de actualizar no existe";
	
	//eventos
	public static final String ERROR_EVENTO_NOT_FOUND_EXCEPTION = "El evento que esta tratando de actualizar no existe";
	
	//mail
	public static final String ERROR_MAIL_EXCEPTION = "Sucedio un problema al intentar enviar el correo de confirmacion";
	
	//solicitud
	public static final String ERROR_REQUEST_NOT_FOUND_EXCEPTION = "La solicitud que esta tratando de actualizar no existe";
	public static final String ERROR_REQUEST_INVALID_STATE_EXCEPTION = "No existe el estado indicado para las solicitudes";
}
