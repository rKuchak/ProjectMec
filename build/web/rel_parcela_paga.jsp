<%-- 
    Document   : rel_parcela_paga
    Created on : 25 de set de 2021, 11:22:51
    Author     : macuser
--%>


<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Reports</title>
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">
        <link rel="stylesheet" media="all" type="text/css" href="css/estilo.css" />

        <!-- Link para exportar Excel-->
        <script type="text/javascript" src="https://unpkg.com/xlsx@0.15.1/dist/xlsx.full.min.js"></script>

        <style>
            body {
                margin:0;
                padding:0;
            }

        </style>
        <script language="JavaScript">
            function ExportExcel(type, fn, dl) {
                var elt = document.getElementById('exportable_table');
                var wb = XLSX.utils.table_to_book(elt, {sheet: "Sheet JS"});
                return dl ?
                        XLSX.write(wb, {bookType: type, bookSST: true, type: 'base64'}) :
                        XLSX.writeFile(wb, fn || ('RelatorioParcelasPagas.' + (type || 'xlsx')));
            }
        </script>

    </head>
    <body>
        <logic:present name="errors">
            <script>
                alert('<bean:write name="errors"/>');
            </script>
        </logic:present>
        <div align="center">
            <img src="imagens/logo_mitsistemas_nova_preta.png"/>
            <hr/>
        </div>
        <html:form action="RelatorioVendaVeiculo">
            <table width="90%" class="table-condensed" align="center" id="exportable_table">
                <tr style="background-color: #D3D3D3">
                    <td><b>Vehicle Type</b></td>
                    <td><b>Brand</b></td>
                    <td><b>Vehicle</b></td>
                    <td><b>Buyer Name</b></td>
                    <td><b>E-mail</b></td>
                    <td><b>Telephone</b></td>
                    <td><b>Payment Date Made</b></td>
                    <td><b>Amount paid</b></td>
                </tr>
                <logic:present name="listaParcelasPagas" scope="session">
                    <logic:iterate name="listaParcelasPagas" id="lista" scope="session">
                        <tr>
                            <td><bean:write name="lista" property="dsTipoVeiculo"/></td>
                            <td><bean:write name="lista" property="dsMarcaVeiculo"/></td>
                            <td><bean:write name="lista" property="dsVeiculo"/></td>
                            <td><bean:write name="lista" property="nomePessoa"/></td>
                            <td><bean:write name="lista" property="emailPessoa"/></td>
                            <td><bean:write name="lista" property="telefonePessoa"/></td>
                            <td><bean:write name="lista" property="dataPagamentoRealizado"/></td>
                            <td><bean:write name="lista" property="valorPago"/></td>
                        </tr>
                    </logic:iterate>
                    <tr><td colspan="13" align="center">&nbsp;</td></tr>
                    <tr><td colspan="13" align="center">&nbsp;</td></tr>
                    
                    <tr>
                        <td colspan="13">
                            <b>Total Amount Paid:</b> ¥<bean:write name="RelatorioVendaVeiculoModel" property="vlTotalPago"/>
                        </td>
                    </tr>
                    <tr><td colspan="13" align="center">&nbsp;</td></tr>
                    <tr><td colspan="13" align="center">&nbsp;</td></tr>
                    <tr>
                        <td colspan="13" align="center">
                            <input type="button" class="btn btn-default" value="Print out" onclick="javascript: style.display = 'none', document.getElementById('export').style.display = 'none', window.print()"/>
                            <button class="btn btn-info" onclick="ExportExcel('xlsx')" id="export">Export to Excel</button>
                        </td>
                    </tr>
                </logic:present>


            </table>

        </html:form>

    </body>
</html>
