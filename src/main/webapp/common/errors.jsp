<%@ page import="java.util.List,com.luis.ravegram.model.*,com.luis.ravegram.web.controller.util.*, com.luis.ravegram.web.controller.*, com.luis.ravegram.web.util.*" %>
 <%
 	Errors errors = (Errors) request.getAttribute(AttributeNames.ERRORS);
 	if (errors == null) {
 		errors = new Errors(); // Primera renderizacion
 	}
 
 	// Variable comun para los errors por parametro
 	String parameterError = null;
              	
 	List<String> commonErrors = errors.getCommonErrors();
 	if (commonErrors.size()>0) {
 %>
 	<div class="errors">
 		<%
 			for (String error: commonErrors) {
 				%><li><%=error %></li>
 			<%
        	}
        %>
    </div>
              
	<%
    }
	%>