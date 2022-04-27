<%@include file="/common/header.jsp"%>


<%@include file="/common/searcher.jsp"%>
<%

 
	
	List<EventoDTO> eventos = (List<EventoDTO>) request.getAttribute(AttributeNames.EVENTS);
	for (EventoDTO e : eventos) {
%>

    <section class="main-detail">
      <div class="wrapper">
        <div class="left-col">
          <div class="post">
            <div class="info">
              <div class="user">
                <div class="profile-pic">
                  <img src="<%=CONTEXT%>/css/images/profile.jpg" alt="" />
                </div>
                <a href="<%=CONTEXT%>/usuario?action=<%=ActionNames.USER_DETAIL%>&<%=ParameterNames.ID%>=<%=e.getIdUsuario()%>"><p class="username"><%=e.getNombreUsuarioCreador() %></p></a>
              </div>
              <i class="fas fa-ellipsis-h options"></i>
            </div>
            <a href="<%=CONTEXT%>/private/evento?action=<%=ActionNames.EVENT_DETAIL%>&<%=ParameterNames.ID%>=<%=e.getId()%>"><img src="<%=CONTEXT%>/css/images/1.jpg" class="post-image" /></a>
            <div class="post-content">
              <div class="reaction-wrapper">
                <i class="icon fas fa-calendar"><%=e.getFechaHora() %></i>
                <i class="icon fas fa-hotel"></i>
                
                <% if (e.getPublicoPrivado()==true){ %> 
                	<i class="icon fas fa-unlock"></i>
                <%}else{%>
                	<i class="icon fas fa-lock"></i>
                <%} %>
                
                <i class="icon fas fa-music"><%=e.getTipoMusica() %></i>
                <i class="icon fas fa-location-arrow"><%=e.getDistanciaKm() %></i>
              </div>
              <p class="description">
                <span>
                  <%=e.getDescripcion() %>
                </span> 
              </p>
            </div>
          </div>
        </div>
      </div>
    </section>
    
    
  
    
     <div class="btns">
     
	    <a href="<%=CONTEXT%>/private/solicitud?action=<%=ActionNames.RECHAZA%>&<%=ParameterNames.ID%>=<%=e.getId()%>">
	    	<img src="<%=CONTEXT%>/css/images/nope.png">
	    </a>
	    
	    <img  class="refresh" src="<%=CONTEXT%>/css/images/refresh.png">
	    
	     <a href="<%=CONTEXT%>/private/solicitud?action=<%=ActionNames.SOLICITUD_EVENTO%>&<%=ParameterNames.ID%>=<%=e.getId()%>">
	    	<img src="<%=CONTEXT%>/css/images/like.png">
	    </a>
	    
  	</div>
    
    
  <% } %>

<%@include file="/common/footer.jsp"%>