<%@include file="/common/header.jsp"%>


<%@include file="/common/searcher.jsp"%>


<<script>

function buscarFollowersAjax() {
    var url = "/RavegramWeb/usuario-service";
    $('#usuarios').empty();
        $.ajax({
           type: "GET",
           url: url,
       data: "action=user-search-follower&id="+"<%=usuario.getId()%>",
       success: function(data) {
        for (i = 0; i<data.length; i++) {
        	$('#usuarios').append('<option value="'+data[i].id+'">'+data[i].userName+'<option>');
        }
      }
    });
        
}

window.addEventListener("load",comenzar,false);


function comenzar(){
	usuariosASeleccionar = document.getElementById("usuarios");
	usuariosSeleccionados = document.getElementById("usuariosSeleccionados");
	
	usuariosASeleccionar.addEventListener("click",pasar,false);
	usuariosSeleccionados.addEventListener("click",regresar,false);
}



function pasar(){
	let seleccionadas = usuariosASeleccionar.selectedOptions;
	let destino = usuariosSeleccionados;
	if(seleccionadas.length>0){	
		while(seleccionadas.length>0){
			destino.add(seleccionadas[0]);				
		}	
	  }
	}



function regresar(){
	let seleccionadas = usuariosSeleccionados.selectedOptions;
	let destino = usuariosASeleccionar;
	if(seleccionadas.length>0){
		while(seleccionadas.length>0){
			destino.add(seleccionadas[0]);				
		}	
	}
}

function initMostrarAnadir(){
    $('#compartir').click(function(){
    	if ($('.anadir-usuarios').is(':hidden'))
    		   $('.anadir-usuarios').show();
    			
    		else
    		   $('.anadir-usuarios').hide();
    	
    });
}




</script>


										


