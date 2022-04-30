<%@include file="/common/header.jsp"%>
   <div class="container">
      <div class="main-container">
        <div class="main-content">
          
          <div class="form-container">
            <div class="form-content box">
              <div class="logo">
                <img src="assets/images/logo.png" alt="" class="logo-img" />
              </div>
             
             
            <!-- INICIO FOMULARIO REGISTRO -->
              <form action="<%=CONTEXT%>/usuario" method="post" class="login-form" id="signup-form" autocomplete="off">
              
   
              
              <input type="hidden" name="action" value="<%=ActionNames.USER_CREATE%>"/>
                <p class="text-center alert-danger" id="error_message"></p>
                
                <!-- USER NAME-->
                   		<%
                  			parameterError = errors.getParameterError(ParameterNames.USER_NAME);
                  			if (parameterError!=null) {
                  		%>
                  			<label style="margin-left: 39px; color: red"><%=parameterError%></label>
	                  	<%
	                  		}
	                  	%>
                <div class="form-group">
                  <div class="login-input">
                    <input type="text" name="<%=ParameterNames.USER_NAME%>" placeholder="UserName" required value="<%=ParametersUtil.print(request.getParameter(ParameterNames.USER_NAME))%>"/>
                  </div>
                </div>

				<!-- EMAIL-->
						<%
                  			parameterError = errors.getParameterError(ParameterNames.EMAIL);
                  			if (parameterError!=null) {
                  		%>
                  			<label style="margin-left: 39px; color: red"><%=parameterError%></label>
	                  	<%
	                  		}
	                  	%>
                <div class="form-group">
                  <div class="login-input">
                    <input type="text" name="<%=ParameterNames.EMAIL%>" placeholder="Email" value="<%=ParametersUtil.print(request.getParameter(ParameterNames.EMAIL))%>" required/>
                  </div>
                </div>


				<!-- PASSWORD -->
						 <%
                  			parameterError = errors.getParameterError(ParameterNames.PASSWORD);
                  			if (parameterError!=null) {
                  		%>
                  			<label style="margin-left: 39px; color: red"><%=parameterError%></label>
	                  	<%
	                  		}
	                  	%>
                <div class="form-group">
                  <div class="login-input">
                    <input type="password" name="<%=ParameterNames.PASSWORD%>" placeholder="Contraseña" required value="<%=ParametersUtil.print(request.getParameter(ParameterNames.PASSWORD))%>"/>
                  </div>
                </div>


				<!-- FECHA -->
				       <%
                  			parameterError = errors.getParameterError(ParameterNames.FECHA_NACIMIENTO);
                  			if (parameterError!=null) {
                  		%>
                  			<label style="margin-left: 39px; color: red"><%=parameterError%></label>
	                  	<%
	                  		}
	                  	%>
                <div class="form-group">
                  <div class="login-input">
                    <input type="date" name="<%=ParameterNames.FECHA_NACIMIENTO%>" value="<%=ParametersUtil.print(request.getParameter(ParameterNames.FECHA_NACIMIENTO))%>" required />
                  </div>
                </div>

				<!-- SEXO -->
				  		<%
                  			parameterError = errors.getParameterError(ParameterNames.SEXO);
                  			if (parameterError!=null) {
                  		%>
                  			<label style="margin-left: 39px; color: red"><%=parameterError%></label>
	                  	<%
	                  		}
	                  	%>
                    <select style="margin-left: 41px; width: 247px; height: 30px; text-align: center;" name="<%=ParameterNames.SEXO%>" placeholder="Sexo" required>
                    	<option value="H">Hombre</option>
                    	<option value="M">Mujer</option>
                    </select>
             

				<!-- TELEFONO -->
				    <%
                  		parameterError = errors.getParameterError(ParameterNames.TELEFONO);
                  		if (parameterError!=null) {
                  	%>
                  		<label style="margin-left: 39px; color: red"><%=parameterError%></label>
                  	<%
                  		}
                  	%>
                <div class="form-group">
                  <div class="login-input">
                    <input type="text" name="<%=ParameterNames.TELEFONO%>" placeholder="Telefono" value="<%=ParametersUtil.print(request.getParameter(ParameterNames.TELEFONO))%>" required />
                  </div>
                </div>

                <!-- BIOGRAFIA -->
                  <%
                  		parameterError = errors.getParameterError(ParameterNames.BIOGRAFIA);
                  		if (parameterError!=null) {
                  	%>
                  		<label style="margin-left: 39px; color: red"><%=parameterError%></label>
                  	<%
                  		}
                  	%>
                <div class="form-group">
                  <div class="login-input">
                    <textarea name="<%=ParameterNames.BIOGRAFIA%>" style="height: 79px; font-size: 14px;height: 100px;" placeholder="Biografia" class="form-control" cols="30" rows="1" ><%=ParametersUtil.print(request.getParameter(ParameterNames.BIOGRAFIA))%></textarea>
                  </div>
                </div>
                <div class="btn-group">
                  <button class="login-btn" style="margin-top: 40px;" id="signup_btn" type="submit">
                    Sign Up
                  </button>
                  </form>
                  <!-- FIN FOMULARIO REGISTRO -->
                </div>
              <div class="or">
                <hr />
                <span>OR</span>
                <hr />
              </div>
              <div class="goto">
                <p>Already have an account? <a href="<%=CONTEXT%>/<%=ViewPaths.USER_LOGIN%>">Login</a></p>
              </div>
              </div>
            </div>
          </div>
        </div>
      </div>

<%@include file="/common/footer.jsp"%>