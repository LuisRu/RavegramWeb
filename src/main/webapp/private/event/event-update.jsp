<%@include file="/common/header.jsp"%>


	<%
	
	EventoDTO evento = (EventoDTO) request.getAttribute(AttributeNames.EVENT);
	
	List<UsuarioDTO> asistentes = (List<UsuarioDTO>) request.getAttribute(AttributeNames.ASISTENTES);
	List<UsuarioDTO> usuariosNoAceptados = (List<UsuarioDTO>) request.getAttribute(AttributeNames.USERS);
	
	String tipoTematica = request.getParameter(ParameterNames.TIPO_TEMATICA);
	if(tipoTematica==null||"".equalsIgnoreCase(tipoTematica)){
		tipoTematica=null;
	}
	String tipoMusica = request.getParameter(ParameterNames.TIPO_MUSICA);
	if(tipoMusica==null||"".equalsIgnoreCase(tipoMusica)){
		tipoMusica=null;
	}
	String tipoEstablecimiento = request.getParameter(ParameterNames.TIPO_ESTABLECIMIENTO);
	if(tipoEstablecimiento==null||"".equalsIgnoreCase(tipoEstablecimiento)){
		tipoEstablecimiento=null;
	}
	

	String idEvento = evento==null?request.getParameter(ParameterNames.ID):evento.getId().toString();
	
	%>



