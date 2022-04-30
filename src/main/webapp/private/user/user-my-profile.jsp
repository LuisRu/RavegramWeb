<%@include file="/common/header.jsp"%>




<script>

	function initMostrarFollowers(){
	    $('#followers').click(function(){
	    	if ($('.followers').is(':hidden'))
	    		   $('.followers').show();
	    		else
	    		   $('.followers').hide();
	    	
	    });
	}
	
    function initOcultarFollow(){
    	$('#close-x-followers').click(function(){
    		$('.followers').hide();
    	});
    	$('#close-x-following').click(function(){
    		$('.following').hide();
    	});
	}

	
	
	function initMostrarFollowing(){
	    $('#following').click(function(){
	    	if ($('.following').is(':hidden'))
	    		   $('.following').show();
	    		else
	    		   $('.following').hide();
	    	
	    }); 
	}
	
	function initOcultarFollowing(){
	    $('#following').click(function(){
	    	if ($('.following').is(':hidden'))
	    		   $('.following').show();
	    		else
	    		   $('.following').hide();
	    	
	    }); 
	}

    
    function buscarFollowersAjax() {
        var url = "/RavegramWeb/usuario-service";
            $.ajax({
               type: "GET",
               url: url,
           data: "action=user-search-follower&id="+<%=usuario.getId()%>,
           success: function(data) {
        	$('#div-followers').empty();
            for (i = 0; i<data.length; i++) {
            	$('#div-followers').append('<a href="/RavegramWeb/usuario?action=user-detail&id='+data[i].id+'"><p class="nombre-usuarios">'+data[i].userName+'</p></a>');
            	 
            }
          }
        });
            
    }
    
    
    function buscarFollowingAjax() {
        var url = "/RavegramWeb/usuario-service";
            $.ajax({
               type: "GET",
               url: url,
           data: "action=user-search-following&id="+<%=usuario.getId()%>,
           success: function(data) {
        	$('#div-following').empty();
            for (i = 0; i<data.length; i++) {
            	$('#div-following').append('<a href="/RavegramWeb/usuario?action=user-detail&id='+data[i].id+'"><p class="nombre-usuarios">'+data[i].userName+'</p></a>');
            }
          }
        });
            
    }

        
</script>


<%
	
    List<EventoDTO> eventosDisponibles = (List<EventoDTO>) request.getAttribute(AttributeNames.EVENTS);
	List<EventoDTO> eventosHistorial = (List<EventoDTO>) request.getAttribute(AttributeNames.EVENTS_DISPO);
    List<UsuarioEventoPuntuaDTO> puntuaciones = (List<UsuarioEventoPuntuaDTO>) request.getAttribute(AttributeNames.PUNTUACIONES);
		
		
	
	
