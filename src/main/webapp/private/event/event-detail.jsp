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
					                <a href="<%=CONTEXT%>/private/evento?action=<%=ActionNames.EVENT_DETAIL%>&<%=ParameterNames.UPDATE%>=<%=ParameterNames.TRUE%>&<%=ParameterNames.ID%>=<%=evento.getId()%>">Editar</a>
					                <a href="<%=CONTEXT%>/private/evento?action=<%=ActionNames.EVENT_DELETE%>&<%=ParameterNames.ID%>=<%=evento.getId()%>">Eliminar</a>
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
                          <div>
                              <i class="icon fas fa-calendar"><%=evento.getFechaHora() %></i>
				              <i class="icon fas fa-hotel"></i>
				                
				              <% if (evento.getPublicoPrivado()==true){ %> 
				                	<i class="icon fas fa-unlock"></i>
				              <%}else{%>
				                	<i class="icon fas fa-lock"></i>
				               <%} %>
				                
				               	<i class="icon fas fa-music"><%=evento.getTipoMusica() %></i>
				                <i class="icon fas fa-location-arrow"><%=evento.getDistanciaKm() %></i>
                            </div>
                            <div>
                              <span>
                                <%=evento.getDescripcion()%>
                              </span> 
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
                          <div>
                              <span style="font-size: 30px; margin-left: -800px;"><%=valoracion.getValoracion()%></span>
                              <span style="font-size: 28px;">
                               <%=valoracion.getComentario() %>
                              </span>
                            </div>
                          </div>
                          <%} %>
                          
                          <div class="comment-wrapper">
                             <form action="<%=CONTEXT%>/private/puntuacion" method="post" class="create-valoracion">
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
                          </div>
                          
                          
                          
                        </div>
                      </div>
                    </div>
            </div>
          </div>
        </div>
      </div>
    </section>



<%@include file="/common/footer.jsp"%>
