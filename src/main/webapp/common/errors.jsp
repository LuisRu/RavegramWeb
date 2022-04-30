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
 	 <div class="error-common" id="errors-common">
 		<% for (String error: commonErrors) { %>
 				<span><%=error %></span>
 		<%} %>
    </div>
              
	<%
    }
	%>