<script>

		function initMostrarAnadir(){
		    $('#anadir-usuarios-popup').click(function(){
		    	if ($('.anadir-usuarios').is(':hidden'))
		    		   $('.anadir-usuarios').show();
		    			
		    		else
		    		   $('.anadir-usuarios').hide();
		    	
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

		
		function buscarEstadosEventoAjax() {
		    var url = "<%=CONTEXT%>/solicitud-service";
		        $.ajax({
		           type: "GET",
		           url: url,
		       data: "action=event-all-states",
		       success: function(data) {
		    	$('#estado-evento').empty();
		        for (i = 0; i<data.length; i++) {
		        	$('#estado-evento').append('<option value="'+data[i].id+'">'+data[i].nombre+'</option>');
		        }
		      }
		    });
		        
		}

		

	      function buscarTipoTematicaAjax() {
	            var url = '/RavegramWeb/tipo-tematica-service';
	            var selectedId = <%=evento==null? tipoTematica:evento.getIdTipoTematica()%>;
	                $.ajax({
	                   type: "GET",
	                   url: url,
	               data: "action=tipo-tematica",
	               success: function(data) {
	                for (i = 0; i<data.length; i++) {
	                	if(data[i].id == selectedId){
	                		$('#tipo-tematica-id').append('<option value="'+data[i].id+'" selected>'+data[i].nombre+'</option>');	
	                	}else{
	                		$('#tipo-tematica-id').append('<option value="'+data[i].id+'">'+data[i].nombre+'</option>');
	                	}
	                	
	                }
	              }
	            });
	        }
	      
	      
	      function buscarTipoMusicaAjax() {
	          var url = '/RavegramWeb/tipo-musica-service';
	          var selectedId = <%=evento==null? tipoMusica:evento.getIdTipoMusica()%>;
	              $.ajax({
	                 type: "GET",
	                 url: url,
	             data: "action=tipo-musica",
	             success: function(data) {
	              for (i = 0; i<data.length; i++) {
	            	 
	            	  if(data[i].id == selectedId){
	            		  $('#tipo-musica-id').append('<option value="'+data[i].id+'" selected>'+data[i].nombre+'</option>');
	            	  }else{
	            		 $('#tipo-musica-id').append('<option value="'+data[i].id+'">'+data[i].nombre+'</option>');  
	            	  }
	              }
	            }
	          });
	      }
	      
	      function buscarTipoEstablecimientoAjax() {
	          var url = '/RavegramWeb/tipo-establecimiento-service';
	          var selectedId = <%=evento==null? tipoEstablecimiento:evento.getIdTipoEstablecimiento()%>;
	              $.ajax({
	                 type: "GET",
	                 url: url,
	             data: "action=tipo-establecimiento",
	             success: function(data) {
	              for (i = 0; i<data.length; i++) {
	            	  if(data[i].id == selectedId){
	            		  $('#tipo-establecimiento-id').append('<option value="'+data[i].id+'" selected>'+data[i].nombre+'</option>');
	            	  }else{
	            		  $('#tipo-establecimiento-id').append('<option value="'+data[i].id+'">'+data[i].nombre+'</option>');  
	            	  }
	              }
	            }
	          });
	      }

	      
	      function buscarFollowersNoAceptados() {
	          var url = "/RavegramWeb/usuario-service";
	              $.ajax({
	                 type: "GET",
	                 url: url,
	             data: "action=user-follower-not-accept&id-dos="+<%=usuario.getId()%>+"&id="+<%=idEvento%>,
	             success: function(data) {
	          	$('#usuarios').empty();
	              for (i = 0; i<data.length; i++) {
	              	$('#usuarios').append('<option selected value="'+data[i].id+'">'+data[i].userName+'</option>');
	              	 
	              }
	            }
	          });
	              
	      }
	      
	      function buscarAsistentesAjax() {
	          var url = "/RavegramWeb/usuario-service";
	              $.ajax({
	                 type: "GET",
	                 url: url,
	             data: "action=user-searcher-assistants&id="+<%=idEvento%>,
	             success: function(data) {
	          	$('#usuariosSeleccionados').empty();
	              for (i = 0; i<data.length; i++) {
	              	$('#usuariosSeleccionados').append('<option selected value="'+data[i].id+'">'+data[i].userName+'</option>');
	              	 
	              }
	            }
	          });
	              
	      }


</script>







 <form action="<%=CONTEXT%>/private/evento" method="post">
	<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=ActionNames.EVENT_UPDATE%>"/>
	<input type="hidden" name="<%=ParameterNames.ID%>" value="<%=evento==null?request.getParameter(ParameterNames.ID):evento.getId()%>"/>
	<input type="hidden" name="<%=ParameterNames.TIPO_ESTADO_EVENTO%>" value="<%=evento==null?request.getParameter(ParameterNames.TIPO_ESTADO_EVENTO):evento.getIdTipoEstadoEvento()%>"/>
	            
     <section class="main-detail">
      <div class="wrapper">
        <div class="left-col">
          <div class="post">
            <div class="info">
              <div class="user">
                <div class="profile-pic">
                  <img src="<%=CONTEXT%>/css/images/profile.jpg" alt="" />
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
		                            <div class="informacion-tab">
		                              			<!-- FECHA HORA -->
			                                    <div class="informacion-tab-dentro"
			                                     		<%
							                  			parameterError = errors.getParameterError(ParameterNames.FECHA_HORA);
							                  			if (parameterError!=null) {
								                  		%>
								                  				<span><%=parameterError%></span>
									                  	<%
									                  		}
									                  	%>>
			                                        <i class="icon fas fa-calendar" style="font-size: 40px; margin-top: 8px;">
			                                          <input type="date" class="form-control" name="<%=ParameterNames.FECHA_HORA%>" value="<%=evento==null?request.getParameter(ParameterNames.FECHA_HORA):ParametersUtil.print(evento.getFechaHora())%>"/>
			                                        </i>
			                                    </div>	
			                                    
		                                 		<!-- TIPO TEMATICA -->
			                                    <div class="informacion-tab-dentro">
			                                   			 <%
							                  			parameterError = errors.getParameterError(ParameterNames.TIPO_TEMATICA);
							                  			if (parameterError!=null) {
								                  		%>
								                  				<span><%=parameterError%></span>
									                  	<%
									                  		}
									                  	%>
			                                      <i class="icon fas fa-mask" style="font-size: 40px; margin-top: 8px;"><br>
			                                          <select style="text-align: center;" class="form-control" id="tipo-tematica-id"  name="<%=ParameterNames.TIPO_TEMATICA%>" >
			                                            <option value="">Tipo Tematica</option>
			                                          </select>
			                                      </i>
			                                    </div>
		
											  <!-- TIPO ESTABLECIMIENTO -->
			                                    <div class="informacion-tab-dentro">
			                                    		<%
							                  			parameterError = errors.getParameterError(ParameterNames.TIPO_ESTABLECIMIENTO);
							                  			if (parameterError!=null) {
								                  		%>
								                  				<span style="font-size: 16px"><%=parameterError%></span>
									                  	<%
									                  		}
									                  	%>
			                                      <i class="icon fas fa-hotel" style="font-size: 40px; margin-top: 8px;"><br>
			                                        <select style="text-align: center;" class="form-control" id="tipo-establecimiento-id"  name="<%=ParameterNames.TIPO_ESTABLECIMIENTO%>">
			                                          <option value="">Tipo Establecimiento</option>
			                                        </select>
			                                      </i>
			                                  </div>
					
											 <!--TIPO MUSICA -->
			                                  <div class="informacion-tab-dentro">
			                                 			 <%
							                  			parameterError = errors.getParameterError(ParameterNames.TIPO_MUSICA);
							                  			if (parameterError!=null) {
								                  		%>
								                  				<span style="font-size: 16px"><%=parameterError%></span>
									                  	<%
									                  		}
									                  	%>
			                                    <i class="icon fas fa-music" style="font-size: 40px; margin-top: 8px;"><br>
			                                        <select  style="text-align: center;" class="form-control" id="tipo-musica-id"  name="<%=ParameterNames.TIPO_MUSICA%>" >
			                                          <option value="">Tipo Musica</option>
			                                        </select>
			                                    </i>
			                                  </div>
					                    	             
		                                 		<!--PUBLIC O PRIVADO -->
			                                  <div class="informacion-tab-dentro">
			                                    <i class="icon fas fa-unlock" style="font-size: 40px; margin-top: 8px;"><br>
			                                    <select style="text-align: center;" class="form-control" id="tipo-establecimiento-id"  name="<%=ParameterNames.ES_PRIVADO%>">
			                                      <% 
			                                      	String esPrivadoStr =  evento==null?request.getParameter(ParameterNames.ES_PRIVADO):evento.getPublicoPrivado().toString();
			                                      	Boolean esPrivado = StringUtils.isEmpty(esPrivadoStr) || "true".equalsIgnoreCase(esPrivadoStr); 
			                                      %>				                                    
			                                      <option value="true" <%=esPrivado?"selected":""%>>Privado</option>
			                                      <option value="false" <%=esPrivado?"":"selected"%>>Publico</option>
			                                    </select>
			                                    <%
							                  			parameterError = errors.getParameterError(ParameterNames.ES_PRIVADO);
							                  			if (parameterError!=null) {
								                  		%>
								                  				<span style="font-size: 16px"><%=parameterError%></span>
									                  	<%
									                  		}
									                  	%> 
			                                    </i>
			                                  </div>
			                                  
											 <!--CALLE -->
			                                  <div class="informacion-tab-dentro">
			                                    <i class="icon fas fa-road" style="font-size: 40px; margin-top: 8px;">
			                                      <input style="text-align: center;" type="text" class="form-control" name="<%=ParameterNames.CALLE%>" value="<%=evento==null?request.getParameter(ParameterNames.CALLE):ParametersUtil.print(evento.getCalle())%>" placeholder="calle"/>
			                                      <%
							                  			parameterError = errors.getParameterError(ParameterNames.CALLE);
							                  			if (parameterError!=null) {
								                  		%>
								                  				<span style="font-size: 16px"><%=parameterError%></span>
									                  	<%
									                  		}
									                  	%>
			                                    </i>
			                                  </div>
			                                  
											<!--NUM ASISTENTES-->
			                                  <div class="informacion-tab-dentro">
			                                    <i class="icon fas fa-users" style="font-size: 40px; margin-top: 8px;">
			                                      <input style="text-align: center;" type="text" class="form-control" name="<%=ParameterNames.NUM_ASISENTES%>" value="<%=evento==null?request.getParameter(ParameterNames.NUM_ASISENTES):ParametersUtil.print(evento.getNumAsistentes())%>"/>
			                                      	 <%
							                  			parameterError = errors.getParameterError(ParameterNames.NUM_ASISENTES);
							                  			if (parameterError!=null) {
								                  		%>
								                  				<span style="font-size: 16px"><%=parameterError%></span>
									                  	<%
									                  		}
									                  	%>
			                                    </i>
			                                  </div>
			                                  
											 <!--DISTANCIA-->
			                                  <div class="informacion-tab-dentro">
			                                  			<%
							                  			parameterError = errors.getParameterError(ParameterNames.LATITUD);
							                  			if (parameterError!=null) {
								                  		%>
								                  				<span style="font-size: 16px"><%=parameterError%></span>
									                  	<%
									                  		}
									                  	%>
									                  	<%
							                  			parameterError = errors.getParameterError(ParameterNames.LONGITUD);
							                  			if (parameterError!=null) {
								                  		%>
								                  				<span style="font-size: 16px"><%=parameterError%></span>
									                  	<%
									                  		}
									                  	%>
			                                    <i class="icon fas fa-location-arrow" style="font-size: 40px; margin-top: -8px;">
			                                    <input style="text-align: center;" type="text" class="form-control" name="<%=ParameterNames.LATITUD%>" value="<%=evento==null?request.getParameter(ParameterNames.LATITUD):ParametersUtil.print(evento.getLatitud())%>" placeholder="latitud"/>
			                                    <input style="text-align: center;" type="text" class="form-control" name="<%=ParameterNames.LONGITUD%>" value="<%=evento==null?request.getParameter(ParameterNames.LONGITUD):ParametersUtil.print(evento.getLongitud())%>" placeholder="longitud"/>
			                                    </i>
			                                  </div>
			                                  
			                                  <!-- TITULO -->
			                                  <input style="width: 100%; text-align: center; border: 1px solid; margin-top: 80px" name="<%=ParameterNames.NOMBRE_EVENTO%>" value="<%=evento==null?request.getParameter(ParameterNames.NOMBRE_EVENTO):ParametersUtil.print(evento.getNombre())%>" placeholder="Titulo evento">
			                                  			<%
							                  			parameterError = errors.getParameterError(ParameterNames.NOMBRE_EVENTO);
							                  			if (parameterError!=null) {
								                  		%>
								                  				<span style="font-size: 16px"><%=parameterError%></span>
									                  	<%
									                  		}
									                  	%>
			                                  <!-- DESCRIPCION -->
			                                  <textarea style="border: 1px solid; text-align: center;" name="<%=ParameterNames.DESCRIPCION%>" placeholder="Descripcion" class="form-control" cols="30" rows="5"><%=evento==null?request.getParameter(ParameterNames.DESCRIPCION):ParametersUtil.print(evento.getDescripcion())%></textarea>
			                                		  <%
							                  			parameterError = errors.getParameterError(ParameterNames.DESCRIPCION);
							                  			if (parameterError!=null) {
								                  		%>
								                  				<span style="font-size: 16px"><%=parameterError%></span>
									                  	<%
									                  		}
									                  	%>
		                                  
		                                </div>
		                            </div>
		              
		          
		                         <!--  Asistentes -->
		                        <input type="radio" name="tabs2" id="tabtwo2">
		                        <label for="tabtwo2">Asistentes</label>
		                        <div class="tab">
		                            <div class="asistente-tab">
		                            
		                            <!-- ANADIR  ASISTENTES-->
		                           
		                               <div class="asistente-tab-dentro" id="anadir-usuarios-popup">
		                                    <div class="asistente-pic">
		                                      <img src="<%=CONTEXT%>/css/images/add-user.png" alt="" />
		                                    </div>
		                                    <div class="aisitente-name">
		                                      <span>AÑADIR</span>
		                                    </div>
		                               </div>
		                               
		                               <!-- POP UP OCULTO PARA ANADIR USUARIOS -->
		                               
		                               <div class="anadir-usuarios">
											<div>
												<select class="select" id="usuarios" multiple>
													
													
													
													
												</select>
											</div>
											<div>
											
												<select class="select" id="usuariosSeleccionados" name="<%=ParameterNames.IDS_ASISTENTES%>" multiple>
													
													
													
												</select>
											</div>
											
											
										</div>
		                           
								
		                       
		
		                            </div>
		                        </div>
		                      
		                         <!--  Valoraciones -->
		                        <input type="radio" name="tabs2" id="tabthree2">
		                        <label for="tabthree2">Valoraciones</label>
                        
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
  
    
    
    

<script >$(document).ready(initMostrarAnadir());</script>

 
 
<script >$(document).ready(buscarTipoTematicaAjax());</script>
<script >$(document).ready(buscarTipoMusicaAjax());</script>
<script >$(document).ready(buscarTipoEstablecimientoAjax());</script>
<script >$(document).ready(buscarAsistentesAjax());</script>
<script >$(document).ready(buscarFollowersNoAceptados());</script>



<%@include file="/common/footer.jsp"%>
