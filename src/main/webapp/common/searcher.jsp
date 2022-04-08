   <!--FILTROS-->
    <%@page import="com.luis.ravegram.web.controller.util.ParameterNames"%>
<section class="main-filtros">
      <div class="wrapper">
        <div class="left-col">
          <div class="post-filtros">
            <div class="tabs">

              <!--  EVENTOS -->
              <input type="radio" name="tabs" id="tabone" checked="checked">
              <label for="tabone">Eventos</label>
              <div class="tab">
                <form action="<%=CONTEXT%>/evento" method="post" autocomplete="off">
                      <input type="hidden" name="action" value="<%=ActionNames.EVENT_SEARCH%>"/>
                        <input type="text" class="search-box" placeholder="search.." /><br>
                        <input type="text" name="<%=ParameterNames.DISTANCIA%>" placeholder="distancia"><br>
                        <input type="text" name="<%=ParameterNames.EDAD_MAX%>" placeholder="edadMax"><br>
                        <input type="text" name="<%=ParameterNames.EDAD_MIN%>" placeholder="edadMin"><br>
                        <select name="<%=ParameterNames.PUBLIC_PRIV%>" id="select1">
                          <option value="0">Publico o privado</option>
                          <option value="1">Publico</option>
                          <option value="2">Privado</option>
                        </select><br>
                        <select name="<%=ParameterNames.TIPO_ESTABLECIMIENTO%>" id="select2">
                          <option value="0">Tipo establecimiento</option>
                          <option value="1">Piso</option>
                          <option value="2">Casa</option>
                          <option value="3">Casa con piscina</option>
                          <option value="4">Calle</option>
                          <option value="5">Bar</option>
                          <option value="6">Pub</option>
                          <option value="7">Discoteca</option>
                        </select><br>
                        <select name="<%=ParameterNames.TIPO_TEMATICA%>" id="select2">
                          <option value="0">Tipo tematica</option>
                          <option value="1">Unviersitaria</option>
                          <option value="2">Botellon</option>
                          <option value="3">Piscina</option>
                          <option value="4">Disfraces</option>
                          <option value="5">Rave</option>
                          <option value="6">Karaoke</option>
                        </select><br>
                        <select name="<%=ParameterNames.TIPO_MUSICA%>" id="select2">
                          <option value="0">Tipo musica</option>
                          <option value="1">Pop</option>
                          <option value="2">Rock</option>
                          <option value="3">Techno</option>
                          <option value="4">Trap</option>
                          <option value="5">Rap</option>
                          <option value="6">Soul</option>
                        </select><br>
                    <input type="submit" value="Buscar" name="Buscar" />
                  </form>
              </div>

               <!--  USUARIOS -->
              <input type="radio" name="tabs" id="tabtwo">
              <label for="tabtwo">Usuario</label>
              <div class="tab">
                <form action="<%=CONTEXT%>/usuario" method="post">
                    <input type="hidden" name="action" value="<%=ActionNames.USER_SEARCH%>"/>
                        <input type="text" class="search-box" placeholder="search.." /><br>
                        <input type="text" name="<%=ParameterNames.DISTANCIA%>" placeholder="distancia"><br>
                        <input type="text" name="<%=ParameterNames.EDAD_MAX%>" placeholder="edadMax"><br>
                        <input type="text" name="<%=ParameterNames.EDAD_MIN%>" placeholder="edadMin"><br>
                    <input type="submit" value="Buscar" name="Buscar" />
                  </form>
              </div>
            
               <!--  ESTABLECIMIENTOS -->
              <input type="radio" name="tabs" id="tabthree">
              <label for="tabthree">Establecimientos</label>
              <div class="tab">
                <form action="<%=CONTEXT%>/usuario" method="post">
                    <input type="hidden" name="action" value="search"/>
                        <input type="text" class="search-box" placeholder="search.." /><br>
                        <input type="text" name="<%=ParameterNames.DISTANCIA%>" placeholder="distancia"><br>
                        <select name="<%=ParameterNames.ESTABLECIMIENTO%>" id="select2">
                          <option value="0">Tipo establecimiento</option>
                          <option value="1">Piso</option>
                          <option value="2">Casa</option>
                          <option value="3">Casa con piscina</option>
                          <option value="4">Calle</option>
                          <option value="5">Bar</option>
                          <option value="6">Pub</option>
                          <option value="7">Discoteca</option>
                        </select><br>
                    <!--  o un button type submit -->
                    <input type="submit" value="Buscar" name="Buscar" />
                  </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
