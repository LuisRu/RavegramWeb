<%@page import="com.luis.ravegram.web.util.ParametersUtil"%>
<%@include file="/common/header.jsp"%>


    <div class="container">
      <div class="main-container">
        <div class="main-content">
          <div class="form-container">
            <div class="form-content box">
              <div class="logo">
                <img src="assets/images/logo.png" alt="" class="logo-img" />
              </div>  
              <form action="<%=CONTEXT%>/<%=ControllerPaths.USER%>" method="post" class="login-form" id="login-form">
              
              <%@include file="/common/errors.jsp"%>
              
              <input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=ActionNames.USER_LOGIN%>"/>
                <p class="text-center alert-danger" id="error_message"></p>
                <div class="form-group">
                  <div class="login-input">                  
                    <input type="text" name="<%=ParameterNames.EMAIL %>" placeholder="Type your email..." required value="<%=ParametersUtil.print(request.getParameter(ParameterNames.EMAIL))%>"/>
                  </div>
                </div>
                <div class="form-group">
                  <div class="login-input">
                    <input type="password" name="<%=ParameterNames.PASSWORD %>" id="password" placeholder="Type your password..." required/>
                  </div>
                </div>
                <input type="checkbox" name="keep-authenticated" value="yes" checked/>Mantener sesion iniciada
                <div class="btn-group">
                  <button class="login-btn" id="login_btn" type="submit">
                    Log In
                  </button>
                </div>
              </form>
              <div class="or">
                <hr />
                <span>OR</span>
                <hr />
              </div>
              <div class="goto">
                <p>Don't have an account? <a href="<%=CONTEXT%>/<%=ViewPaths.USER_CREATE%>">Sign Up</a></p>
              </div>
            </div>
          </div>
        </div>
      </div>
     </div>

<%@include file="/common/footer.jsp"%>