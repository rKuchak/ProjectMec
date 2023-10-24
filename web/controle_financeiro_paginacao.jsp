<%-- 
    Document   : controle_financeiro_paginacao
    Created on : 15 de out de 2021, 21:17:39
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
            function fPesquisa(offset) {
                document.ControleFinanceiroModel.action = "ControleFinanceiro.do?action=paginacao&offset=" + offset;
                document.ControleFinanceiroModel.submit();
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
                        <td colspan="5">
                            Month / year: 
                            <b>
                                <logic:equal name="ControleFinanceiroModel" property="mes" value="1">January</logic:equal>
                                <logic:equal name="ControleFinanceiroModel" property="mes" value="2">February</logic:equal>
                                <logic:equal name="ControleFinanceiroModel" property="mes" value="3">March</logic:equal>
                                <logic:equal name="ControleFinanceiroModel" property="mes" value="4">April</logic:equal>
                                <logic:equal name="ControleFinanceiroModel" property="mes" value="5">May</logic:equal>
                                <logic:equal name="ControleFinanceiroModel" property="mes" value="6">June</logic:equal>
                                <logic:equal name="ControleFinanceiroModel" property="mes" value="7">July</logic:equal>
                                <logic:equal name="ControleFinanceiroModel" property="mes" value="8">August</logic:equal>
                                <logic:equal name="ControleFinanceiroModel" property="mes" value="9">September</logic:equal>
                                <logic:equal name="ControleFinanceiroModel" property="mes" value="10">October</logic:equal>
                                <logic:equal name="ControleFinanceiroModel" property="mes" value="11">November</logic:equal>
                                <logic:equal name="ControleFinanceiroModel" property="mes" value="12">December</logic:equal>
                                de <bean:write name="ControleFinanceiroModel" property="ano"/>
                            </b>
                            <html:hidden name="ControleFinanceiroModel" property="mes"/>
                            <html:hidden name="ControleFinanceiroModel" property="ano"/>
                        </td>
                    </tr>
                    <tr>
                        <td><label>ID</label></td>
                        <td><label>Type</label></td>
                        <td><label>Reference Date</label></td>
                        <td><label>Value</label></td>
                        <td><label>Description</label></td>
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
                            </tr>
                        </logic:iterate>
                    </logic:present>
                    <tr><td colspan="5">&nbsp;</td></tr>
                    <tr><td colspan="5">&nbsp;</td></tr>
                    <tr>
                        <td colspan="5" align="center"><b><bean:write name="pagerHeader" scope="request" filter="false"/></b></td>
                    </tr>
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



