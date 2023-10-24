<%-- 
    Document   : controle_financeiro_adicionar
    Created on : 2 de out de 2021, 09:44:58
    Author     : macuser
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<!DOCTYPE html>
<html lang="pt-br" class="js-disabled">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">
        <link rel="stylesheet" media="all" type="text/css" href="css/estilo.css" />
        <script language="javascript" src="js/mizanscene.js"></script>

        <style>
            body {
                margin:0;
                padding:0;
            }

        </style>
        <script type="text/javascript">
            function fAdicionar() {
                var dataReferencia = document.getElementById("dataReferencia").value;
                var valor = document.getElementById("valor").value;
                var descricao = document.getElementById("descricao").value;

                if (dataReferencia.length < 1) {
                    alert("The reference date must be informed!!");
                } else if (valor.length < 1) {
                    alert("The value must be informed correctly!!")
                } else if (descricao.length < 4) {
                    alert("The description must be provided correctly!!")
                } else {
                    document.ControleFinanceiroModel.action = "ControleFinanceiro.do?action=adicionar";
                    document.ControleFinanceiroModel.submit();
                }
            }

            function fExcluir(id) {
                if (confirm("Do you really want to delete?")) {
                    document.ControleFinanceiroModel.action = "ControleFinanceiro.do?action=excluir&id=" + id;
                    document.ControleFinanceiroModel.submit();
                }
            }
        </script>
    </head>
    <body>

        <logic:notEqual name="nome" value="" scope="session">
            <jsp:include page="topo.jsp"/>
            <div align="center">
                <h1>Financial control</h1>
                <hr/>
            </div>

            <html:form action="ControleFinanceiro">
                <table width="60%" border="0" align="center" class="table-condensed">
                    <tr>
                        <td>
                            <label>Type</label>
                            <html:select name="ControleFinanceiroModel" property="tipo" styleClass="form-control">
                                <html:option value="entrada">Entry</html:option>
                                <html:option value="saida">Exit</html:option>
                            </html:select>
                        </td>
                        <td>
                            <label>Reference Date</label>
                            <input type="date" class="form-control" id="dataReferencia" name="dataReferencia"/>
                        </td>
                        <td>
                            <label>Value</label>
                            <html:text name="ControleFinanceiroModel" property="valor" styleId="valor" styleClass="form-control" onkeypress="return(validarConteudo(event, 'numero'));"/>
                        </td>
                        <td>
                            <label>Description</label>
                            <html:text name="ControleFinanceiroModel" property="descricao" styleId="descricao" styleClass="form-control"/>
                        </td>
                        <td>
                            <br/>
                            <a href="javascript:fAdicionar()" class="btn btn-success">Add</a>
                        </td>
                    </tr>
                </table>
                <hr/>
                <table width="60%" border="0" align="center" class="table-condensed">
                    <tr>
                        <td><label>ID</label></td>
                        <td><label>Type</label></td>
                        <td><label>Reference Date</label></td>
                        <td><label>Value</label></td>
                        <td><label>Description</label></td>
                        <td>&nbsp;</td>
                    </tr>
                    <logic:present name="listaControleFinanceiro" scope="request">
                        <logic:iterate name="listaControleFinanceiro" id="lista" scope="request">
                            <tr>
                                <td>
                                    <bean:write name="lista" property="id"/>
                                </td>
                                <td>
                                    <bean:write name="lista" property="tipo"/>
                                </td>
                                <td>
                                    <bean:write name="lista" property="dataReferencia"/>
                                </td>
                                <td>
                                    ¥<bean:write name="lista" property="valor"/>
                                </td>
                                <td>
                                    <bean:write name="lista" property="descricao"/>
                                </td>
                                <td>
                                    <a href="javascript:fExcluir(<bean:write name="lista" property="id"/>)" class="btn btn-danger">Delete</a>
                                </td>
                            </tr>
                        </logic:iterate>
                    </logic:present>
                </table>
            </html:form>

        </logic:notEqual>
        <logic:equal name="nome" value="" scope="session">
            <div style="padding-top: 20%">
                <div class="alert alert-danger" align="center">
                    <img src="imagens/cancel.png" width="50px"/>
                    <br/>
                    <br/>
                    You are not logged into the system!!
                    <br/>
                    <br/>
                    <a href="index.jsp" class="btn btn-default">Click here to return to the login page</a>
                </div>
            </div>
        </logic:equal>
    </body>
</html>


