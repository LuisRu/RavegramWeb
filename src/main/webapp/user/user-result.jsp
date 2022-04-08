<%@include file="/common/header.jsp"%>



<%@include file="/common/searcher.jsp"%>
      
<%
	
	List<UsuarioDTO> usuarios = (List<UsuarioDTO>) request.getAttribute(AttributeNames.USERS);
	for (UsuarioDTO u : usuarios) {
%>
      
             
     <!--POST-->
    <section class="main">
      <div class="wrapper">
        <div class="left-col">
          <div class="post">
            <div class="info-usuarios">
              <div>
                <a href="<%=CONTEXT%>/private/usuario?action=<%=ActionNames.USER_DETAIL%>&<%=ParameterNames.ID%>=<%=u.getId()%>"><h3><%=u.getUserName()%></h3></a>
              </div>
            </div>
            <img src="<%=CONTEXT%>/css/images/profile.jpg" class="post-image" />
            <div class="post-content">
              <div class="reaction-wrapper">
                <i class="icon fas fa-calendar"><%=u.getFechaNacimiento()%></i>
                <i class="icon fas fa-location-arrow"></i>
              </div>
              <p class="description">
                <span>
                  <%=u.getBiografia()%>
                </span> 
              </p>
            </div>
          </div>
        </div>
      </div>
    </section>
  
  
   <% } %>
  

<%@include file="/common/footer.jsp"%>