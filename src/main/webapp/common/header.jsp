<%@ page import="org.apache.commons.lang3.StringUtils,java.util.List,com.luis.ravegram.model.*,com.luis.ravegram.web.controller.util.*, com.luis.ravegram.web.controller.*, com.luis.ravegram.web.util.*,java.util.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
 
<%
	String CONTEXT = request.getContextPath();
%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="ISO-8859-1" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Ravegram | Tu red social de eventos</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="<%=CONTEXT%>/css/style.css" />
    <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  </head>
  <body>
  <script>
      function buscarUsuariosAjax() {
    	    var buscador = $('#buscador').val();
    	    if (buscador.length>0) {
            var url = '/RavegramWeb/usuario-service';
                $.ajax({
                   type: "GET",
                   url: url,
               data: "action=user-search&buscador="+buscador,
               success: function(data) {
            	$('#buscador-results').empty();
            	$('.resultados-busqueda').show();
                for (i = 0; i<data.length; i++) {
                	$('#buscador-results').append('<a href="/RavegramWeb/private/usuario?action=user-detail&id='+data[i].id+'"><p><b>'+data[i].userName+'</b></p></a>');
                }
              }
            });
    	 }else{
    		$('#buscador-results').empty();
    		$('.resultados-busqueda').hide();
    	 }
        }
     
      
      </script>
  
 	   <%
 	     	    
    	UsuarioDTO usuario = (UsuarioDTO) SessionManager.get(request, AttributeNames.USER);
 	   
    %>
    
    <% if(usuario==null){ %>
    
     <%@include file="/common/nav-deslogeado.jsp"%>
     
    <%}else{ %>
    
     <%@include file="/common/nav-logeado.jsp"%>
     
    <%} %>
    
     <div  id="buscador-results" class="resultados-busqueda">

      
    </div>
    
    <%@include file="/common/errors.jsp"%>  
    
