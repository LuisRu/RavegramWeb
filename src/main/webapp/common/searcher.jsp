    <%@page import="com.luis.ravegram.web.util.ParametersUtil"%>
<%@page import="com.luis.ravegram.web.controller.util.ParameterNames"%>
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
                        <input type="text" name="<%=ParameterNames.DISTANCIA%>" value="<%=ParametersUtil.print(request.getParameter(ParameterNames.DISTANCIA)) %>" placeholder="distancia"><br> 
                        <select name="<%=ParameterNames.ES_PRIVADO%>" id="select1">
                          <option value="0">Publico o privado</option>
                          <option value="1">Publico</option>
                          <option value="2">Privado</option>
                        </select><br>
                        
                        
                         <!-- TIPO ESTABLECIMIENTO -->
                        <select name="<%=ParameterNames.TIPO_ESTABLECIMIENTO%>" id="tipo-establecimiento-id">
                        	<option value="" selected>TIPO ESTABLECIMIENTO</option>
                        </select><br>
                        
                        <!-- TIPO TEMATICA -->
                        <select name="<%=ParameterNames.TIPO_TEMATICA%>" id="tipo-tematica-id">
                        	<option value="" selected >TIPO TEMATICA</option>
                        </select><br>
                        
                        <!-- TIPO MUSICA -->
                        <select name="<%=ParameterNames.TIPO_MUSICA%>" id="tipo-musica-id">
                        	<option value="" selected>TIPO MUSICA</option>
                        </select><br>
                        
                    <input type="submit" value="Buscar" name="Buscar" />
                  </form>
              </div>

               <!--  USUARIOS -->
              <input type="radio" name="tabs" id="tabtwo">
              <label for="tabtwo">Usuario</label>
              <div class="tab">
                <form action="<%=CONTEXT%>/private/usuario" method="post">
                    <input type="hidden" name="action" value="<%=ActionNames.USER_SEARCH%>"/>
                        <input type="text" name="<%=ParameterNames.DISTANCIA%>" placeholder="distancia"><br>
                        <input type="text" name="<%=ParameterNames.EDAD_MAX%>" placeholder="edadMax"><br>
                        <input type="text" name="<%=ParameterNames.EDAD_MIN%>" placeholder="edadMin"><br>
                    <input type="submit" value="Buscar" name="Buscar" />
                  </form>
              </div>
            
               <!--  ESTABLECIMIENTOS -->
              <input type="radio" name="tabs" id="tabthree">
              <label for="tabthree">Establecimientos</label>
              <div class="tab">
                <form action="<%=CONTEXT%>/usuario" method="post">
                    <input type="hidden" name="action" value="search"/>
                        <input type="text" name="<%=ParameterNames.DISTANCIA%>" placeholder="distancia"><br>
                        <select name="<%=ParameterNames.ESTABLECIMIENTO%>" id="select2">
                       
                        </select><br>
                    <!--  o un button type submit -->
                    <input type="submit" value="Buscar" name="Buscar" />
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
