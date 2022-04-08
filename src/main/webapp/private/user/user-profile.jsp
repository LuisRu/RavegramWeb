<%@page import="org.apache.catalina.manager.DummyProxySession"%>
<%@include file="/common/header.jsp"%>




<script>

	function initMostrarFollowers(){
	    $('#followers').click(function(){
	    	if ($('.followers').is(':hidden'))
	    		   $('.followers').show();
	    		else
	    		   $('.followers').hide();
	    	
	    });
	}
    
    function initMostrarFollowing(){
        $('#following').click(function(){
        	if ($('.following').is(':hidden'))
        		   $('.following').show();
        		else
        		   $('.following').hide();
        	
        }); 
    }
    
    
    function buscarFollowersAjax() {
        var url = "/Ravegram/usuario-service";
            $.ajax({
               type: "GET",
               url: url,
           data: "action=user-search-follower&id="+<%=request.getParameter(ParameterNames.ID)%>,
           success: function(data) {
        	$('#div-followers').empty();
            for (i = 0; i<data.length; i++) {
            	$('#div-followers').append('<p class="nombre-usuarios">'+data[i].userName+'</p>');
            }
          }
        });
            
    }
    
    
    function buscarFollowingAjax() {
        var url = "/Ravegram/usuario-service";
            $.ajax({
               type: "GET",
               url: url,
           data: "action=user-search-following&id="+<%=request.getParameter(ParameterNames.ID)%>,
           success: function(data) {
        	$('#div-following').empty();
            for (i = 0; i<data.length; i++) {
            	$('#div-following').append('<p class="nombre-usuarios">'+data[i].userName+'</p>');
            }
          }
        });
            
    }

        
</script>


<%
 
//para diferenciar si es mi perfil o no segun la accion
	Boolean myProfile = ActionNames.USER_MY_PROFILE.equalsIgnoreCase((String) request.getAttribute(ParameterNames.ACTION));
	
	//
	Long idUsuario = (Long) request.getAttribute(AttributeNames.ID);
	//si es mi perfil saco mi perifl de la sesion si no de la request
	usuario = myProfile? (UsuarioDTO) SessionManager.get(request, AttributeNames.USER):(UsuarioDTO) request.getAttribute(AttributeNames.USER);	
	
	Set<Long> idsSeguidos = !myProfile? (Set<Long>) SessionManager.get(request,AttributeNames.FOLLOWING): null;
%>


 <div  id="followers-div" class="followers">
      <p style="font-size: 28px;  background-color: white; border-radius: 20px;">Followers</p>
      <div id="div-followers" class="dentro-followers"> 
        
      </div>
    </div>

    <div  id="following-div" class="following">
      <p style="font-size: 28px;  background-color: white; border-radius: 20px;">Following</p>
      <div id="div-following" class="dentro-following"> 
        
      </div>
    </div>





  <header class="profile-header">
      <div class="profile-container">
        <div class="profile">
          <div class="profile-image">
            <img src="<%=CONTEXT%>/css/images/profile.jpg" alt="" />
          </div>
          <div class="profile-user-settings">
            <h1 class="profile-user-name"><%=usuario.getUserName() %></h1>
            
            
            <!-- BOTON EDIT PROFILE -->
            
            <% if(myProfile){ %>
            	<a href="<%=CONTEXT%>/<%=ViewPaths.USER_EDIT%>"><button class="profile-btn profile-edit-btn" >Edit Profile</button></a>
            <% } %>
          
          
          
          </div>
          <div class="profile-stats">
            <ul>
              <li><span class="profile-stat-count"></span> </li>
              <li id="followers" ><span class="profile-stat-count"></span> FOLLOWERS</li>
              <li id="following"><span class="profile-stat-count"></span> FOLLOWING</li>
            </ul>
 
 			
			<%if(!myProfile){%>
				<%if(!idsSeguidos.contains(idUsuario)){ %>
				<!-- BOTON FOLLOW -->
			  	<a href="<%=CONTEXT%>/private/usuario?action=<%=ActionNames.USER_FOLLOW%>&<%=ParameterNames.ID%>=<%=idUsuario%>">
              		<button type="submit" class="follow-btn-user-profile"> FOLLOW </button>
            	 </a>
            	 
            	 <%}else{ %>
            	 <!-- BOTON UNFOLLOW -->
            	 <a href="<%=CONTEXT%>/private/usuario?action=<%=ActionNames.USER_UNFOLLOW%>&<%=ParameterNames.ID%>=<%=idUsuario%>">
              		<button type="submit" class="unfollow-btn-user-profile"> UNFOLLOW </button>
            	 </a>
            <%}
				}
			%>  
          </div>
          
          
          <!-- BIOGRAFIA -->
          <div class="profile-bio" style="text-align: center; width: 100%">
            <p style="text-align: center">
              <%=usuario.getBiografia() %>
            </p>
          </div>
          
          
        </div>
      </div>
    </header>

    <main>
      <div class="profile-container">
        <div class="tabsprofile">
          <div>Eventos disponibles</div>
          <div>Todos eventos</div>
          <div>Valoraciones</div>
        </div>
        <div class="gallery">
        
       </div>
      	</div>
    </main>
        

<script >$(document).ready(initMostrarFollowers());</script>
<script >$(document).ready(initMostrarFollowing());</script>
<script >$(document).ready(buscarFollowersAjax());</script>
<script >$(document).ready(buscarFollowingAjax());</script>

    
<%@include file="/common/footer.jsp"%>
