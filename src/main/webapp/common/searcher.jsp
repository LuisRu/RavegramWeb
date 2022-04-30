<script>


      function buscarTipoTematicaAjax() {
            var url = '/RavegramWeb/tipo-tematica-service';
            var selectId = <%=ParametersUtil.printNull(request.getParameter(ParameterNames.TIPO_TEMATICA))%>;
                $.ajax({
                   type: "GET",
                   url: url,
               data: "action=tipo-tematica",
               success: function(data) {
                for (i = 0; i<data.length; i++) {
                	if(selectId==data[i].id){
                		$('#tipo-tematica-id').append('<option selected value="'+data[i].id+'">'+data[i].nombre+'</option>');	
                	}else{
                		$('#tipo-tematica-id').append('<option  value="'+data[i].id+'">'+data[i].nombre+'</option>');
                	}
                	
                }
              }
            });
        }
      
      
      function buscarTipoMusicaAjax() {
          var url = '/RavegramWeb/tipo-musica-service';
          var selectId = <%=ParametersUtil.printNull(request.getParameter(ParameterNames.TIPO_MUSICA))%>;
              $.ajax({
                 type: "GET",
                 url: url,
             data: "action=tipo-musica",
             success: function(data) {
              for (i = 0; i<data.length; i++) {
            	  if(selectId==data[i].id){
            		  $('#tipo-musica-id').append('<option selected value="'+data[i].id+'">'+data[i].nombre+'</option>');	  
            	  }else{
            		  $('#tipo-musica-id').append('<option value="'+data[i].id+'">'+data[i].nombre+'</option>');
            	  }
              	
              }
            }
          });
      }
      
      function buscarTipoEstablecimientoAjax() {
          var url = '/RavegramWeb/tipo-establecimiento-service';
          var selectId = <%=ParametersUtil.printNull(request.getParameter(ParameterNames.TIPO_ESTABLECIMIENTO))%>;
              $.ajax({
                 type: "GET",
                 url: url,
             data: "action=tipo-establecimiento",
             success: function(data) {
              for (i = 0; i<data.length; i++) {
            	  if(selectId==data[i].id){
            		  $('#tipo-establecimiento-id').append('<option selected value="'+data[i].id+'">'+data[i].nombre+'</option>');	  
            	  }else{
            		  $('#tipo-establecimiento-id').append('<option  value="'+data[i].id+'">'+data[i].nombre+'</option>');
            	  }
              	
              }
            }
          });
      }
      
      
      

   
     
      
      </script>
   
   
   
   
   
   
   
   
   <!--FILTROS-->
