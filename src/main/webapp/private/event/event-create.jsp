<%@include file="/common/header.jsp"%>
<%@ page import="java.util.List, com.luis.ravegram.model.UsuarioDTO" %>

 <section class="main">
      <div class="wrapper">
        <div class="left-col">
          <h3>Crear tu evento</h3>

         	<form action="<%=CONTEXT%>/evento/private" method="post" class="login-form" id="signup-form">
              
              <%@include file="/common/errors.jsp"%>
              
              <input type="hidden" name="action" value="<%=ActionNames.EVENT_CREATE%>"/>
              
          	<!-- TITULO -->
            <div class="mb-3">
              <label for="nombreEvento" class="form-label">Titulo</label>
              		<%
                  		parameterError = errors.getParameterError(ParameterNames.NOMBRE_EVENTO);
                  		if (parameterError!=null) {
                  	%>
                  		<p><i><%=parameterError%></i></p>
                  	<%
                  		}
                  	%>
              <input type="text" class="form-control" name="<%=ParameterNames.NOMBRE_EVENTO%>" id=""/>
            </div>
            
            
            <!-- DESCRIPCION -->
            <div class="mb-3">
              <label for="descripcion" class="form-label">Descripcion</label>
              		<%
                  		parameterError = errors.getParameterError(ParameterNames.DESCRIPCION);
                  		if (parameterError!=null) {
                  	%>
                  		<p><i><%=parameterError%></i></p>
                  	<%
                  		}
                  	%>
              <input type="text" class="form-control" name="<%=ParameterNames.DESCRIPCION%>" id=""/>
            </div>
            
            <!-- FECHA HORA-->
            <div class="mb-3">
              <label for="username" class="form-label">Fecha Hora</label>
              		<%
                  		parameterError = errors.getParameterError(ParameterNames.FECHA_HORA);
                  		if (parameterError!=null) {
                  	%>
                  		<p><i><%=parameterError%></i></p>
                  	<%
                  		}
                  	%>
              <input type="date" class="form-control" name="<%=ParameterNames.FECHA_HORA%>" id="" placeholder="fecha hora" />
              
             <!-- NUM ASISTES --> 
             <div class="mb-3">
               <label for="username" class="form-label">Num asistentes</label>
               		<%
                  		parameterError = errors.getParameterError(ParameterNames.NUM_ASISENTES);
                  		if (parameterError!=null) {
                  	%>
                  		<p><i><%=parameterError%></i></p>
                  	<%
                  		}
                  	%>
               <input type="text" class="form-control" name="<%=ParameterNames.NUM_ASISENTES%>" id="" placeholder="NumAsistentes" />
            </div>
            
            <!-- EDAD DESDE-->
            <div class="mb-3">
              <label for="username" class="form-label">Edad desde</label>
              		<%
                  		parameterError = errors.getParameterError(ParameterNames.EDAD_MIN);
                  		if (parameterError!=null) {
                  	%>
                  		<p><i><%=parameterError%></i></p>
                  	<%
                  		}
                  	%>
              <input type="text" class="form-control" name="<%=ParameterNames.EDAD_MIN%>" id="" placeholder="EdadDesde"/>
          	</div>
          	
          	<!-- EDAD HASTA-->
         	 <div class="mb-3">
	            <label for="username" class="form-label">Edad hasta</label>
	           		<%
                  		parameterError = errors.getParameterError(ParameterNames.EDAD_MAX);
                  		if (parameterError!=null) {
                  	%>
                  		<p><i><%=parameterError%></i></p>
                  	<%
                  		}
                  	%>
	            <input type="text" class="form-control" name="<%=ParameterNames.EDAD_MAX%>" id="" placeholder="EdadHasta"/>
        	</div>
        	
        	<!-- PUBLIC/PRIVADO -->
        	<div class="mb-3">
	          <label for="username" class="form-label">Publico o privado</label>
	         	 	<%
                  		parameterError = errors.getParameterError(ParameterNames.PUBLIC_PRIV);
                  		if (parameterError!=null) {
                  	%>
                  		<p><i><%=parameterError%></i></p>
                  	<%
                  		}
                  	%>
	          <input type="text" class="form-control" name="<%=ParameterNames.PUBLIC_PRIV%>" id=""/>
      		</div>
      		
      		<!-- CALLE-->
      		<div class="mb-3">
		        <label for="username" class="form-label">Calle</label>
		         	<%
                  		parameterError = errors.getParameterError(ParameterNames.CALLE);
                  		if (parameterError!=null) {
                  	%>
                  		<p><i><%=parameterError%></i></p>
                  	<%
                  		}
                  	%>
		        <input type="text" class="form-control" name="<%=ParameterNames.CALLE%>" id="" placeholder="calle"/>
    		</div>
    		
    		<!-- ZIP-->
   		 	<div class="mb-3">
		      <label for="username" class="form-label">ZIP</label>
		      		<%
                  		parameterError = errors.getParameterError(ParameterNames.ZIP);
                  		if (parameterError!=null) {
                  	%>
                  		<p><i><%=parameterError%></i></p>
                  	<%
                  		}
                  	%>
		      <input type="text" class="form-control" name="<%=ParameterNames.ZIP%>"  placeholder="ZIP"/>
   			</div>
   			
   			<!-- TIPO TEMATICA-->
   			<div class="mb-3">
		      <label for="username" class="form-label">Tipo tematica</label>
		      		<%
                  		parameterError = errors.getParameterError(ParameterNames.USER_NAME);
                  		if (parameterError!=null) {
                  	%>
                  		<p><i><%=parameterError%></i></p>
                  	<%
                  		}
                  	%>
		      <input type="text" class="form-control" name="<%=ParameterNames.TIPO_TEMATICA%>"  placeholder="tipo tematica"/>
    		</div>
    		
    		<!-- TIPO ESTABLECIMIENTO-->
    		<div class="mb-3">
			      <label for="username" class="form-label">Tipo establecimiento</label>
			      	<%
                  		parameterError = errors.getParameterError(ParameterNames.TIPO_ESTABLECIMIENTO);
                  		if (parameterError!=null) {
                  	%>
                  		<p><i><%=parameterError%></i></p>
                  	<%
                  		}
                  	%>
			      <input type="text" class="form-control" name="<%=ParameterNames.TIPO_ESTABLECIMIENTO%>" id="username" placeholder="tipo establecimiento"/>
   			 </div>
   			 
   			 <!-- LOCALIDAD-->
    		<div class="mb-3">
		      <label for="username" class="form-label">Localidad</label>
		      		<%
                  		parameterError = errors.getParameterError(ParameterNames.LOCALIDAD);
                  		if (parameterError!=null) {
                  	%>
                  		<p><i><%=parameterError%></i></p>
                  	<%
                  		}
                  	%>
		      <input type="text" class="form-control" name="<%=ParameterNames.LOCALIDAD%>" id="username" placeholder="Localidad"/>
		    </div>
		    
		    <!-- ESTABLECIMIENTO-->
		    <div class="mb-3">
		      <label for="username" class="form-label">Establecimiento</label>
		      		<%
                  		parameterError = errors.getParameterError(ParameterNames.ESTABLECIMIENTO);
                  		if (parameterError!=null) {
                  	%>
                  		<p><i><%=parameterError%></i></p>
                  	<%
                  		}
                  	%>
		      <input type="text" class="form-control" name="<%=ParameterNames.ESTABLECIMIENTO%>" id="username" placeholder="Establecimiento"/>
		    </div>
		    
		    <!-- TIPO MUSICA-->
		    <div class="mb-3">
		      <label for="username" class="form-label">Tipo musica</label>
		      		<%
                  		parameterError = errors.getParameterError(ParameterNames.TIPO_MUSICA);
                  		if (parameterError!=null) {
                  	%>
                  		<p><i><%=parameterError%></i></p>
                  	<%
                  		}
                  	%>
		      <input type="text" class="form-control" name="<%=ParameterNames.TIPO_MUSICA%>"  placeholder="tipo musica"/>
   			</div>
            <div class="mb-3">
              <input name="update_profile_btn" id="update_profile_btn" class="update-profile-btn" value="CREATE" type="button"/>
            </div>
          </form>
        </div>
      </div>
    </section>

<%@include file="/common/footer.jsp"%>
