<%-- 
    Document   : gerenciador_relatorios
    Created on : 28 de ago de 2021, 09:43:59
    Author     : macuser
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<!DOCTYPE html>
<html lang="pt-br" class="js-disabled">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">
        <link rel="stylesheet" media="all" type="text/css" href="css/estilo.css" />

        <style>
            body {
                margin:0;
                padding:0;
            }

        </style>
    </head>
    <body>

        <logic:notEqual name="nome" value="" scope="session">
            <jsp:include page="topo.jsp"/>
            <div align="center">
                <h1>System Reports</h1>
                <hr/>
            </div>

            <table width="60%" border="0" align="center" class="table-condensed">
                <tr>
                    <td width="70%">
                        <b>Registered People Report</b>
                    </td>
                    <td>
                        <a href="Relatorio.do?action=relPessoasCadastradasPage" class="btn btn-success">Generate report</a>
                    </td>
                </tr>
                <tr>
                    <td>
                        <b>Registered Vehicle Report</b>
                    </td>
                    <td>
                        <a href="Relatorio.do?action=relVeiculosCadatrados" class="btn btn-success" target="_blank">Generate report</a>
                    </td>
                </tr>
                <tr>
                    <td>
                        <b>Vehicles Sold by Sale Date Report</b>
                    </td>
                    <td>
                        <a href="RelatorioVendaVeiculo.do?action=relVendasVeiculosRealizadasPage" class="btn btn-success">Generate report</a>
                    </td>
                </tr>
                <tr>
                    <td>
                        <b>Report of Installments Paid for Vehicles Sold per Month</b>
                    </td>
                    <td>
                        <a href="RelatorioVendaVeiculo.do?action=relParcelasPagasPage" class="btn btn-success">Generate report</a>
                    </td>
                </tr>
                <tr>
                    <td>
                        <b>Inspection report carried out by date of sale</b>
                    </td>
                    <td>
                        <a href="RelatorioShakenVeiculo.do?action=relShakenRealizadasPage" class="btn btn-success">Generate report</a>
                    </td>
                </tr>
                <tr>
                    <td>
                        <b>Inspection Report Performed by Next Due Date</b>
                    </td>
                    <td>
                        <a href="RelatorioShakenVeiculo.do?action=relShakenVencerPage" class="btn btn-success">Generate report</a>
                    </td>
                </tr>
            </table>

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

