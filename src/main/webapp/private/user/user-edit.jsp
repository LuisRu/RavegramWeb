<%@include file="/common/header.jsp"%>    
     <section class="main">
      <div class="wrapper">
        <div class="left-col">
          <h3>Actualizar perfil</h3>

          <form action="<%=CONTEXT%>/private/usuario" method="post">
	            <input type="hidden" name="action" value="<%=ActionNames.USER_UPDATE%>"/>
	            
	            <!-- SUBIR FOTO
	 		    <div class="mb-3">
	              <img src="<%=CONTEXT%>/css/images/profile.jpg" class="edit-profile-image" alt=""/>
	              <input type="file" name="image" class="form-control" />
	            </div> 
	            -->
	            
	            <!-- USERNAME-->
	            <div class="mb-3">
	              <label for="email" class="form-label">UserName</label>
	              <input type="text" class="form-control" name="<%=ParameterNames.USER_NAME %>" id="username" value="<%=ParametersUtil.print(usuario.getUserName())%>"/>
	                 <%
                  		parameterError = errors.getParameterError(ParameterNames.USER_NAME);
                  		if (parameterError!=null) {
                  		%>
                  			<p><%=parameterError%></p>
                  	<%
                  		}
                  	%>
	            </div>
	            
	            <!-- CONTRASEÑA-->
	            <div class="mb-3">
	              <label for="username" class="form-label">Contraseña</label>
	              <input type="password" class="form-control" name="<%=ParameterNames.PASSWORD %>" id="password" />
	                 <%
                  		parameterError = errors.getParameterError(ParameterNames.PASSWORD);
                  		if (parameterError!=null) {
                  		%>
                  			<p><%=parameterError%></p>
                  	<%
                  		}
                  	%>
	            </div>
	            
	            <!-- FECHA NACIMIENTO -->
	            <div class="mb-3">
	              <label for="fechanacimiento" class="form-label">Fecha Nacimiento</label>
	              <input type="date" name="<%=ParameterNames.FECHA_NACIMIENTO%>" value="<%=ParametersUtil.print(usuario.getFechaNacimiento())%>" required />
                      <%
                  		parameterError = errors.getParameterError(ParameterNames.FECHA_NACIMIENTO);
                  		if (parameterError!=null) {
                  		%>
                  			<p><%=parameterError%></p>
                  	<%
                  		}
                  	%>
	             </div>
	             
	             <!-- SEXO -->
	             <div class="mb-3">
	                <label for="sexo" class="form-label">Sexo</label>
	                 <% 
			           String sexoStr = usuario.getSexo().toString();
			           Boolean esHombre = StringUtils.isEmpty(sexoStr) || "H".equalsIgnoreCase(sexoStr);  
		             %>				         
	                <select name="<%=ParameterNames.SEXO%>" class="form-control" id="sexo">
                          <option value="H" <%=esHombre?"selected":""%>>Hombre</option>
                          <option value="M" <%=esHombre?"":"selected"%>>Mujer</option>
                    </select>
                     <%
                  		parameterError = errors.getParameterError(ParameterNames.SEXO);
                  		if (parameterError!=null) {
                  		%>
                  			<p><%=parameterError%></p>
                  	<%
                  		}
                  	%>
	            </div>
	            
	            <!-- BIOGRAFIA-->
	            <div class="mb-3">
	              <label for="bio" class="form-label">Biografia</label>
	              <textarea name="<%=ParameterNames.BIOGRAFIA%>" id="biografia" class="form-control" cols="30" rows="3" ><%=ParametersUtil.print(usuario.getBiografia())%></textarea>
	               <%
                  		parameterError = errors.getParameterError(ParameterNames.BIOGRAFIA);
                  		if (parameterError!=null) {
                  		%>
                  			<p><%=parameterError%></p>
                  	<%
                  		}
                  	%>
	            </div>
	           
	            <div class="mb-3">
	              <input type="submit" value="UPDATE"  name="update_profile_btn" id="update_profile_btn" class="update-profile-btn" />
	            </div>
	            
	            <div class="mb-3">
	              <a href="<%=CONTEXT%>/private/usuario?action=<%=ActionNames.USER_DELETE%>"><input value="BORRAR CUENTA"  name="update_profile_btn" id="update_profile_btn" style="background-color: red;" class="update-profile-btn" /></a>
	            </div>
          </form>
        </div>
      </div>
    </section>

<%@include file="/common/footer.jsp"%>
