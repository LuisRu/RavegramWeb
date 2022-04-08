<%@include file="/common/header.jsp"%>



<script>

		function buscarEstadosEventoAjax() {
		    var url = "<%=CONTEXT%>/solicitud-service";
		        $.ajax({
		           type: "GET",
		           url: url,
		       data: "action=event-all-states",
		       success: function(data) {
		    	$('#estado-evento').empty();
		        for (i = 0; i<data.length; i++) {
		        	$('#estado-evento').append('<option>'+data[i].nombre+'</option>');
		        }
		      }
		    });
		        
		}



		function buscarSeguidosMutuamenteAjax() {
		    var url = "/Ravegram/usuario-service";
		        $.ajax({
		           type: "GET",
		           url: url,
		       data: "action=user-search-follow-mutual&id="+<%=request.getParameter(ParameterNames.ID)%>,
		       success: function(data) {
		    	$('#usuarios').empty();
		        for (i = 0; i<data.length; i++) {
		        	$('#usuarios').append('<option>'+data[i].userName+'</option>');
		        }
		      }
		    });
		        
		}



		function initMostrarA�adir(){
		    $('#a�adir-usuarios-popup').click(function(){
		    	if ($('.a�adir-usuarios').is(':hidden'))
		    		   $('.a�adir-usuarios').show();
		    			
		    		else
		    		   $('.a�adir-usuarios').hide();
		    	
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


</script>





	<%

	EventoDTO evento = (EventoDTO) request.getAttribute(AttributeNames.EVENT);
	List<UsuarioDTO> usuarios = (List<UsuarioDTO>) request.getAttribute(AttributeNames.USERS);
	List<UsuarioEventoPuntuaDTO> puntuaciones = (List<UsuarioEventoPuntuaDTO>) request.getAttribute(AttributeNames.PUNTUACIONES);
	
	
	%>


 <form action="<%=CONTEXT%>/private/evento" method="post">
	<input type="hidden" name="action" value="<%=ActionNames.EVENT_UPDATE%>"/>
	            
     <section class="main-detail">
      <div class="wrapper">
        <div class="left-col">
          <div class="post">
            <div class="info">
              <div class="user">
                <div class="profile-pic">
                  <img src="<%=CONTEXT%>/css/images/profile.jpg" alt="" />
                </div>
                <a href="<%=CONTEXT%>/usuario?action=<%=ActionNames.USER_DETAIL%>&<%=ParameterNames.ID%>=<%=evento.getIdUsuario()%>"><p class="username"><%=evento.getNombreUsuarioCreador() %></p></a>
              </div>
             
            </div>
            <img src="<%=CONTEXT%>/css/images/1.jpg" class="post-image" />
            <div class="tabspost">
                    <div class="post-filtros">
                      <div class="tabs2">
          
                       
                           <!--  Informacion -->
                            <input type="radio" name="tabs2" id="tabone2" checked="checked">
                            <label for="tabone2">Informacion</label>
                            <div class="tab">
                              <div>

                                  <i class="icon fas fa-calendar">
                                    <input type="date" class="form-control" name="<%=ParameterNames.FECHA_HORA%>" value=""  />
                                  </i>

                                  <i class="icon fas fa-calendar">
                                    <select>
                                      <option>TIPO TEMATICA</option>
                                    </select>
                                  </i>

                                  <i class="icon fas fa-hotel">
                                    <select>
                                      <option>TIPO ESTABLECIMIENTO</option>
                                    </select>
                                  </i>
                                  
                                  <i class="icon fas fa-hotel">
                                    <select>
                                      <option>ESTABLECIMIENTO</option>
                                    </select>
                                  </i>

                                  <i class="icon fas fa-music">
                                    <select>
                                      <option>TIPO MUSICA</option>
                                    </select>
                                  </i>

                                  <i class="icon fas fa-unlock"> 
                                    <select>
                                      <option>public privado</option>
                                    </select>
                                  </i>
                                 
                                  <i class="icon fas fa-music">
                                    <input type="text" class="form-control" name="<%=ParameterNames.CALLE%>" value="<%=ParametersUtil.print(evento.getCalle())%>" placeholder="calle"/>
                                  </i>
                                  <i class="icon fas fa-music">
                                    NUM ASISTENTES
                                  </i>
                                  <i class="icon fas fa-location-arrow">
                                    ubicacion
                                  </i>
                                </div>
                                <div>
                                  <textarea name="<%=ParameterNames.DESCRIPCION%>" placeholder="Descripcion" class="form-control" cols="30" rows="5"><%=ParametersUtil.print(evento.getDescripcion())%></textarea>
                                </div>
                            </div>
              
          
                         <!--  Asistentes -->
                        <input type="radio" name="tabs2" id="tabtwo2">
                        <label for="tabtwo2">Asistentes</label>
                        <div class="tab">
                            <div class="asistente-tab">
                            
                            <!-- A�ADIR  ASISTENTES-->
                           
                               <div class="asistente-tab-dentro" id="a�adir-usuarios-popup">
                                    <div class="asistente-pic">
                                      <img src="<%=CONTEXT%>/css/images/add-user.png" alt="" />
                                    </div>
                                    <div class="aisitente-name">
                                      <span>A�ADIR</span>
                                    </div>
                               </div>
                               
                               <!-- POP UP OCULTO PARA A�ADIR USUARIOS -->
                               
                               <div class="a�adir-usuarios">
									<div>
										<select class="select" id="usuarios" multiple>
											
										</select>
									</div>
									<div>
										<select class="select" id="usuariosSeleccionados" multiple>
										</select>
									</div>
									<button class="btn-a�adir-usuarios">A�adir</button>
								</div>
                           
                            
                            
                            <!--ASISTENTES-->
                               <% for (UsuarioDTO u : usuarios) { %>
                                <div class="asistente-tab-dentro">
                                  <div class="asistente-pic">
                                    <img src="<%=CONTEXT%>/css/images/profile.jpg" alt="" />
                                  </div>
                                  <div class="aisitente-name">
                                    <span><%=u.getUserName()%></span>
                                  </div>
                                </div>
                                
								<%} %>
                        

                            </div>
                        </div>
                      
                         <!--  Valoraciones -->
                        <input type="radio" name="tabs2" id="tabthree2">
                        <label for="tabthree2">Valoraciones</label>
                        <div class="tab">
                         <% for (UsuarioEventoPuntuaDTO valoracion : puntuaciones) { %>
                          <div class="post-content">
                          <div>
                              <span style="font-size: 30px; margin-left: -800px;"><%=valoracion.getValoracion()%></span>
                              <span style="font-size: 28px;">
                               <%=valoracion.getComentario() %>
                              </span>
                            </div>
                          </div>
                          <%} %>
                          
                          <div class="comment-wrapper">
                      			<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=ActionNames.PUNTUACION_CREATE%>"/>
                      			<input type="hidden" name="<%=ParameterNames.ID%>" value="<%=evento.getId()%>"/>
	                      
                          </div>
                        </div>
                      </div>
                         <div class="mb-3">
	            			  <input type="submit" value="UPDATE"  name="update_profile_btn" id="update_profile_btn" class="update-profile-btn" />
	            		</div>
	           	</form>
              </div>
            </div>
          </div>
        </div>
      </div>        
    </section>
  
    
    
    

<script >$(document).ready(initMostrarA�adir());</script>
<script >$(document).ready(buscarSeguidosMutuamenteAjax());</script>



<%@include file="/common/footer.jsp"%>
