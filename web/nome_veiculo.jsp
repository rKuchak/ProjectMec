<%-- 
    Document   : nome_veiculo
    Created on : 10 de abr de 2021, 11:32:31
    Author     : macuser
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Tipo de Veículo</title>
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">

        <script type="text/javascript">
            function fCarregarMarcaVeiculo() {
                document.VeiculoModel.action = "NomeVeiculo.do?action=carregarMarcaVeiculo";
                document.VeiculoModel.submit();
            }

            function fCarregarVeiculosPorMarca() {
                document.VeiculoModel.action = "NomeVeiculo.do?action=carregarVeiculoPorMarca";
                document.VeiculoModel.submit();
            }

            function fCadastrar() {
                var idTipoVeiculo = document.getElementById("idTipoVeiculo").value;
                var idMarcaVeiculo = document.getElementById("idMarcaVeiculo").value;
                var nomeVeiculo = document.getElementById("nomeVeiculo").value;

                if (idTipoVeiculo == "") {
                    alert("The Vehicle Type must be entered correctly!!");
                    document.getElementById("idTipoVeiculo").focus();
                } else if (idMarcaVeiculo == "") {
                    alert("The vehicle brand must be informed correctly!!");
                    document.getElementById("idMarcaVeiculo").focus();
                } else if (nomeVeiculo.length < 2) {
                    alert("The name of the Vehicle must be entered correctly!!");
                    document.getElementById("nomeVeiculo").focus();
                } else {
                    document.VeiculoModel.action = "NomeVeiculo.do?action=save";
                    document.VeiculoModel.submit();
                }

            }


            function fExcluir(idVeiculo) {
                if (confirm("Do you really want to delete???")) {
                    document.VeiculoModel.action = "NomeVeiculo.do?action=excluir&idVeiculo=" + idVeiculo;
                    document.VeiculoModel.submit();
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
                <h1>Vehicle Manager</h1>
                <hr/>
            </div>
            <html:form action="NomeVeiculo">
                <table width="60%" border="0" align="center" class="table-condensed">
                    <tr>
                        <td>
                            <div class="col-lg-12">
                                Vehicle Type:
                                <html:select property="idTipoVeiculo" styleClass="form-control" styleId="idTipoVeiculo" onchange="fCarregarMarcaVeiculo()">
                                    <html:option value="">Select</html:option>
                                    <html:options collection="listaTipoVeiculo" property="idTipoVeiculo" labelProperty="dsTipoVeiculo"></html:options>
                                </html:select>   
                            </div>
                        </td>
                        <logic:present name="listaMarcaVeiculo" scope="session">
                            <td>
                                <div class="col-lg-12">
                                    Vehicle Brand:
                                    <html:select property="idMarcaVeiculo" styleClass="form-control" styleId="idMarcaVeiculo" onchange="fCarregarVeiculosPorMarca()">
                                        <html:option value="">Select</html:option>
                                        <html:options collection="listaMarcaVeiculo" property="idMarcaVeiculo" labelProperty="dsMarcaVeiculo"></html:options>
                                    </html:select>   
                                </div>
                            </td>
                            <td>
                                <div class="col-lg-12">
                                    Vehicle Name:
                                    <html:text styleClass="form-control" name="VeiculoModel" property="nomeVeiculo" styleId="nomeVeiculo"/>        
                                </div>
                            </td>
                            <td>
                                <br/>
                                <input class="btn btn-success" type="button" value="Register" onClick="fCadastrar();">
                            </td>
                        </logic:present>

                    </tr>
                </table>

                <logic:present name="listaVeiculoPorMarca" scope="session">
                    <div align="center" style="margin-top: 100px">
                        <h1>List of Registered Vehicles</h1>
                    </div>

                    <table width="60%" border="0" align="center" class="table-condensed">
                        <tr style="background-color: #F4F4F4">
                            <td><b>Vehicle Brand</b></td>
                            <td><b>Vehicle Name</b></td>
                            <td width='10%'>&nbsp;</td>
                        </tr>
                        <logic:iterate name="listaVeiculoPorMarca" scope="session" id="listaNomeVeiculo">
                            <tr>
                                <td>
                                    <bean:write name="listaNomeVeiculo" property="dsMarcaVeiculo"/>
                                </td>
                                <td>
                                    <bean:write name="listaNomeVeiculo" property="nomeVeiculo"/>
                                </td>
                                <td width='10%'>
                                    <input type="button" class="btn btn-danger" value="Delete" onclick="fExcluir(<bean:write name="listaNomeVeiculo" property="idVeiculo"/>)"/>
                                </td>
                            </tr>
                        </logic:iterate>
                    </table>
                </logic:present>

            </html:form>

        </logic:notEqual>    
    </body>
</html>