<section class="main-filtros">
      <div class="wrapper">
        <div class="left-col">
          <div class="post-filtros">
            <div class="tabs">

              <!--  EVENTOS -->
              <input type="radio" name="tabs" id="tabone" checked="checked">
              <label for="tabone">Eventos</label>
              <div class="tab">
                <form action="<%=CONTEXT%>/private/evento" method="post" autocomplete="off">
                      <input type="hidden" name="action" value="<%=ActionNames.EVENT_SEARCH%>"/>
                      <!-- DISTANCIA -->
                        <input type="text" style="border-radius: 20px;width: 336px;text-align: center;height: 30px;" name="<%=ParameterNames.DISTANCIA%>" value="<%=ParametersUtil.print(request.getParameter(ParameterNames.DISTANCIA)) %>" placeholder="distancia"><br>
                        <!-- PRIVADO PUBLICO --> 
                        <% 
							String esPrivadoStr =  request.getParameter(ParameterNames.ES_PRIVADO);
							Boolean esPrivado = StringUtils.isEmpty(esPrivadoStr) || "true".equalsIgnoreCase(esPrivadoStr); 
						 %>
                        <select name="<%=ParameterNames.ES_PRIVADO%>"  style="border-radius: 20px;width: 336px;text-align: center;height: 30px;" id="select1">
						   <option value="true" <%=esPrivado?"selected":""%>>Privado</option>
						   <option value="false" <%=esPrivado?"":"selected"%>>Publico</option>
                        </select><br>
                        
                         <!-- TIPO ESTABLECIMIENTO -->
                        <select name="<%=ParameterNames.TIPO_ESTABLECIMIENTO%>" style="border-radius: 20px;width: 336px;text-align: center;height: 30px;" id="tipo-establecimiento-id">
                        	<option value="" selected>TIPO ESTABLECIMIENTO</option>
                        </select><br>
                        
                        <!-- TIPO TEMATICA -->
                        <select name="<%=ParameterNames.TIPO_TEMATICA%>" style="border-radius: 20px;width: 336px;text-align: center;height: 30px;" id="tipo-tematica-id">
                        	<option value="" selected >TIPO TEMATICA</option>
                        </select><br>
                        
                        <!-- TIPO MUSICA -->
                        <select name="<%=ParameterNames.TIPO_MUSICA%>" style="border-radius: 20px;width: 336px;text-align: center;height: 30px;" id="tipo-musica-id">
                        	<option value="" selected>TIPO MUSICA</option>
                        </select><br>
                        
                        <!-- order by -->
                        <select name="<%=ParameterNames.ORDER_BY%>" style="border-radius: 20px;width: 336px;text-align: center;height: 30px;" id="order-by">
                        	<option value="" selected>ORDERNAR POR</option>
                        	<option value="NOMBRE-ASC" >NOMBRE ASCENDETE</option>
                        	<option value="NOMBRE-DESC" >NOMBRE DESCENDENTE</option>
                        	<option value="FECHA-ASC" >FECHA ASC</option>
                        	<option value="FECHA-DESC" >FECHA DESC</option>
                        </select><br>
                        
                        
                        
                    <input type="submit" value="Buscar" style="border-radius: 20px;width: 160px;text-align: center;height: 30px;" name="Buscar" />
                  </form>
              </div>

               <!--  USUARIOS -->
              <input type="radio" name="tabs" id="tabtwo">
              <label for="tabtwo">Usuario</label>
              <div class="tab">
                <form action="<%=CONTEXT+ControllerPaths.PRIVATE_USER%>" method="post">
                    <input type="hidden" name="action" value="<%=ActionNames.USER_SEARCH%>"/>
                    	<!-- EDAD MAX -->
                        <input type="text" style="border-radius: 20px;width: 336px;text-align: center;height: 30px;" name="<%=ParameterNames.EDAD_MAX%>" placeholder="edadMax"><br>
                        <!-- EDAD MIN -->
                        <input type="text" style="border-radius: 20px;width: 336px;text-align: center;height: 30px;" name="<%=ParameterNames.EDAD_MIN%>" placeholder="edadMin"><br>
                    <input type="submit" style="border-radius: 20px;width: 160px;text-align: center;height: 30px;" value="Buscar" name="Buscar" />
                  </form>
              </div>
            
               <!--  ESTABLECIMIENTOS -->
              <input type="radio" name="tabs" id="tabthree">
              <label for="tabthree">Establecimientos</label>
              <div class="tab">
                <form action="<%=CONTEXT+ControllerPaths.PRIVATE_ESTABLECIMIENTO%>" method="post">
                    <input type="hidden" name="action" value="<%=ActionNames.ESTABLECIMIENTO_SEARCH%>"/>
                        <select style="border-radius: 20px;width: 336px;text-align: center;height: 30px;" name="<%=ParameterNames.ESTABLECIMIENTO%>" id="select2">
                       		<option value="5">Bar</option>
                       		<option value="7">Discoteca</option>
                       		<option value="6">Pub</option>
                        </select><br>
                    <!--  o un button type submit -->
                    <input type="submit" value="Buscar" style="border-radius: 20px;width: 160px;text-align: center;height: 30px;" name="Buscar" />
                  </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
    
    
    <script >$(document).ready(buscarTipoTematicaAjax());</script>
    <script >$(document).ready(buscarTipoMusicaAjax());</script>
    <script >$(document).ready(buscarTipoEstablecimientoAjax());</script>
