<%@include file="/common/header.jsp"%>


<%=ActionNames.EVENT_DISPO%>

<%@include file="/common/searcher.jsp"%>

<script>
 function buscarEventosDisponiblesAjax(){
        var url = "<%=CONTEXT+ControllerPaths.EVENTO_WEB%>";
            $.ajax({
               type: "GET",
               url: url,
           data: "action=<%=ActionNames.EVENT_DISPO%>",
           success: function(data) {
        	   if(data.length<1){
        		   $('#select-event-dispo').append('<option >No tienes eventos disponibles</option>');
        	   }else{
        		   $('#select-event-dispo').empty();
                   for (i = 0; i<data.length; i++) {
                   	$('#select-event-dispo').append('<option value="'+data[i].id+'">'+data[i].nombre+'</option>');
                   }   
        	   }
          }
        });
            
    }
</script>

 <script >$(document).ready(buscarEventosDisponiblesAjax());</script>

      
<%
	Integer currentPage = (Integer) request.getAttribute(AttributeNames.CURRENT_PAGE);
	
	Integer pagingFrom = (Integer) request.getAttribute(AttributeNames.PAGING_FROM);
	Integer pagingTo = (Integer) request.getAttribute(AttributeNames.PAGING_TO);
	
	Integer totalPages = (Integer) request.getAttribute(AttributeNames.TOTAL_PAGES);
	
	Map<String,String[]> parameters = new HashMap<String, String[]>(request.getParameterMap());								
	parameters.remove(ParameterNames.PAGE); // para que no arrastre el valor anterior
	
	// Ya viene terminada en &
	String baseURL = ParametersUtil.getURLPaginacion(request.getContextPath()+ControllerPaths.PRIVATE_USER, parameters);
	String url = baseURL+ParameterNames.PAGE+"="+(currentPage+1);
	String urlSinContexto = ParametersUtil.getURLPaginacion(ControllerPaths.PRIVATE_USER, parameters)+ParameterNames.PAGE+"="+(currentPage+1);;
	
	List<UsuarioDTO> usuarios = (List<UsuarioDTO>) request.getAttribute(AttributeNames.USERS);
	for (UsuarioDTO u : usuarios) {
%>
      
      

     <!--POST PROFILE-->
 	 <section class="main">
     	<div class="card">
        	<img src="<%=CONTEXT%>/css/images/profile.jpg" alt="Image">
	        <div class="details">
	            <a href="<%=CONTEXT+ControllerPaths.USER%>?action=<%=ActionNames.USER_DETAIL%>&id=<%=u.getId()%>"><h2><%=u.getUserName()%>,<%=ParametersUtil.printEdad(u.getFechaNacimiento())%><span> </span>años</h2></a>
	        </div>
	        <p id="info">
	         	<%=u.getBiografia() %>
	        </p>
	        <!-- Siguiente -->
    		<% if (currentPage<=totalPages) { %>
				<form action="<%=CONTEXT%>/private/solicitud" method="post" >
			   	 	<input type="hidden" name="action" value="<%=ActionNames.INVITIACION_USUARIO%>"/>
			   	 	<input type="hidden" name="<%=ParameterNames.URL%>" value="<%=urlSinContexto%>"/>
			   	 	<input type="hidden" name="<%=ParameterNames.ID%>" value="<%=u.getId()%>"/>
				    <select  style="text-align:center;min-width: 300px;" multiple id="select-event-dispo" name="<%=ParameterNames.IDS%>">
			 		</select>
			 		<button class="boton-invitar" type="submit">Invitar</button>
				</form>
				<a href="<%=url%>"><button class="boton-next">Next</button></a>
			<% } %>
     	</div>  
    </section>
    
    	
 
   <% } %>
  
 
 

<%@include file="/common/footer.jsp"%>