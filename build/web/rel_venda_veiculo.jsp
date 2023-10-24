<%-- 
    Document   : rel_venda_veiculo
    Created on : 11 de set de 2021, 11:33:10
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
//            function fFiltrar(tipo) {
//                document.RelatorioModel.action = "Relatorio.do?action=filtrarRelPessoa&tipo=" + tipo;
//                document.RelatorioModel.submit();
//            }

            function ExportExcel(type, fn, dl) {
                var elt = document.getElementById('exportable_table');
                var wb = XLSX.utils.table_to_book(elt, {sheet: "Sheet JS"});
                return dl ?
                        XLSX.write(wb, {bookType: type, bookSST: true, type: 'base64'}) :
                        XLSX.writeFile(wb, fn || ('RelatorioVendaVeiculos.' + (type || 'xlsx')));
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
                    <td><b>ID</b></td>
                    <td><b><a href="javascript:fFiltrar('nome')" style="color: black">Vehicle Type <img src="imagens/up-arrow.png" width="12px"/> </a></b></td>
                    <td><b><a href="javascript:fFiltrar('data_nascimento')" style="color: black">Vehicle Brand <img src="imagens/up-arrow.png" width="12px"/></a></b></td>
                    <td><b><a href="javascript:fFiltrar('sexo')" style="color: black">Vehicle <img src="imagens/up-arrow.png" width="12px"/></a></b></td>
                    <td><b><a href="javascript:fFiltrar('email')" style="color: black">Purchase Price <img src="imagens/up-arrow.png" width="12px"/></a></b></td>
                    <td><b><a href="javascript:fFiltrar('my_number')" style="color: black">Sale Price <img src="imagens/up-arrow.png" width="12px"/></a></b></td>
                    <td><b><a href="javascript:fFiltrar('telefone')" style="color: black">Sale Date <img src="imagens/up-arrow.png" width="12px"/></a></b></td>
                    <td><b><a href="javascript:fFiltrar('tipo_telefone')" style="color: black">Qty. Installments <img src="imagens/up-arrow.png" width="12px"/></a></b></td>
                    <td><b><a href="javascript:fFiltrar('provincia')" style="color: black">Buyer Name <img src="imagens/up-arrow.png" width="12px"/></a></b></td>
                    <td><b><a href="javascript:fFiltrar('cidade')" style="color: black">E-mail <img src="imagens/up-arrow.png" width="12px"/></a></b></td>
                    <td><b><a href="javascript:fFiltrar('cep')" style="color: black">Telephone <img src="imagens/up-arrow.png" width="12px"/></a></b></td>
                </tr>
                <logic:present name="listaVendasVeiculos" scope="session">
                    <logic:iterate name="listaVendasVeiculos" id="lista" scope="session">
                        <tr>
                            <td><bean:write name="lista" property="idVenda"/></td>
                            <td><bean:write name="lista" property="dsTipoVeiculo"/></td>
                            <td><bean:write name="lista" property="dsMarcaVeiculo"/></td>
                            <td><bean:write name="lista" property="dsVeiculo"/></td>
                            <td><bean:write name="lista" property="precoCompra"/></td>
                            <td><bean:write name="lista" property="precoVenda"/></td>
                            <td><bean:write name="lista" property="dataVenda"/></td>
                            <td><bean:write name="lista" property="qtdParcelas"/></td>
                            <td><bean:write name="lista" property="nomePessoa"/></td>
                            <td><bean:write name="lista" property="emailPessoa"/></td>
                            <td><bean:write name="lista" property="telefonePessoa"/></td>
                        </tr>
                    </logic:iterate>
                    <tr><td colspan="13" align="center">&nbsp;</td></tr>
                    <tr><td colspan="13" align="center">&nbsp;</td></tr>
                    
                    <tr>
                        <td colspan="13">
                            <b>Total Purchase Price:</b> ¥<bean:write name="RelatorioVendaVeiculoModel" property="vlTotalCompra"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="13">
                            <b>Total Value Sales Price:</b> ¥<bean:write name="RelatorioVendaVeiculoModel" property="vlTotalVenda"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="13">
                            <b>Total Net Value:</b> ¥<bean:write name="RelatorioVendaVeiculoModel" property="vlTotalLucroLiquido"/>
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
