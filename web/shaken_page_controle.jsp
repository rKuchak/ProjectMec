<%-- 
    Document   : shaken_page_controle
    Created on : 19 de jun de 2021, 10:12:19
    Author     : macuser
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Inspection control</title>
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">
        <script language="javascript" src="js/mizanscene.js"></script>

        <script type="text/javascript">

            function fPesquisar() {
                var idPessoa = document.ShakenModel.idPessoa.value;
                if (idPessoa === "") {
                    alert("The person must be informed that the Shaken was performed");
                    document.getElementById("idPessoa").focus();
                } else {
                    document.ShakenModel.action = "Shaken.do?action=pesquisarShaken";
                    document.ShakenModel.submit();
                }

            }

            function fEditar(id) {
                document.ShakenModel.action = "Shaken.do?action=editarPagamentoShaken&id=" + id;
                document.ShakenModel.submit();
            }

            function fExcluir(id, idPessoa) {
                if (confirm("Do you really want to DELETE??")) {
                    document.ShakenModel.action = "Shaken.do?action=excluirShaken&id=" + id + "&idPessoa=" + idPessoa;
                    document.ShakenModel.submit();
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

        <logic:notEqual name="nome" value="" scope="session">
            <jsp:include page="topo.jsp"/>

            <div align="center">
                <h1>List Inspection control Accomplished</h1>
                <hr/>
            </div>
            <html:form action="Shaken">
                <table width="60%" border="0" align="center" class="table-condensed">
                    <tr>
                        <td>
                            <div class="col-lg-12">
                                Name Person with inspection:
                                <html:select property="idPessoa" styleClass="form-control" styleId="idPessoa">
                                    <html:option value="">Select</html:option>
                                    <html:options collection="listaPessoasComShaken" property="idPessoa" labelProperty="nomePessoa"></html:options>
                                </html:select>   
                            </div>
                        </td>
                        <td>
                            <div class="col-lg-12">
                                &nbsp; <br/>
                                <input class="btn btn-success" style="width: 200px" type="button" value="Search" onClick="fPesquisar();">
                            </div>
                        </td>
                    </tr>
                </table>

                <br/>
                <logic:present name="listaShaken" scope="request">
                    <logic:iterate id="lista" name="listaShaken" scope="request">
                        <div style="border: 0px solid black; padding: 20px; background-color: #F4F4F4;">
                            <table width="60%" border="0" align="center" class="table-condensed">
                                <tr>
                                    <td width="25%">
                                        <b>ID:</b> <bean:write name="lista" property="id"/>
                                    </td>
                                    <td width="25%">
                                        <b>Date of Accomplishment:</b> <bean:write name="lista" property="dataRealizacao"/>
                                    </td>
                                    <td width="25%">
                                        <b>Next Due Date:</b> <bean:write name="lista" property="dataVencimento"/>
                                    </td>
                                    <td width="25%" rowspan="2" align="center">
                                        <input class="btn btn-primary" style="width: 150px; height: 50px" type="button" value="Edit Payment" onClick="fEditar(<bean:write name="lista" property="id"/>);">
                                    </td>
                                </tr>
                                <tr>
                                    <td width="25%">
                                        <b>Vehicle Type:</b> <bean:write name="lista" property="dsTipoVeiculo"/>
                                    </td>
                                    <td width="25%">
                                        <b>Vehicle Brand:</b> <bean:write name="lista" property="dsMarcaVeiculo"/>
                                    </td>
                                    <td width="25%">
                                        <b>Vehicle Name:</b> <bean:write name="lista" property="nomeVeiculo"/>
                                    </td>

                                </tr>
                                <tr>
                                    <td width="25%">
                                        <b>Vehicle Year:</b> <bean:write name="lista" property="anoVeiculo"/>
                                    </td>
                                    <td width="25%">
                                        <b>Qty. Installments:</b> <bean:write name="lista" property="qtdParcelas"/>
                                    </td>
                                    <td width="25%">
                                        <b>Payment Day:</b> <bean:write name="lista" property="diaPagamentoPrestacao"/>
                                    </td>
                                    <td width="25%" rowspan="2" align="center">
                                        <input class="btn btn-danger" style="width: 150px;  height: 50px" type="button" value="Delete" onClick="fExcluir(<bean:write name="lista" property="id"/>, <bean:write name="lista" property="idPessoa"/>);">
                                    </td>
                                </tr>
                                <tr>
                                    <td width="25%">
                                        <b>Amount charged:</b> <bean:write name="lista" property="valorCobrado"/>
                                    </td>
                                    <td width="25%">
                                        <b>Entry Value</b> <bean:write name="lista" property="valorEntrada"/>
                                    </td>
                                    <td width="25%">
                                        <b>Remaining Value:</b> <bean:write name="lista" property="valorRestante"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="3">
                                        <b>Observation:</b> <bean:write name="lista" property="observacao"/>
                                    </td>
                                </tr>

                            </table>
                        </div>
                        <br/>       
                    </logic:iterate>

                </logic:present>


            </html:form>

        </logic:notEqual>    
    </body>
</html>


