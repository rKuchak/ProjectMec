<%-- 
    Document   : rel_parcela_paga_page
    Created on : 25 de set de 2021, 11:03:09
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

        <style>
            body {
                margin:0;
                padding:0;
            }

        </style>
        <script type="text/javascript">
            function fVisualizar() {
                var ano = document.getElementById("ano").value;

                if (ano.length < 1) {
                    alert("The year must be informed correctly!!");
                } else {
                    document.RelatorioVendaVeiculoModel.action = "RelatorioVendaVeiculo.do?action=relParcelasPagas";
                    document.RelatorioVendaVeiculoModel.target = "_blank";
                    document.RelatorioVendaVeiculoModel.submit();
                }
            }
        </script>
    </head>
    <body>

        <logic:notEqual name="nome" value="" scope="session">
            <jsp:include page="topo.jsp"/>
            <div align="center">
                <h1>Vehicle Installments Paid Reports</h1>
                <hr/>
            </div>

            <html:form action="RelatorioVendaVeiculo">
                <table width="40%" border="0" align="center" class="table-condensed">
                    <tr>
                        <td>
                            <label>Month</label>
                            <html:select name="RelatorioVendaVeiculoModel" property="mes" styleId="mes" styleClass="form-control">
                                <html:option value="1">January</html:option>
                                <html:option value="2">February</html:option>
                                <html:option value="3">March</html:option>
                                <html:option value="4">April</html:option>
                                <html:option value="5">May</html:option>
                                <html:option value="6">June</html:option>
                                <html:option value="7">July</html:option>
                                <html:option value="8">August</html:option>
                                <html:option value="9">September</html:option>
                                <html:option value="10">October</html:option>
                                <html:option value="11">November</html:option>
                                <html:option value="12">December</html:option>
                            </html:select>
                        </td>
                        <td width="10%">
                            <label>Year</label>
                            <html:text name="RelatorioVendaVeiculoModel" property="ano" styleId="ano" styleClass="form-control"/>
                        </td>
                        <td>
                            <br/>
                            <a href="javascript:fVisualizar()" class="btn btn-success">View</a>
                        </td>
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