<%
		
		Map<String,String[]> parameters = new HashMap<String, String[]>(request.getParameterMap());								
		parameters.remove(ParameterNames.PAGE); // para que no arrastre el valor anterior
		
		String urlSinContexto = ParametersUtil.getURLPaginacion(ControllerPaths.PRIVATE_EVENTO, parameters);
		

	
	List<EventoDTO> eventos = (List<EventoDTO>) request.getAttribute(AttributeNames.EVENTS);
	for (EventoDTO e : eventos) {
%>

 <!-- POP UP OCULTO PARA COMAPARTIR-->
		                               
	<div class="anadir-usuarios" style="top:44%; right: 31%">
		<form action="<%=CONTEXT+ControllerPaths.PRIVATE_EVENTO%>" method="post" >
  			<input type="hidden" name="action" value="<%=ActionNames.EVENT_SHARE%>"/>
			<input type="hidden" name="<%=ParameterNames.URL%>" value="<%=urlSinContexto%>"/>
			<input type="hidden" name="<%=ParameterNames.ID%>" value="<%=e.getId()%>"/>
			<div>
				<select class="select" id="usuarios" multiple>
				</select>
			</div>
			<!-- DIV USUARIOS CON LOS QUE COMPARTO -->
			<div>
				<select class="select" id="usuariosSeleccionados" name="<%=ParameterNames.IDS%>" multiple>
				</select>
			</div>
			<input type="submit" value="COMPARTIR"  name="update_profile_btn" id="update_profile_btn" class="update-profile-btn" />
		</form>
	</div>
 
 
   
    <section class="main-detail">
      <div class="wrapper">
        <div class="left-col">
          <div class="post">
            <div class="info">
              <div class="user">
                <div class="profile-pic">
                  <img src="<%=CONTEXT%>/css/images/profile.jpg" alt="" />
                </div>
                <a href="<%=CONTEXT%>/usuario?action=<%=ActionNames.USER_DETAIL%>&<%=ParameterNames.ID%>=<%=e.getIdUsuario()%>"><p class="username"><%=e.getNombreUsuarioCreador() %></p></a>
              </div>
              
                <div class="dropdown" style="float:right;">
		                <i class="fas fa-ellipsis-h options"></i>
			               <div class="dropdown-content">
				               	<p id="compartir">Compartir</p>
				               	<% if (e.getIdUsuario()==usuario.getId()){%>
					                <a href="<%=CONTEXT%>/private/evento?action=<%=ActionNames.EVENT_DETAIL%>&<%=ParameterNames.UPDATE%>=<%=ParameterNames.TRUE%>&<%=ParameterNames.ID%>=<%=e.getId()%>">Editar</a>
					                <a href="<%=CONTEXT%>/private/evento?action=<%=ActionNames.EVENT_DELETE%>&<%=ParameterNames.ID%>=<%=e.getId()%>">Eliminar</a>
				               <%} %>
			              </div>
              		  </div>
              
            </div>
            <a href="<%=CONTEXT%>/private/evento?action=<%=ActionNames.EVENT_DETAIL%>&<%=ParameterNames.ID%>=<%=e.getId()%>"><img src="<%=CONTEXT%>/css/images/1.jpg" style="height:300px;" class="post-image" /></a>
            <div class="post-content">
              
    
									     <div class="btns">
									     <!-- RECHAZAR -->
									     <form action="<%=CONTEXT%>/private/solicitud" method="post" >
									   		<input type="hidden" name="action" value="<%=ActionNames.RECHAZA%>"/>
									   		<input type="hidden" name="<%=ParameterNames.ID%>" value="<%=e.getId()%>"/>
									   		<input type="hidden" name="<%=ParameterNames.URL%>" value="<%=urlSinContexto%>"/>
										    <input type="submit" style="font-size: 55px;height: 100px;width: 100px;border-radius: 50px; background: red" value="X">
										 </form>
										 
										 <form action="<%=CONTEXT%>/private/solicitud" method="post" >
										   	<input type="hidden" name="action" value="<%=ActionNames.SOLICITUD_EVENTO%>"/>
										   	<input type="hidden" name="<%=ParameterNames.ID%>" value="<%=e.getId()%>"/>
										   	<input type="hidden" name="<%=ParameterNames.URL%>" value="<%=urlSinContexto%>"/>
											<input type="submit" style="font-size: 55px;height: 100px;width: 100px;border-radius: 50px;background: #7FFFD4;" value="S">
										  </form>
										    
									  	</div>
         
		                           <!--  Informacion -->
			                            <div class="tab" style="height: 350px">
			                             <div class="informacion-tab" style="height: 100%">
		
											 <!-- TITULO -->
											  <div style="width: 100%; height: 43px;" class="informacion-tab-dentro">
			                                  	<h2 style="text-align: center; width: 100%"><%=e.getNombre()%></h2><br>
			                                  </div>
			                                  <!-- DESCRIPCION -->
			                                  <div style="width: 100%; height: 45px; " class="informacion-tab-dentro">
			                                  	<h4 style="text-align: center; width: 100%"><%=e.getDescripcion()%></h4><br>
			                                  </div>
		
					 							 <!-- FECHA HORA -->
			                                    <div class="informacion-tab-dentro">
			                                        <i class="icon fas fa-calendar" style="font-size: 30px; margin-top: 8px;"><BR>
			                                          <span style="text-align: center;"><%=e.getFechaHora()%></span>
			                                        </i>
			                                    </div>	
			                                    
		                                 		<!-- TIPO TEMATICA -->
		                                 		<%if(e.getTipoTematica()!=null){ %>
				                                    <div class="informacion-tab-dentro">			                                 
				                                      <i class="icon fas fa-mask" style="font-size: 30px; margin-top: 8px;"><br>
				                                       <span style="text-align: center;"><%=e.getTipoTematica()%></span>
				                                      </i>
				                                    </div>
												<%} %>
												
											  <!-- TIPO ESTABLECIMIENTO -->
			                                    <div class="informacion-tab-dentro">
			                                      <i class="icon fas fa-hotel" style="font-size: 30px; margin-top: 8px;"><br>
			                                        <span style="text-align: center;"><%=e.getTipoEstablecimiento()%></span>
			                                      </i>
			                                  </div>
					
											 <!--TIPO MUSICA -->
											 
											 <%if(e.getTipoMusica()!=null){ %>
				                                  <div class="informacion-tab-dentro">
				                                    <i class="icon fas fa-music" style="font-size: 30px; margin-top: 8px;"><br>
				                                        <span style="text-align: center;"><%=e.getTipoMusica()%></span>
				                                    </i>
				                                  </div>
					                    	 <% }%>  
		                                 	<!--PUBLIC O PRIVADO -->
		                                 	<%if(e.getPublicoPrivado()==true){ %>
				                                  <div class="informacion-tab-dentro">
				                                    <i class="icon fas fa-lock" style="font-size: 30px; "><br>
				                                    </i>
				                                  </div>
			                                  <%}else if(e.getPublicoPrivado()==false){ %>
				                                  <div class="informacion-tab-dentro">
					                                    <i class="icon fas fa-unlock" style="font-size: 30px; "><br>
					                                    </i>
					                               </div>
			                                  <%} %>
			                                  
											 <!--CALLE -->
			                                  <div class="informacion-tab-dentro">
			                                    <i class="icon fas fa-road" style="font-size: 30px; "><BR>
			                                      <span style="text-align: center;"><%=e.getCalle()%></span>
			                                    </i>
			                                  </div>
			                                  
											<!--NUM ASISTENTES-->
											<%if(e.getNumAsistentes()!=null|| e.getNumAsistentes()!=0){ %>
			                                  <div class="informacion-tab-dentro">
			                                    <i class="icon fas fa-users" style="font-size: 30px;"><BR>
			                                      <span style="text-align: center;"><%=e.getNumAsistentes()%></span>
			                                    </i>
			                                  </div>
			                                  <%} %>
			                                  
											 <!--DISTANCIA-->
			                                  <div class="informacion-tab-dentro">
			                                    <i class="icon fas fa-location-arrow" style="font-size: 30px;"><BR>
			                                 		<span style="text-align: center;"><%=Math.round(e.getDistanciaKm())%>KM</span>
			                                    </i>
			                                  </div>
			                                  
			                                  
		
		                            </div>
		              			</div>
            </div>
          </div>
        </div>
      </div>
    </section>
    
    
  <% } %>
  
  
      
<script >$(document).ready(buscarFollowersAjax());</script>
<script >$(document).ready(initMostrarAnadir());</script>

<%@include file="/common/footer.jsp"%>