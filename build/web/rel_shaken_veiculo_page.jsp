<%-- 
    Document   : rel_shaken_veiculo_page
    Created on : 18 de set de 2021, 09:21:52
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
        <title>Project Default - Report</title>
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
                var dataInicio = document.getElementById("dataInicio").value;
                var dataFinal = document.getElementById("dataFinal").value;

                if (dataInicio.length < 1) {
                    alert("The start date must be informed!!");
                } else if (dataFinal.length < 1) {
                    alert("The End date must be informed!!");
                } else {
                    document.RelatorioShakenVeiculoModel.action = "RelatorioShakenVeiculo.do?action=relShakenRealizadas";
                    document.RelatorioShakenVeiculoModel.target = "_blank";
                    document.RelatorioShakenVeiculoModel.submit();
                }
            }
        </script>
    </head>
    <body>

        <logic:notEqual name="nome" value="" scope="session">
            <jsp:include page="topo.jsp"/>
            <div align="center">
                <h1>Inspection Reports Performed by Date</h1>
                <hr/>
            </div>

            <html:form action="RelatorioShakenVeiculo">
                <table width="60%" border="0" align="center" class="table-condensed">
                    <tr>
                        <td>
                            <label>Start date</label>
                            <input type="date" class="form-control" id="dataInicio" name="dataInicio"/>
                        </td>
                        <td>
                            <label>Final date</label>
                            <input type="date" class="form-control" id="dataFinal" name="dataFinal"/>
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


