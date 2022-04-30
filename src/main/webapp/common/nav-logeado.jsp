
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
                	$('#solicitudes-results').append('<p style="margin-top:9px; margin-left:9px;">Solicitud de <b>'+data[i].nombreUsuario+'</b> para asistir a <b>'+data[i].nombreEvento+'</b><button class="btn-aceptar" onclick="AceptarSolicitud('+data[i].idUsuario+','+data[i].idEvento+')">Aceptar</button><button onclick="RechazarSolicitud('+data[i].idUsuario+','+data[i].idEvento+')" class="btn-denegar">Denegar</button></p>');
                }
              }
            });
                
        }
    
    function AceptarSolicitud(idUsuario,idEvento) {
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
    
    


    
    function RechazarSolicitud(idUsuario,idEvento) {
        var url = '/RavegramWeb/solicitud-service';
            $.ajax({
               type: "GET",
               url: url,
           data: "action=solicitud-rechazar&id="+idUsuario+"&id-dos="+idEvento,
           success: function(data) {
        	   $('#solicitudes-results').empty();
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

    function buscarInvitacionesPendientessAjax() {
        var url = '/RavegramWeb/solicitud-service';
            $.ajax({
               type: "GET",
               url: url,
           data: "action=invitacion-pediente",
           success: function(data) {
        	$('#invitaciones-results').empty();
            for (i = 0; i<data.length; i++) {
            	$('#invitaciones-results').append('<p style="margin-top:9px; margin-left:9px;">Invitacion de para asistir a <b>'+data[i].nombreEvento+'</b><button class="btn-aceptar" onclick="AceptarInvitacion('+data[i].idUsuario+','+data[i].idEvento+')">Aceptar</button><button onclick="RechazarInvitacion('+data[i].idUsuario+','+data[i].idEvento+')" class="btn-denegar">Denegar</button></p>');
            }
          }
        });
            
    }
    
    function AceptarInvitacion(idUsuario,idEvento) {
        var url = '/RavegramWeb/solicitud-service';
            $.ajax({
               type: "GET",
               url: url,
           data: "action=solicitud-aceptar&id="+idUsuario+"&id-dos="+idEvento,
           success: function(data) {
        		 $('#invitaciones-results').empty();
        		 $('.invitaciones-pendientes').hide();
            }
        });
    }
    
    
    function RechazarInvitacion(idUsuario,idEvento) {
        var url = '/RavegramWeb/solicitud-service';
            $.ajax({
               type: "GET",
               url: url,
           data: "action=solicitud-rechazar&id="+idUsuario+"&id-dos="+idEvento,
           success: function(data) {
        		 $('#invitaciones-results').empty();
        		 $('.invitaciones-pendientes').hide();
        	   
            }
        });
    }
    
    
    function initMostrarInvitaciones(){
        $('#corazon').click(function(){
        	buscarInvitacionesPendientessAjax();
        	if ($('.invitaciones-pendientes').is(':hidden'))
        		   $('.invitaciones-pendientes').show();
        		else
        		   $('.invitaciones-pendientes').hide();
        });
    }
    
    function obtenerUbicacion(){
    	$('#localizacion').click(function(){
    		navigator.geolocation.getCurrentPosition(actualizarUbicacion);
    	});
    }
    
         	
        	
        
        function actualizarUbicacion(position) {
        	var latitud =  position.coords.latitude;
    		var longitud = position.coords.longitude;
            var url = '/RavegramWeb/usuario-service';
            $.ajax({
                   type: "GET",
                   url: url,
            	   data: "action=user-update-ubicacion&latitud="+latitud+"&longitud="+longitud,
               	   success: function(data) {
               		if(data=="OK"){
               			alert("Ubicacion actualizada con exito");
	            	}else{
	            		alert("Lo sentimos, ocurrio algun problema al acualizar su ubicacion");
	            	}
               	   }
            });
        }	
        
        
        
        
        
    
</script>

 
  
 
    <!-- navigation -->

    <nav class="navbar">
      <div class="nav-wrapper">
         <a href="<%=CONTEXT%>"> <img class="brand-img" src="<%=CONTEXT%>/css/images/logo.png" /></a>
        <div class="nav-items">
       	  <input type="text" class="search-box" placeholder="Buscar" id="buscador" onkeyup="buscarUsuariosAjax()">
          <a href="<%=CONTEXT%><%=ViewPaths.HOME%>"><i class="icon fas fa-home" style="font-size: 20px"></i></a>
          <i class="icon fas fa-bell" style="font-size: 20px" id="campana"></i>
          <i class="icon fas fa-heart" style="font-size: 20px" id="corazon"></i>
          <a href="<%=CONTEXT+ViewPaths.EVENT_CREATE%>"><i class="icon fas fa-plus" style="font-size: 20px"></i></a>
          <i class="icon fas fa-location-arrow" id="localizacion" style="font-size: 20px"></i>
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
       <div class="invitaciones-pendientes" id="invitaciones-results">
       </div>
    </nav>

 	<script >$(document).ready(obtenerUbicacion());</script>
    <script >$(document).ready(initMostrarSolicitudes());</script>
    <script >$(document).ready(initMostrarInvitaciones());</script>
    <script >$(document).ready(buscarSolicitudesPendientessAjax());</script>
    <script >$(document).ready(buscarInvitacionesPendientessAjax());</script>

   