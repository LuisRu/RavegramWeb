package com.luis.ravegram.web.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.DoubleValidator;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.IntegerValidator;
import org.apache.commons.validator.routines.LongValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.ravegram.web.controller.Errors;
import com.luis.ravegram.web.controller.util.ErrorsParameter;


public class ValidationUtils {


	private static Logger logger = LogManager.getLogger(ValidationUtils.class);

	private static Map<String, String[]> mapParameter = null;
	private static EmailValidator emailValidator  = EmailValidator.getInstance();
	private static IntegerValidator intValidator = IntegerValidator.getInstance();
	private static LongValidator longValidator = LongValidator.getInstance();
	private static DoubleValidator doubleValidator = DoubleValidator.getInstance();


	//TODO poner numeros letras etc
	private static final Pattern USER_NAME_REGEX = Pattern.compile("[A-Za-z]*");
	private static final Pattern EVENT_NAME_REGEX = Pattern.compile("/^[a-zA-Z\\s]*$/");
	private static final Pattern CONTRASENA_REGEX = Pattern.compile("[[a-z]+[A-Z]+[0-9]]{6,20}");
	private static final Pattern TELEFONO_REGEX = Pattern.compile("^[6,8,9][0-9]{8}$|^\\\\+[0-9]{2}\\\\s[1-9][0-9]{8}$");

	private static final String DATE_FORMAT = "dd-MM-yyyy";
	private static final String DATE_FORMAT_CON_HORA = "dd-MM-yyyy H:m:s";



	public ValidationUtils() {

	}

	public static String getParameterMap(String parameter) {
		return mapParameter.get(parameter)[0];
	}

	public static void setMapParameter(Map<String, String[]> mapParameters) {
		mapParameter = mapParameters;
	}


	public static String userName(Errors errors,String parameterName) { 

		String userName = getParameterMap(parameterName);

		if(!StringUtils.isBlank(userName)) {
			userName = userName.trim();
			if(!USER_NAME_REGEX.matcher(userName).matches()) {
				errors.addParameterError(parameterName, ErrorsParameter.USER_NAME_ERROR_FORMATO_INVALIDO);
			}
		}else {
			errors.addParameterError(parameterName, ErrorsParameter.ERROR_VACIO);

		}

		return userName;

	}

	public static String eventName(Errors errors,String parameterName) { 

		String eventNameuserName = getParameterMap(parameterName);

		if(!StringUtils.isBlank(eventNameuserName)) {
			eventNameuserName = eventNameuserName.trim();
		}else {
			errors.addParameterError(parameterName, ErrorsParameter.ERROR_VACIO);

		}

		return eventNameuserName;

	}



	public static String email(Errors errors,String parameterName) { 

		String email = getParameterMap(parameterName);

		if(!StringUtils.isBlank(email)) {
			email = email.trim();
			if(!emailValidator.isValid(email)) {
				errors.addParameterError(parameterName, ErrorsParameter.EMAIL_ERROR_FORMATO_INVALIDO);
			}
		}else {
			errors.addParameterError(parameterName, ErrorsParameter.ERROR_VACIO);
		}

		return email;
	}

	/**
	 * El parametro update debe ir a true si los datos de la contraseña vienen 
	 * del update
	 * @param errors
	 * @param parameterName
	 * @param update
	 * @return
	 */
	public static String contrasena(Errors errors,String parameterName,Boolean update) { 

		String contrasena = getParameterMap(parameterName);

		if(!StringUtils.isBlank(contrasena)) {
			contrasena = contrasena.trim();
			if(!CONTRASENA_REGEX.matcher(contrasena).matches()) {
				errors.addParameterError(parameterName, ErrorsParameter.CONTRASENA_ERROR_FORMATO_INVALIDO);
			}
		}else {
			//lo tratara en service
			if(update==true) {
				return null;
			}else {
				errors.addParameterError(parameterName, ErrorsParameter.ERROR_VACIO);
			}
		}

		return contrasena;

	}

	public static Date fecha(Errors errors,String parameterName) { 


		String fecha = getParameterMap(parameterName);
		Date date = null;


		if(!StringUtils.isBlank(fecha)) {
			fecha = fecha.trim();
			try {

				date = new SimpleDateFormat(DATE_FORMAT).parse(fecha);

			}catch(ParseException e) {
				errors.addParameterError(parameterName,ErrorsParameter.ERROR_FORMATO_INVALIDO);
				logger.error("error comviritiendo la fecha : "+fecha);
			}

		}else {
			errors.addParameterError(parameterName, ErrorsParameter.ERROR_VACIO);
		}

		return date;

	}


	public static Date fechaHora(Errors errors,String parameterName) { 


		String fecha = getParameterMap(parameterName);
		Date date = null;

		Date today = Calendar.getInstance().getTime();


		if(!StringUtils.isBlank(fecha)) {
			fecha = fecha.trim();
			try {
				//TODO poner hora
				date = new SimpleDateFormat(DATE_FORMAT).parse(fecha);
				if(date.compareTo(today)>0) {
					errors.addParameterError(parameterName, ErrorsParameter.FECHA_ERROR_PASADA);
				}

			}catch(ParseException e) {
				errors.addParameterError(parameterName,ErrorsParameter.ERROR_FORMATO_INVALIDO);
				logger.error("error comviritiendo la fecha : "+fecha);
			}

		}else {
			errors.addParameterError(parameterName, ErrorsParameter.ERROR_VACIO);
		}

		return date;

	}


