  
  <%@page import="com.luis.ravegram.web.controller.util.ActionNames"%>
<%@page import="com.luis.ravegram.web.controller.util.ParameterNames"%>
<%@page import="com.luis.ravegram.web.controller.util.ViewPaths"%>
<%@page import="com.luis.ravegram.web.controller.util.ControllerPaths"%>
<script>
    function buscarSolicitudesPendientessAjax() {
            var url = '/RavegramWeb/solicitud-service';
                $.ajax({
                   type: "GET",
                   url: url,
               data: "action=solicitud-pediente",
               success: function(data) {
            	$('#solicitudes-results').empty();
                for (i = 0; i<data.length; i++) {
                	$('#solicitudes-results').append('<p style="margin-top:9px; margin-left:9px;"><b>'+data[i].nombreUsuario+'</b> a solicitudo asistir a <b>'+data[i].nombreEvento+'</b><button class="btn-aceptar" onclick="buscarAceptarSolicitud('+data[i].idUsuario+','+data[i].idEvento+')">Aceptar</button><button onclick="buscarRechazarSolicitud('+data[i].idUsuario+','+data[i].idEvento+')" class="btn-denegar">Denegar</button></p>');
                }
              }
            });
                
        }
    
    function buscarAceptarSolicitud(idUsuario,idEvento) {
        var url = '/RavegramWeb/solicitud-service';
            $.ajax({
               type: "GET",
               url: url,
           data: "action=solicitud-aceptar&id="+idUsuario+"&id-dos="+idEvento,
           success: function(data) {
        		$('#solicitudes-results').empty();
        	  $('.solicitudes-pendientes').hide();
            }
        });
    }
    
    function buscarRechazarSolicitud(idUsuario,idEvento) {
        var url = '/RavegramWeb/solicitud-service';
            $.ajax({
               type: "GET",
               url: url,
           data: "action=solicitud-aceptar&id="+idUsuario+"&id-dos="+idEvento,
           success: function(data) {
        	  $('.solicitudes-pendientes').hide();
            }
        });
    }

    
    
    
    
 
    function initMostrarSolicitudes(){
        $('#campana').click(function(){
        	if ($('.solicitudes-pendientes').is(':hidden'))
        		   $('.solicitudes-pendientes').show();
        		else
        		   $('.solicitudes-pendientes').hide();
        	
        });
    }

</script>

 
  
 
    <!-- navigation -->

    <nav class="navbar">
      <div class="nav-wrapper">
         <a href="<%=CONTEXT+ControllerPaths.PRIVATE_AREA_PREFFIX+ViewPaths.HOME%>"> <img class="brand-img" src="<%=CONTEXT%>/css/images/logo.png" /></a>
        <div class="nav-items">
       	  <input type="text" class="search-box" placeholder="Buscar" id="buscador" onkeyup="buscarUsuariosAjax()">
          <a href="<%=CONTEXT%>/<%=ViewPaths.HOME%>"><i class="icon fas fa-home" style="font-size: 20px"></i></a>
          <i class="icon fas fa-bell" style="font-size: 20px" id="campana" onclick="buscarSolicitudesPendientessAjax()" ></i>
          <a href="<%=CONTEXT+ViewPaths.EVENT_CREATE%>"><i class="icon fas fa-plus" style="font-size: 20px"></i></a>
          <i class="icon fas fa-heart" style="font-size: 20px"></i>
          <i class="icon fas fa-location-arrow" style="font-size: 20px"></i>
	          <div class="icon user-profile" style="font-size: 20px">
		              <div class="dropdown" style="float:right;">
		               <i class="fas fa-user" style="font-size: 20px"></i>
			               <div class="dropdown-content">
				               	<a href="<%=CONTEXT+ControllerPaths.PRIVATE_USER%>?<%=ParameterNames.ACTION%>=<%=ActionNames.USER_MY_PROFILE%>"><%=usuario.getUserName()%></a>
				                <a href="<%=CONTEXT+ControllerPaths.PRIVATE_USER%>?<%=ParameterNames.ACTION%>=<%=ActionNames.USER_LOGOUT%>">Cerrar sesion</a>
			              </div>
              		  </div>
         	 
	       </div>
      	</div>
      </div>      	
     </div>
    </div>
      
       <div class="solicitudes-pendientes" id="solicitudes-results">
       </div>
    </nav>

    
    <script >$(document).ready(initMostrarSolicitudes());</script>
    <script >$(document).ready(buscarSolicitudesPendientessAjax());</script>

   