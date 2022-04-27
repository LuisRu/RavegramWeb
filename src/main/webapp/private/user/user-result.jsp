<%@include file="/common/header.jsp"%>



<%@include file="/common/searcher.jsp"%>

<script>
 function buscarEventosDisponiblesAjax(){
        var url = "/RavegramWeb/evento-service";
            $.ajax({
               type: "GET",
               url: url,
           data: "action=event-dispo",
           success: function(data) {
        	$('#select-event-dispo').empty();
            for (i = 0; i<data.length; i++) {
            	$('#select-event-dispo').append('<option value="'+data[i].id+'">'+data[i].nombre+'</option>');
            }
          }
        });
            
    }
</script>

 <script >$(document).ready(buscarEventosDisponiblesAjax());</script>

      
<%
	
	List<UsuarioDTO> usuarios = (List<UsuarioDTO>) request.getAttribute(AttributeNames.USERS);
	for (UsuarioDTO u : usuarios) {
%>
      
      

     <!--POST PROFILE-->
 	 <section class="main">
     	<div class="card">
        	<img src="<%=CONTEXT%>/css/images/profile.jpg" alt="Image">
	        <div class="details">
	            <h2><%=u.getUserName()%>,<%=u.getFechaNacimiento()%></h2>
	        </div>
	        <p id="info">
	         <%=u.getBiografia() %>
	        </p>
     	</div>  
    </section>
    
      <select id="select-event-dispo">
      
      </select>
             
  
   <% } %>
  
  
 

<%@include file="/common/footer.jsp"%>