	public static char sexo(Errors errors,String parameterName) { 

		String sexo = getParameterMap(parameterName);
		char sexoChar = Character.MIN_VALUE;

		if(!StringUtils.isBlank(sexo)) {
			sexo = sexo.trim();
			if(sexo.equalsIgnoreCase("H") || sexo.equalsIgnoreCase("M"))  {
				sexoChar = sexo.charAt(0);
			}else {
				errors.addParameterError(parameterName, ErrorsParameter.ERROR_FORMATO_INVALIDO);
			}
		}else {
			errors.addParameterError(parameterName, ErrorsParameter.ERROR_VACIO);
		}

		return sexoChar;
	}

	public static String telefono(Errors errors,String parameterName) { 

		String telefono = getParameterMap(parameterName);

		if(!StringUtils.isBlank(telefono)) {
			telefono = telefono.trim();
			if(!TELEFONO_REGEX.matcher(telefono).matches()) {
				errors.addParameterError(parameterName, ErrorsParameter.ERROR_FORMATO_INVALIDO);
			}
		}else {
			errors.addParameterError(parameterName, ErrorsParameter.ERROR_VACIO);
		}

		return telefono;

	}

	public static String biografia(Errors errors,String parameterName) {

		String biografia = getParameterMap(parameterName);

		if(!StringUtils.isBlank(biografia)) {
			biografia = biografia.trim();
			if(biografia.length()>500) {
				errors.addParameterError(parameterName, ErrorsParameter.CONTRASENA_ERROR_FORMATO_INVALIDO);
			}
		}

		return biografia;

	}

	public static String vacio(Errors errors,String parameterName) {

		String texto = getParameterMap(parameterName);

		if(StringUtils.isBlank(texto)) {
			errors.addParameterError(parameterName,ErrorsParameter.ERROR_VACIO );
		}

		return texto;

	}





	public static Integer integerValidator(Errors errors,String parameterName ,Integer minValue,Integer maxValue,Boolean obligatorio) {
		Integer number = null;
		String numberStr = getParameterMap(parameterName);

		if(!StringUtils.isBlank(numberStr)) {
			try {
				number = Integer.valueOf(numberStr);

			}catch (NumberFormatException nfe) {
				errors.addParameterError(parameterName, ErrorsParameter.ERROR_FORMATO_INVALIDO);
				logger.error("error comviritiendo a Long : "+parameterName);
			}
		}else {
			if(obligatorio==true) {
				errors.addParameterError(parameterName, ErrorsParameter.ERROR_VACIO);
			}

		}

		return number;
	}

	public static Long longValidator(Errors errors,String parameterName ,Integer minValue,Integer maxValue,Boolean obligatorio) {
		Long number = null;
		String numberStr = getParameterMap(parameterName);

		if(!StringUtils.isBlank(numberStr)) {
			try {
				number = Long.valueOf(numberStr);
				if(!(number>=minValue && number<=maxValue)) {
					errors.addParameterError(parameterName, ErrorsParameter.ERROR_FUERA_RANGO);
				}

			}catch (NumberFormatException nfe) {
				errors.addParameterError(parameterName, ErrorsParameter.ERROR_FORMATO_INVALIDO);
				logger.error("error comviritiendo a Long : "+numberStr);
			}
		}else {
			if(obligatorio==true) {
				errors.addParameterError(parameterName, ErrorsParameter.ERROR_VACIO);
			}

		}

		return number;
	}


	public static List<Long> longValidator(Errors errors,HttpServletRequest request,String parameterName ) {
		Long number = null;
		List<Long> listLongs = null;
		String[] longs = request.getParameterValues(parameterName);

		if(longs!=null) {
			listLongs = new ArrayList<Long>();
			try {
				for(int i=0;i<longs.length;i++) {
					number = Long.valueOf(longs[i]);
					listLongs.add(number);
				}
			}catch (NumberFormatException nfe) {
				logger.error("error comviritiendo a Long : ");
				errors.addParameterError(parameterName,ErrorsParameter.ERROR_FORMATO_INVALIDO);
				return null;
			}
		}
		return listLongs;

	}




	public static Boolean boleanValidator(Errors errors,String parameterName ,Boolean obligatorio) {
		Boolean boolea = null;
		String booleanStr = getParameterMap(parameterName);

		if(!StringUtils.isBlank(booleanStr)) {
			if(("true".equalsIgnoreCase(booleanStr)||("false".equalsIgnoreCase(booleanStr)))){
				boolea = Boolean.valueOf(booleanStr);
			}else {
				errors.addParameterError(parameterName, ErrorsParameter.ERROR_FORMATO_INVALIDO);
			}

		}else {
			if(obligatorio==true) {
				errors.addParameterError(parameterName, ErrorsParameter.ERROR_VACIO);
			}

		}

		return boolea;
	}






	public static Long longTransform(Errors errors,String parameter) {

		Long number = null;

		if(!StringUtils.isBlank(parameter)) {
			try {
				number = Long.valueOf(parameter);

			}catch (NumberFormatException nfe) {
				logger.error("error comviritiendo a Long el numero  : "+parameter);
			}
		}else {
			if (logger.isInfoEnabled()) {
				logger.info("longTransform vario = "+parameter);
			}			
		}

		return number;
	}


	public static Boolean booleanTransform(Errors errors,String parameter) {

		Boolean boolen = null;

		if(!StringUtils.isBlank(parameter)) {
			try {
				boolen = Boolean.valueOf(parameter);

			}catch (NumberFormatException nfe) {
				logger.error("error comviritiendo a Long el numero  : "+parameter);
			}
		}else {
			if (logger.isInfoEnabled()) {
				logger.info("longTransform vario = "+parameter);
			}			
		}

		return boolen;
	}



}
