
<nav class="navbar">
      <div class="nav-wrapper">
       <a href="<%=CONTEXT%>"><img class="brand-img" src="<%=CONTEXT%>/css/images/logo.png" /></a>
        <div class="nav-items-user">
          <input type="text" class="search-box" placeholder="Buscar" id="buscador" onkeyup="buscarUsuariosAjax()">
         	<a href="<%=CONTEXT%>/<%=ViewPaths.USER_LOGIN%>"><button class="inicio-sesion">Entra</button></a>    
         	<a href="<%=CONTEXT%>/<%=ViewPaths.USER_CREATE%>"><button class="registrarte">Registrarte</button></a>
      	</div>
      	</div>      	
        </div>
      </div>
    </nav>