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
              <form action="<%=CONTEXT%>/usuario" method="post" class="login-form" id="signup-form">
              
              <%@include file="/common/errors.jsp"%>
              
              <input type="hidden" name="action" value="<%=ActionNames.USER_CREATE%>"/>
                <p class="text-center alert-danger" id="error_message"></p>
                
                <!-- USER NAME-->
                <div class="form-group">
	                <%
                  		parameterError = errors.getParameterError(ParameterNames.USER_NAME);
                  		if (parameterError!=null) {
                  	%>
                  		<p><i><%=parameterError%></i></p>
                  	<%
                  		}
                  	%>
                  <div class="login-input">
                    <input type="text" name="<%=ParameterNames.USER_NAME%>" placeholder="UserName" required value="<%=ParametersUtil.print(request.getParameter(ParameterNames.USER_NAME))%>"/>
                  </div>
                </div>

				<!-- EMAIL-->
                <div class="form-group">
                	<%
                  		parameterError = errors.getParameterError(ParameterNames.EMAIL);
                  		if (parameterError!=null) {
                  	%>
                  		<p><i><%=parameterError%></i></p>
                  	<%
                  		}
                  	%>
                  <div class="login-input">
                    <input type="text" name="<%=ParameterNames.EMAIL%>" placeholder="Email" value="<%=ParametersUtil.print(request.getParameter(ParameterNames.EMAIL))%>" required/>
                  </div>
                </div>


				<!-- PASSWORD -->
                <div class="form-group">
             	   <%
                  		parameterError = errors.getParameterError(ParameterNames.PASSWORD);
                  		if (parameterError!=null) {
                  	%>
                  		<p><%=parameterError%></p>
                  	<%
                  		}
                  	%>
                  <div class="login-input">
                    <input type="text" name="<%=ParameterNames.PASSWORD%>" placeholder="Contraseņa" required value="<%=ParametersUtil.print(request.getParameter(ParameterNames.PASSWORD))%>"/>
                  </div>
                </div>


				<!-- FECHA -->
                <div class="form-group">
                   <%
                  		parameterError = errors.getParameterError(ParameterNames.FECHA_NACIMIENTO);
                  		if (parameterError!=null) {
                  	%>
                  		<p><%=parameterError%></p>
                  	<%
                  		}
                  	%>
                  <div class="login-input">
                    <input type="date" name="<%=ParameterNames.FECHA_NACIMIENTO%>" value="<%=ParametersUtil.print(request.getParameter(ParameterNames.FECHA_NACIMIENTO))%>" required />
                  </div>
                </div>

				<!-- SEXO -->
                <div class="form-group">
                	<%
                  		parameterError = errors.getParameterError(ParameterNames.SEXO);
                  		if (parameterError!=null) {
                  	%>
                  		<p><%=parameterError%></p>
                  	<%
                  		}
                  	%>
                  <div class="login-input">
                    <input type="text" name="<%=ParameterNames.SEXO%>" placeholder="Sexo" value="Poner valor" required/>
                  </div>
                </div>


				<!-- TELEFONO -->
                <div class="form-group">
                	<%
                  		parameterError = errors.getParameterError(ParameterNames.TELEFONO);
                  		if (parameterError!=null) {
                  	%>
                  		<p><%=parameterError%></p>
                  	<%
                  		}
                  	%>
                  <div class="login-input">
                    <input type="text" name="<%=ParameterNames.TELEFONO%>" placeholder="Telefono" value="<%=ParametersUtil.print(request.getParameter(ParameterNames.TELEFONO))%>" required />
                  </div>
                </div>

                <!-- BIOGRAFIA -->
                <div class="form-group">
                  	<%
                  		parameterError = errors.getParameterError(ParameterNames.BIOGRAFIA);
                  		if (parameterError!=null) {
                  	%>
                  		<p><%=parameterError%></p>
                  	<%
                  		}
                  	%>
                  <div class="login-input">
                    <textarea name="<%=ParameterNames.BIOGRAFIA%>" placeholder="Biografia" class="form-control" cols="30" rows="1"><%=ParametersUtil.print(request.getParameter(ParameterNames.BIOGRAFIA))%>"</textarea>
                  </div>
                </div>
                
                <div class="btn-group">
                  <button class="login-btn" id="signup_btn" type="submit">
                    Sign Up
                  </button>
                </div>
              </form>
              <!-- FIN FOMULARIO REGISTRO -->
              
              
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