<%@page import="com.luis.ravegram.model.state.EventoEstado"%>
<%@include file="/common/header.jsp"%>




<script>


</script>





	<%

	EventoDTO evento = (EventoDTO) request.getAttribute(AttributeNames.EVENT);
	List<UsuarioDTO> asistentes = (List<UsuarioDTO>) request.getAttribute(AttributeNames.ASISTENTES);
	List<UsuarioEventoPuntuaDTO> puntuaciones = (List<UsuarioEventoPuntuaDTO>) request.getAttribute(AttributeNames.PUNTUACIONES);
	
	
	%>

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
              
               <!-- TRES PUNTOS DE LA IZQUIERDA -->
		              <div class="dropdown" style="float:right;">
		                <i class="fas fa-ellipsis-h options"></i>
			               <div class="dropdown-content">
				               	<a href="">Compartir</a>
				               	<% if (evento.getIdUsuario()==usuario.getId()){%>
					                <a href="<%=CONTEXT+ControllerPaths.PRIVATE_EVENTO%>?action=<%=ActionNames.EVENT_DETAIL%>&<%=ParameterNames.UPDATE%>=<%=ParameterNames.TRUE%>&<%=ParameterNames.ID%>=<%=evento.getId()%>">Editar</a>
					                <a href="<%=CONTEXT+ControllerPaths.PRIVATE_EVENTO%>?action=<%=ActionNames.EVENT_DELETE%>&<%=ParameterNames.ID%>=<%=evento.getId()%>">Eliminar</a>
					                <%if (evento.getIdTipoEstadoEvento()!=EventoEstado.FINALIZADO){ %>
					                <a href="<%=CONTEXT+ControllerPaths.PRIVATE_EVENTO%>?action=<%=ActionNames.EVENT_FINALIZAR%>&<%=ParameterNames.ID%>=<%=evento.getId()%>">Finalizar</a>
					                <%} %>
				               <%} %>
				               
			              </div>
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
                          <div class="informacion-tab" style="height: 100%">
					
														 <!-- TITULO -->
														  <div style="width: 100%; height: 43px;" class="informacion-tab-dentro">
						                                  	<h2 style="text-align: center; width: 100%"><%=evento.getNombre()%></h2><br>
						                                  </div>
						                                  <!-- DESCRIPCION -->
						                                  <div style="width: 100%; height: 45px; " class="informacion-tab-dentro">
						                                  	<h4 style="text-align: center; width: 100%"><%=evento.getDescripcion()%></h4><br>
						                                  </div>
					
								 							 <!-- FECHA HORA -->
						                                    <div class="informacion-tab-dentro">
						                                        <i class="icon fas fa-calendar" style="font-size: 30px; margin-top: 8px;"><BR>
						                                          <span style="text-align: center;"><%=evento.getFechaHora()%></span>
						                                        </i>
						                                    </div>	
						                                    
					                                 		<!-- TIPO TEMATICA -->
					                                 		<%if(evento.getTipoTematica()!=null){ %>
							                                    <div class="informacion-tab-dentro">			                                 
							                                      <i class="icon fas fa-mask" style="font-size: 30px; margin-top: 8px;"><br>
							                                       <span style="text-align: center;"><%=evento.getTipoTematica()%></span>
							                                      </i>
							                                    </div>
															<%} %>
															
														  <!-- TIPO ESTABLECIMIENTO -->
						                                    <div class="informacion-tab-dentro">
						                                      <i class="icon fas fa-hotel" style="font-size: 30px; margin-top: 8px;"><br>
						                                        <span style="text-align: center;"><%=evento.getTipoEstablecimiento()%></span>
						                                      </i>
						                                  </div>
								
														 <!--TIPO MUSICA -->
														 <!-- NO FUNCIONA CON NULL NO SE PORQUE -->
														 <%if(evento.getTipoMusica()!=null){ %>
							                                  <div class="informacion-tab-dentro">
							                                    <i class="icon fas fa-music" style="font-size: 30px; margin-top: 8px;"><br>
							                                        <span style="text-align: center;"><%=evento.getTipoMusica()%></span>
							                                    </i>
							                                  </div>
								                    	 <% }%>  
					                                 	<!--PUBLIC O PRIVADO -->
					                                 	<%if(evento.getPublicoPrivado()==true){ %>
							                                  <div class="informacion-tab-dentro">
							                                    <i class="icon fas fa-lock" style="font-size: 30px; "><br>
							                                    </i>
							                                  </div>
						                                  <%}else if(evento.getPublicoPrivado()==false){ %>
							                                  <div class="informacion-tab-dentro">
								                                    <i class="icon fas fa-unlock" style="font-size: 30px; "><br>
								                                    </i>
								                               </div>
						                                  <%} %>
						                                  
														 <!--CALLE -->
						                                  <div class="informacion-tab-dentro">
						                                    <i class="icon fas fa-road" style="font-size: 30px; "><BR>
						                                      <span style="text-align: center;"><%=evento.getCalle()%></span>
						                                    </i>
						                                  </div>
						                                  
														<!--NUM ASISTENTES-->
														<%if(evento.getNumAsistentes()!=null || evento.getNumAsistentes()!=0){ %>
						                                  <div class="informacion-tab-dentro">
						                                    <i class="icon fas fa-users" style="font-size: 30px;"><BR>
						                                      <span style="text-align: center;"><%=evento.getNumAsistentes()%></span>
						                                    </i>
						                                  </div>
						                                  <%} %>
						                                  
														 <!--DISTANCIA-->
						                                  <div class="informacion-tab-dentro">
						                                    <i class="icon fas fa-location-arrow" style="font-size: 30px;"><BR>
						                                 		<span style="text-align: center;"><%=Math.round(evento.getDistanciaKm())%>KM</span>
						                                    </i>
						                                  </div>
						                                  
						                                 
					                            </div>
                        </div>
          
                         <!--  Asistentes -->
                        <input type="radio" name="tabs2" id="tabtwo2">
                        <label for="tabtwo2">Asistentes</label>
                        <div class="tab">
                            <div class="asistente-tab">
                               
                    
                           
                            
                            
                            <!--ASISTENTES-->
                               <% for (UsuarioDTO u : asistentes) { %>
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
                              <span style="font-size: 30px; margin-left: -800px;"><%=valoracion.getValoracion()%></span>
                              <span style="font-size: 28px;">
                               <%=valoracion.getComentario() %>
                              </span>
                          </div>
                          <%} %>
                          
                          <%if(evento.getIdTipoEstadoEvento()==EventoEstado.FINALIZADO) {%>
                          
	                             <form action="<%=CONTEXT+ControllerPaths.PRIVATE_PUNTUACION%>" method="post" class="create-valoracion">
	                      			<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=ActionNames.PUNTUACION_CREATE%>"/>
	                      			<input type="hidden" name="<%=ParameterNames.ID%>" value="<%=evento.getId()%>"/>
	                      			<% if (usuario.getId()!=evento.getIdUsuario()){ %>
	                      				<input type="text" class="comment-box" name="<%=ParameterNames.COMENTARIO%>" placeholder="Add a comment"/>
	                      				<select name="<%=ParameterNames.VALORACION%>" class="nada">
					                          <option value="0">Valoracion  0</option>
					                          <option value="1">Valoracion  1</option>
					                          <option value="2">Valoracion  2</option>
					                          <option value="3">Valoracion  3</option>
					                          <option value="4">Valoracion  4</option>
					                          <option value="5">Valoracion  5</option>			                    
		                        		</select>
			                            <button type="submit"  class="">POST</button>
	                      			<%  } %>
		                        	</form>
	                        <%} %>
                          
                          
                          
                          
                        </div>
                      </div>
                    </div>
            </div>
          </div>
        </div>
      </div>
    </section>



<%@include file="/common/footer.jsp"%>
