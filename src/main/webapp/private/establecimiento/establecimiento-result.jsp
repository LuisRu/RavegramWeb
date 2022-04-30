<%@include file="/common/header.jsp"%>



<%@include file="/common/searcher.jsp"%>


      
<%

	List<EstablecimientoDTO> establecimientos = (List<EstablecimientoDTO>) request.getAttribute(AttributeNames.ESTABLECIMIENTOS);
	for(EstablecimientoDTO establecimiento:  establecimientos){
%>
      
      

     <!--POST PROFILE-->
 	 <section class="main">
     	<div class="card">
        	<img src="<%=CONTEXT%>/css/images/profile.jpg" alt="Image">
	        <div class="details">
	            <h2><%=establecimiento.getNombre()%></h2>
	        </div>
	        <p style="font-size: 22px;" id="info">
	         	<i class="icon fas fa-road"><%=establecimiento.getCalle()%></i>
	         	<i class="icon fas fa-hotel"><%=establecimiento.getNombreTipoEstablecimiento()%></i>
	         	<i class="icon fas fa-users"><%=establecimiento.getAforo()%></i>
	         	<i class="icon fas fa-location-arrow"><%=establecimiento.getNombreLocalidad()%></i>
	        </p>

     	</div>  
    </section>
 
 <%} %>
 
 <div class="caja_paginacion">
								<ul class="paginacion">
								<!--  Paginador -->
								<%
									Integer currentPage = (Integer) request.getAttribute(AttributeNames.CURRENT_PAGE);
								
									Integer pagingFrom = (Integer) request.getAttribute(AttributeNames.PAGING_FROM);
									Integer pagingTo = (Integer) request.getAttribute(AttributeNames.PAGING_TO);
									
									Integer totalPages = (Integer) request.getAttribute(AttributeNames.TOTAL_PAGES);
									
									Map<String,String[]> parameters = new HashMap<String, String[]>(request.getParameterMap());								
									parameters.remove(ParameterNames.PAGE); // para que no arrastre el valor anterior
									
									// Ya viene terminada en &
									String baseURL = ParametersUtil.getURLPaginacion(request.getContextPath()+ControllerPaths.PRIVATE_ESTABLECIMIENTO, parameters);
	
									
									// Primera
									if (currentPage>1) {
										%> 
										<li><a href="<%=baseURL%>">Primera</a></li>
										<%
									}
	
									
									// Anterior
									if (currentPage>1) {
										%> 
										<li><a href="<%=baseURL+ParameterNames.PAGE+"="+(currentPage-1)%>">Anterior</a></li>
										<%
									}
									
									// Paginas antes de la actual
									for (int i = pagingFrom; i<currentPage; i++) {
											%> 
											<li>&nbsp;<a href="<%=baseURL+ParameterNames.PAGE+"="+i%>"><%=i%></a>&nbsp;</li>
											<% 
									}	
									
									// La actual
									%>&nbsp;<span class="paginacion_activa"><%=currentPage%></span>&nbsp;<%
									
									// Despues de la actual
									for (int i = currentPage+1; i<=pagingTo; i++) {
											%> 
											<li>&nbsp;<a href="<%=baseURL+ParameterNames.PAGE+"="+i%>"><%=i%></a>&nbsp;</li>
											<% 
									}
									
									// Siguiente
									if (currentPage<totalPages) {
										%>
											<li><a href="<%=baseURL+ParameterNames.PAGE+"="+(currentPage+1)%>">Siguiente</a></li>
										<%
									}
									
									
									// Última
									if (currentPage<totalPages) {
										%>
											<li><a href="<%=baseURL+ParameterNames.PAGE+"="+(totalPages)%>">Última</a></li>
										<%
								}
									
									%>
							</ul>
						</div>
 
 
 

<%@include file="/common/footer.jsp"%>