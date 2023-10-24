<%-- 
    Document   : rel_pessoas_cadastradas
    Created on : 28 de ago de 2021, 10:41:25
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
            function fFiltrar(tipo) {
                document.RelatorioModel.action = "Relatorio.do?action=filtrarRelPessoa&tipo=" + tipo;
                document.RelatorioModel.submit();
            }

            function ExportExcel(type, fn, dl) {
                var elt = document.getElementById('exportable_table');
                var wb = XLSX.utils.table_to_book(elt, {sheet: "Sheet JS"});
                return dl ?
                        XLSX.write(wb, {bookType: type, bookSST: true, type: 'base64'}) :
                        XLSX.writeFile(wb, fn || ('RelatorioPessoasCadastradas.' + (type || 'xlsx')));
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
        <html:form action="Relatorio">
            <table width="90%" class="table-condensed" align="center" id="exportable_table">
                <tr style="background-color: #D3D3D3">
                    <td><b>ID</b></td>
                    <td><b><a href="javascript:fFiltrar('nome')" style="color: black">Name <img src="imagens/up-arrow.png" width="12px"/> </a></b></td>
                    <td><b><a href="javascript:fFiltrar('data_nascimento')" style="color: black">Birth date <img src="imagens/up-arrow.png" width="12px"/></a></b></td>
                    <td><b><a href="javascript:fFiltrar('sexo')" style="color: black">Gender <img src="imagens/up-arrow.png" width="12px"/></a></b></td>
                    <td><b><a href="javascript:fFiltrar('email')" style="color: black">E-mail <img src="imagens/up-arrow.png" width="12px"/></a></b></td>
                    <td><b><a href="javascript:fFiltrar('my_number')" style="color: black">MyNumber <img src="imagens/up-arrow.png" width="12px"/></a></b></td>
                    <td><b><a href="javascript:fFiltrar('telefone')" style="color: black">Telephone <img src="imagens/up-arrow.png" width="12px"/></a></b></td>
                    <td><b><a href="javascript:fFiltrar('tipo_telefone')" style="color: black">Phone Type <img src="imagens/up-arrow.png" width="12px"/></a></b></td>
                    <td><b><a href="javascript:fFiltrar('provincia')" style="color: black">Province <img src="imagens/up-arrow.png" width="12px"/></a></b></td>
                    <td><b><a href="javascript:fFiltrar('cidade')" style="color: black">City <img src="imagens/up-arrow.png" width="12px"/></a></b></td>
                    <td><b><a href="javascript:fFiltrar('cep')" style="color: black">Zip <img src="imagens/up-arrow.png" width="12px"/></a></b></td>
                    <td><b><a href="javascript:fFiltrar('endereco')" style="color: black">Address <img src="imagens/up-arrow.png" width="12px"/></a></b></td>
                    <td><b><a href="javascript:fFiltrar('bairro')" style="color: black">Neighborhood <img src="imagens/up-arrow.png" width="12px"/></a></b></td>
                    <td><b><a href="javascript:fFiltrar('data_cadastro')" style="color: black">Registration date <img src="imagens/up-arrow.png" width="12px"/></a></b></td>
                </tr>
                <logic:present name="listaPessoasCadastradas" scope="session">
                    <logic:iterate name="listaPessoasCadastradas" id="lista" scope="session">
                        <tr>
                            <td><bean:write name="lista" property="idPessoa"/></td>
                            <td><bean:write name="lista" property="nomePessoa"/></td>
                            <td><bean:write name="lista" property="dataNascimento"/></td>
                            <td><bean:write name="lista" property="sexo"/></td>
                            <td><bean:write name="lista" property="email"/></td>
                            <td><bean:write name="lista" property="myNumber"/></td>
                            <td><bean:write name="lista" property="numeroTelefone"/></td>
                            <td><bean:write name="lista" property="tipoTelefone"/></td>
                            <td><bean:write name="lista" property="provincia"/></td>
                            <td><bean:write name="lista" property="cidade"/></td>
                            <td><bean:write name="lista" property="cep"/></td>
                            <td><bean:write name="lista" property="endereco"/></td>
                            <td><bean:write name="lista" property="bairro"/></td>
                            <td><bean:write name="lista" property="dataCadastro"/></td>
                        </tr>
                    </logic:iterate>
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
