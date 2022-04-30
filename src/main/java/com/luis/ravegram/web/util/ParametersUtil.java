package com.luis.ravegram.web.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

public class ParametersUtil {

	public static final String getValue(HttpServletRequest request, String parameterName) {
		return URLDecoder.decode(request.getParameter(parameterName));
	}
	
	
	public static final String getURL(String uri, Map<String, String> parameters) {
		StringBuilder sb = new StringBuilder(uri);
		if (parameters.size()>0) {
			sb.append("?");
		}
		for (String pname: parameters.keySet()) {
			sb.append(URLEncoder.encode(pname)).append("=")
					.append(URLEncoder.encode(parameters.get(pname))).append("&");
		}
		return sb.substring(0, sb.toString().length()-1);
	}
	
	public static final String print(String parameterValue) {
		if (StringUtils.isEmpty(parameterValue)) {
			return Strings.EMPTY;
		} else {
			return parameterValue.trim();
		}
	}
	
	public static final String getURLPaginacion(String uri, Map<String, String[]> parameters) {
		StringBuilder sb = new StringBuilder(uri);
		if (parameters.size()>0) {
			sb.append("?");
		}
		String[] pvalues = null;
		for (String pname: parameters.keySet()) {
			pvalues = parameters.get(pname);
			for (String pvalue: pvalues) {
				sb.append(URLEncoder.encode(pname)).append("=").append(URLEncoder.encode(pvalue)).append("&");
			}
		}
		return sb.toString();
	}
	
	
	public static final String printNull(String parameterValue) {
		if (StringUtils.isEmpty(parameterValue)) {
			return null;
		} else {
			return parameterValue.trim();
		}
	}
	
	
	public static final int printEdad(Date fechaNacimiento) {
		
		Calendar fechaNacimientoCalendar = Calendar.getInstance();
		fechaNacimientoCalendar.setTime(fechaNacimiento);
		
		Calendar fechaActual = Calendar.getInstance();
		
		int anoActual = fechaActual.get(Calendar.YEAR);
		int anoNacimiento = fechaNacimientoCalendar.get(Calendar.YEAR);
		
		return anoActual - anoNacimiento;
	}
	
	public static final String print(Long parameterValue) {
		if (parameterValue ==null) {
			return Strings.EMPTY;
		} else {
			return Long.toString(parameterValue);
		}
	}
	

	
	public static final String print(Double parameterValue) {
		if (parameterValue ==null) {
			return Strings.EMPTY;
		} else {
			return Double.toString(parameterValue);
		}
	}
	
	
	public static final String print(Integer parameterValue) {
		if (parameterValue ==null) {
			return Strings.EMPTY;
		} else {
			return Integer.toString(parameterValue);
		}
	}
	
	
	
	public static final String print(Date parameterValue) {
		if (parameterValue ==null) {
			return Strings.EMPTY;
		} else {
			DateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
			return fecha.format(parameterValue);
		}
	}

	

	

}