%>


 <div  id="followers-div" class="followers">
      <p style="font-size: 28px;  background-color: white; border-radius: 20px;">Followers<span   id="close-x-followers" class="cerrar-x">x</span></p>
      <div id="div-followers" class="dentro-followers"> 
        
      </div>
    </div>

    <div  id="following-div" class="following">
      <p style="font-size: 28px;  background-color: white; border-radius: 20px;">Following<span id="close-x-following" class="cerrar-x">x</span></p>
      <div id="div-following" class="dentro-following"> 
        
      </div>
    </div>





  <header class="profile-header">
      <div class="profile-container">
        <div class="profile">
          <div class="profile-image">
            <img src="<%=CONTEXT%>/css/images/profile.jpg" alt="" />
          </div>
          <div class="profile-user-settings">
            <h1 class="profile-user-name"><%=usuario.getUserName() %></h1>
            
            
            <!-- BOTON EDIT PROFILE -->
         
            	<a href="<%=CONTEXT+ViewPaths.USER_EDIT%>"><button class="profile-btn profile-edit-btn" >Edit Profile</button></a>
         
          
          
          
          </div>
          <div class="profile-stats">
            <ul>
              <li><span class="profile-stat-count"></span> </li>
              <li id="followers" ><span class="profile-stat-count"></span> FOLLOWERS</li>
              <li id="following"><span class="profile-stat-count"></span> FOLLOWING</li>
            </ul>
 
 			
          </div>
          
          
          <!-- BIOGRAFIA -->
          <div class="profile-bio" style="text-align: center; width: 100%">
            <p style="text-align: center">
              <%=usuario.getBiografia() %>
            </p>
          </div>
          
          
        </div>
      </div>
      
        <section class="main-profile">
			      <div class="wrapper">
			        <div class="left-col">
			          <div class="post-filtros">
			            <div class="tabs">
			              <!-- EVENTOS DISPONIBLES -->
			              <input type="radio" name="tabs" id="tabone" checked="checked">
			              <label for="tabone">Evento Disponibles</label>
			              <div class="tab">
								    
								    <main>
                 					  <div class="profile-container">
								    
								 		  <% for (EventoDTO e : eventosDisponibles) { %>
								 		  	 <a href="<%=CONTEXT%>/private/evento?action=<%=ActionNames.EVENT_DETAIL%>&<%=ParameterNames.ID%>=<%=e.getId()%>">
										        <div class="post-profile">
											      	<div class="post-profile-foto">
											        	<img src="<%=CONTEXT%>/css/images/profile.jpg">
											      	</div>
											      	<div class="post-profile-info">
											        	<h3><%=e.getNombre()%></h3>
											        	<span style="font-size: 16px;">
											        		<%=e.getDescripcion() %>
											        	</span>
												        <div class="post-profile-iconos">
												          <i style="font-size: 20px;" class="icon fas fa-calendar"><%=e.getFechaHora()%></i>
												          
												          <%if(e.getTipoTematica()!=null) {%>
												          	<i style="font-size: 20px;" class="icon fas fa-mask"><%=e.getTipoTematica()%></i>
												          <%} %>
												          
												          <i style="font-size: 20px;" class="icon fas fa-hotel"><%=e.getTipoEstablecimiento()%></i>
												          
												          <%if(e.getEstablecimiento()!=null) {%>
												          <i style="font-size: 20px;" class="icon fas fa-hotel"><%=e.getEstablecimiento()%></i>
												          <%} %>
												          
												          <%if(e.getTipoMusica()!=null) {%>
												          <i style="font-size: 20px;" class="icon fas fa-music"><%=e.getTipoMusica()%></i>
												          <%} %>
												          
												          <%if(e.getPublicoPrivado()==true) {%>
												          	<i style="font-size: 20px;" class="icon fas fa-lock"></i>
												          <%}else if(e.getPublicoPrivado()==false){ %>
												          	<i style="font-size: 20px;" class="icon fas fa-unlock"></i>
												          <%} %>
												          
												          <i style="font-size: 20px;" class="icon fas fa-road"><%=e.getCalle()%></i>
												          
												          <%if(e.getNumAsistentes()!=null||e.getNumAsistentes()!=0){ %>
												          <i style="font-size: 20px;" class="icon fas fa-users"><%=e.getNumAsistentes()%></i>
												          <%} %>
												          
												          <i style="font-size: 20px;" class="icon fas fa-location-arrow"><%=Math.round(e.getDistanciaKm())%>KM</i>
												          
												        </div>
											         </div>  	
											       </div>
											  </a>
									       <%} %>
									       
									    </div>
                					</main>
				
			              </div>

				
			              
			
			
			                <!--  Historial -->
			              <input type="radio" name="tabs" id="tabtwo">
			              <label for="tabtwo">Historial</label>
			                         <div class="tab">
								    
								    <main>
                 					  <div class="profile-container">
								    
								 		  <% for (EventoDTO e : eventosHistorial) { %>
								 		  	 <a href="<%=CONTEXT%>/private/evento?action=<%=ActionNames.EVENT_DETAIL%>&<%=ParameterNames.ID%>=<%=e.getId()%>">
										        <div class="post-profile">
											      	<div class="post-profile-foto">
											        	<img src="<%=CONTEXT%>/css/images/profile.jpg">
											      	</div>
											      	<div class="post-profile-info">
											        	<h3><%=e.getNombre()%></h3>
											        	<span style="font-size: 16px;">
											        		<%=e.getDescripcion() %>
											        	</span>
												        <div class="post-profile-iconos">
												          <i style="font-size: 20px;" class="icon fas fa-calendar"><%=e.getFechaHora()%></i>
												          
												          <%if(e.getTipoTematica()!=null){ %>
												          	<i style="font-size: 20px;" class="icon fas fa-mask"><%=e.getTipoTematica()%></i>
												          <%} %>
												          
												          <i style="font-size: 20px;" class="icon fas fa-hotel"><%=e.getTipoEstablecimiento()%></i>
												          
												          <%if(e.getEstablecimiento()!=null) {%>
												          <i style="font-size: 20px;" class="icon fas fa-hotel"><%=e.getEstablecimiento()%></i>
												          <%} %>
												          
												          <%if(e.getTipoMusica()!=null) {%>
												          <i style="font-size: 20px;" class="icon fas fa-music"><%=e.getTipoMusica()%></i>
												          <%} %>
												          
												          <%if(e.getPublicoPrivado()==true) {%>
												          	<i style="font-size: 20px;" class="icon fas fa-lock"></i>
												          <%}else if(e.getPublicoPrivado()==false){ %>
												          	<i style="font-size: 20px;" class="icon fas fa-unlock"></i>
												          <%} %>
												          
												          <i style="font-size: 20px;" class="icon fas fa-road"><%=e.getCalle()%></i>
												          
												          <%if(e.getNumAsistentes()!=null||e.getNumAsistentes()!=0){ %>
												          <i style="font-size: 20px;" class="icon fas fa-users"><%=e.getNumAsistentes()%></i>
												          <%} %>
												          
												          <i style="font-size: 20px;" class="icon fas fa-location-arrow"><%=Math.round(e.getDistanciaKm())%>KM</i>
												          
												        </div>
											         </div>  	
											       </div>
											  </a>
									       <%} %>
									       
									    </div>
                					</main>
				
			              </div>
			            
			            
			               <!--  Valoraciones -->
			              <input type="radio" name="tabs" id="tabthree">
			              <label for="tabthree">Valoraciones</label>
			              <div class="tab">
			              
				                <main>
				                  <div class="profile-container">
				                  
				                  <!-- DONDE ESTA TODO -->
				                    <div class="post-profile-valoracion">
				                    
				                   			 <!-- DONDE ESTA LA MEDIA -->
						                      <div style="width: 100%; margin-top: 10px; margin-bottom: 20px;">
						                        <div class="valoracion">
						                          <span><%=usuario.getValoracionMedia()%></span>
						                        </div>
						                      </div>  
						                
						                	<!-- listado puntuaciones -->
						               		 <%for(UsuarioEventoPuntuaDTO puntuacion : puntuaciones){ %>
							                      <div class="valoraciones">
							                        <div class="valoraciones-nota">
							                          <%=puntuacion.getValoracion()%>
							                        </div>
							                        <div class="valoraciones-comentario">
							                           <a href="<%=CONTEXT%>/private/evento?action=<%=ActionNames.EVENT_DETAIL%>&<%=ParameterNames.ID%>=<%=puntuacion.getIdEvento()%>"><span><%=puntuacion.getNombreEvento() %></span><br></a>
							                          <span style="font-size: 20px;">
							                            <%=puntuacion.getComentario() %>
							                          </span>
							                        </div>
							                      </div>
						               		 <%} %>
				                       
				                     </div>    
				                     
				                  </div>
				                </main>
			
			              </div>
			            </div>
			          </div>
			        </div>
			      </div>
		      </section>
      
    </header>


    
        
<script >$(document).ready(initOcultarFollow());</script>
<script >$(document).ready(initMostrarFollowers());</script>
<script >$(document).ready(initMostrarFollowing());</script>
<script >$(document).ready(buscarFollowersAjax());</script>
<script >$(document).ready(buscarFollowingAjax());</script>

    
<%@include file="/common/footer.jsp"%>
