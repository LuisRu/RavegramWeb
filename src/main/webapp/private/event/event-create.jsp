<%@include file="/common/header.jsp"%>



<script>




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
	            var selectedId = <%=ParametersUtil.print(request.getParameter(ParameterNames.TIPO_TEMATICA))%>
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
	          var selectedId = <%=ParametersUtil.print(request.getParameter(ParameterNames.TIPO_MUSICA))%>
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
	          var selectedId = <%=ParametersUtil.print(request.getParameter(ParameterNames.TIPO_ESTABLECIMIENTO))%> 
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
	      
	     



</script>







 <form action="<%=CONTEXT%>/private/evento" method="post">
	<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=ActionNames.EVENT_CREATE%>"/>
	            
     <section class="main-detail">
      <div class="wrapper">
        <div class="left-col">
          <div class="post">
            <div class="info">
              <div class="user">
                <div class="profile-pic">
                  
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
			                            <div class="tab" style="height: 505px">
			                             <div class="informacion-tab" style="height: 100%">
		
					 							 <!-- FECHA HORA -->
			                                    <div  style="margin-bottom: 14px;" class="informacion-tab-dentro">
			                                   		 <%
							                  			parameterError = errors.getParameterError(ParameterNames.FECHA_HORA);
							                  			if (parameterError!=null) {
								                  		%>
								                  				<span style="font-size:17px"><%=parameterError%></span>
									                  	<%
									                  		}
									                  	%>
			                                        <i class="icon fas fa-calendar" style="font-size: 40px; margin-top: 8px;">
			                                          <input style="text-align: center;" type="date" class="form-control" name="<%=ParameterNames.FECHA_HORA%>" value="<%=ParametersUtil.print(request.getParameter(ParameterNames.FECHA_HORA))%>"/>
			                                        </i>
			                                    </div>	
			                                    
		                                 		<!-- TIPO TEMATICA -->
			                                    <div class="informacion-tab-dentro">
			                                    		<%
							                  			parameterError = errors.getParameterError(ParameterNames.TIPO_TEMATICA);
							                  			if (parameterError!=null) {
								                  		%>
								                  				<span style="font-size:17px"><%=parameterError%></span>
									                  	<%
									                  		}
									                  	%>
			                                      <i class="icon fas fa-mask" style="font-size: 40px; margin-top: 8px;"><br>
			                                          <select style="text-align: center;" class="form-control" id="tipo-tematica-id"  name="<%=ParameterNames.TIPO_TEMATICA%>">
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
								                  				<span style="font-size:17px" ><%=parameterError%></span>
									                  	<%
									                  		}
									                  	%>
			                                      <i class="icon fas fa-hotel" style="font-size: 40px; margin-top: 8px;"><br>
			                                        <select style="text-align: center;" class="form-control" id="tipo-establecimiento-id"  name="<%=ParameterNames.TIPO_ESTABLECIMIENTO%>" >
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
								                  				<span style="font-size:17px"><%=parameterError%></span>
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
			                                  			<%
							                  			parameterError = errors.getParameterError(ParameterNames.ES_PRIVADO);
							                  			if (parameterError!=null) {
								                  		%>
								                  				<span style="font-size:17px"><%=parameterError%></span>
									                  	<%
									                  		}
									                  	%>
			                                    <i class="icon fas fa-unlock" style="font-size: 40px; margin-top: 8px;"><br>
			                                    <select style="text-align: center;" class="form-control" id="tipo-establecimiento-id"  name="<%=ParameterNames.ES_PRIVADO%>">
			                                      <% 
			                                      	String esPrivadoStr = (String) request.getParameter(ParameterNames.ES_PRIVADO);
			                                      	Boolean esPrivado = StringUtils.isEmpty(esPrivadoStr) || "true".equalsIgnoreCase(esPrivadoStr); 
			                                      %>				                                    
			                                      <option value="true" <%=esPrivado?"selected":""%>>Privado</option>
			                                      <option value="false" <%=esPrivado?"":"selected"%>>Publico</option>
			                                    </select>
			                                    <!-- 
			                                    <input type="checkbox" name="<%=ParameterNames.ES_PRIVADO%>" <%=esPrivado?"checked":""%> value="privado">Privado</>
			                                     -->
			                                    </i>
			                                  </div>
			                                  
											 <!--CALLE -->
			                                  <div class="informacion-tab-dentro">
			                                  			<%
							                  			parameterError = errors.getParameterError(ParameterNames.CALLE);
							                  			if (parameterError!=null) {
								                  		%>
								                  				<span style="font-size:17px"><%=parameterError%></span>
									                  	<%
									                  		}
									                  	%>
			                                    <i class="icon fas fa-road" style="font-size: 40px; margin-top: 8px;">
			                                      <input style="text-align: center;"  type="text" class="form-control" name="<%=ParameterNames.CALLE%>" value="<%=ParametersUtil.print(request.getParameter(ParameterNames.CALLE))%>" placeholder="Direccion"/>
			                                    </i>
			                                  </div>
			                                  
											<!--NUM ASISTENTES-->
			                                  <div class="informacion-tab-dentro">
			                                  			<%
							                  			parameterError = errors.getParameterError(ParameterNames.NUM_ASISENTES);
							                  			if (parameterError!=null) {
								                  		%>
								                  				<span style="font-size:17px"><%=parameterError%></span>
									                  	<%
									                  		}
									                  	%>
			                                    <i class="icon fas fa-users" style="font-size: 40px; margin-top: 8px;">
			                                      <input style="text-align: center;" type="text" class="form-control" name="<%=ParameterNames.NUM_ASISENTES%>" value="<%=ParametersUtil.print(request.getParameter(ParameterNames.NUM_ASISENTES))%>" placeholder="Numero asistentes"/>
			                                    </i>
			                                  </div>
			                                  
											 <!--DISTANCIA-->
			                                  <div class="informacion-tab-dentro">
			                                  			<%
							                  			parameterError = errors.getParameterError(ParameterNames.LATITUD);
							                  			if (parameterError!=null) {
								                  		%>
								                  				<span style="font-size:17px"><%=parameterError%></span>
									                  	<%
									                  		}
									                  	%>
									                  	<%
							                  			parameterError = errors.getParameterError(ParameterNames.LONGITUD);
							                  			if (parameterError!=null) {
								                  		%>
								                  				<span style="font-size:17px"><%=parameterError%></span>
									                  	<%
									                  		}
									                  	%>
			                                    <i class="icon fas fa-location-arrow" style="font-size: 40px; margin-top: -8px;">
			                                    <input style="text-align: center;" type="text" class="form-control" name="<%=ParameterNames.LATITUD%>" value="<%=ParametersUtil.print(request.getParameter(ParameterNames.LATITUD))%>" placeholder="Latitud"/>
			                                    <input style="text-align: center;" type="text" class="form-control" name="<%=ParameterNames.LONGITUD%>" value="<%=ParametersUtil.print(request.getParameter(ParameterNames.LONGITUD))%>" placeholder="Longitud"/>
			                                    </i>
			                                  </div>
			                                  
			                                  <!-- TITULO -->
			                                  <input style="width: 100%; text-align: center; border: 1px solid; margin-top: 52px" name="<%=ParameterNames.NOMBRE_EVENTO%>" value="<%=ParametersUtil.print(request.getParameter(ParameterNames.NOMBRE_EVENTO))%>" placeholder="Titulo evento">
			                                  			<%
							                  			parameterError = errors.getParameterError(ParameterNames.NOMBRE_EVENTO);
							                  			if (parameterError!=null) {
								                  		%>
								                  				<span><%=parameterError%></span>
									                  	<%
									                  		}
									                  	%>
			                                  <!-- DESCRIPCION -->
			                                  <textarea style="border: 1px solid; text-align: center;" name="<%=ParameterNames.DESCRIPCION%>" placeholder="Descripcion" class="form-control" cols="30" rows="5"><%=ParametersUtil.print(request.getParameter(ParameterNames.DESCRIPCION))%></textarea>
			                                  			<%
							                  			parameterError = errors.getParameterError(ParameterNames.DESCRIPCION);
							                  			if (parameterError!=null) {
								                  		%>
								                  				<span><%=parameterError%></span>
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
	            			  <input type="submit" value="CREAR"  name="update_profile_btn" id="update_profile_btn" class="update-profile-btn" />
	            		</div>
	            		
	            		</form>
	           	
              </div>
            </div>
          </div>
        </div>
      </div>        
    </section>
  
    
    
    
<script >$(document).ready(buscarFollowersAjax());</script>
<script >$(document).ready(initMostrarAnadir());</script>
 
<script >$(document).ready(buscarTipoTematicaAjax());</script>
<script >$(document).ready(buscarTipoMusicaAjax());</script>
<script >$(document).ready(buscarTipoEstablecimientoAjax());</script>




<%@include file="/common/footer.jsp"%>
