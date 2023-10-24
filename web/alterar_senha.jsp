<%-- 
    Document   : alterar_senha
    Created on : 13 de mar de 2021, 10:57:46
    Author     : macuser
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>JSP Page</title>
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">

        <script type="text/javascript">
            function fAlterarSenha() {
                var senhaAtual = document.getElementById("senhaAtual").value;
                var senhaNova = document.getElementById("senhaNova").value;

                if (senhaAtual.length < 2) {
                    alert("The Current Password must be entered correctly!!");
                    document.getElementById("senhaAtual").focus();
                } else if (senhaNova.length < 2) {
                    alert("The New Password must be entered correctly!!");
                    document.getElementById("senhaNova").focus();
                } else {
                    document.SenhaModel.action = "Senha.do?action=alterarSenha";
                    document.SenhaModel.submit();
                }

            }
        </script>

    </head>
    <body>

        <logic:present name="errors">
            <script>
                alert('<bean:write name="errors"/>');
            </script>
        </logic:present>

        <jsp:include page="topo.jsp"/>

        <div align="center">
            <h1>Change Password</h1>
            <hr/>
        </div>
        <html:form action="Senha">
            <table width="30%" border="0" align="center" class="table-condensed">
                <tr>
                    <html:hidden name="SenhaModel" property="idPessoa"/>
                    <td width="30%">ID:</td>
                    <td><b><bean:write name="SenhaModel" property="idPessoa"/></b></td>
                </tr>
                <tr>
                    <html:hidden name="SenhaModel" property="nomePessoa"/>
                    <td>Name:</td>
                    <td><b><bean:write name="SenhaModel" property="nomePessoa"/></b></td>
                </tr>
                <tr>
                    <td>Current password:</td>
                    <td>
                        <html:password name="SenhaModel" property="senhaAtual" styleId="senhaAtual"/>
                    </td>
                </tr>
                <tr>
                    <td>New password:</td>
                    <td>
                        <html:password name="SenhaModel" property="senhaNova" styleId="senhaNova"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        &nbsp;
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>
                        <input class="btn btn-success" type="button" value="ALTERAR SENHA" onClick="fAlterarSenha();">
                    </td>
                </tr>
            </table>

            <logic:equal name="senhaAlterada" value="true" scope="request">
                <div align="center" style="padding-top: 50px;">
                    <div class="alert alert-success">
                        <img src="imagens/checked.png" width="50px"/>
                        <b><bean:write name="errors"/></b>
                    </div>
                </div>
            </logic:equal>

            <logic:equal name="senhaAlterada" value="false" scope="request">
                <div align="center" style="padding-top: 50px;">
                    <div class="alert alert-danger">
                        <img src="imagens/cancel.png" width="50px"/>
                        <b><bean:write name="errors"/></b>
                    </div>
                </div>
            </logic:equal>


        </html:form>
    </body>
</html>

