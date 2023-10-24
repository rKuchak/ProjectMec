<%-- 
    Document   : gerenciador_veiculos
    Created on : 3 de abr de 2021, 10:02:39
    Author     : macuser
--%>


<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Vehicle Manager</title>
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">
    </head>
    <body>

        <logic:notEqual name="nome" value="" scope="session">
            <jsp:include page="topo.jsp"/>

            <div align="center">
                <h1>person registration</h1>
                <hr/>
            </div>
  
        </logic:notEqual>    
    </body>
</html>

