<%-- 
    Document   : controle_vendas_atualizar
    Created on : 22 de mai de 2021, 09:07:15
    Author     : macuser
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Controle de Veículo</title>
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">

        <script type="text/javascript">
            function fAtualizar() {
                var idTipoVeiculo = document.ControleVendasModel.idTipoVeiculo.value;
                var chassi = document.ControleVendasModel.chassi.value;
                var cor = document.ControleVendasModel.cor.value;
                var ano = document.ControleVendasModel.ano.value;
                var cambio = document.ControleVendasModel.cambio.value;
                var precoVenda = document.ControleVendasModel.precoVenda.value;
                var motor = document.ControleVendasModel.motor.value;
                var combustivel = document.ControleVendasModel.combustivel.value;
                var km = document.ControleVendasModel.km.value;
                var shaken = document.ControleVendasModel.shaken.value;
                var capacidadePessoa = document.ControleVendasModel.capacidadePessoa.value;


                if (chassi === "") {
                    alert("Chassis must be informed");
                    document.getElementById("chassi").focus();
                } else if (cor === "") {
                    alert("Color must be informed");
                    document.getElementById("cor").focus();
                } else if (ano === "") {
                    alert("The year must be informed");
                    document.getElementById("ano").focus();
                } else if (cambio === "") {
                    alert("The car gearbox must be informed");
                    document.getElementById("cambio").focus();
                } else if (precoVenda.length < 4) {
                    alert("The Sales Price must be informed correctly");
                    document.getElementById("precoVenda").focus();
                } else if (motor === "") {
                    alert("The Engine must be informed");
                    document.getElementById("motor").focus();
                } else if (combustivel === "") {
                    alert("Fuel Type must be informed");
                    document.getElementById("combustivel").focus();
                } else if (km === "") {
                    alert("Mileage must be provided");
                    document.getElementById("km").focus();
                } else if (shaken === "") {
                    alert("The vehicle inspection must be informed");
                    document.getElementById("shaken").focus();
                } else if (capacidadePessoa === "") {
                    alert("The capacity of people must be informed");
                    document.getElementById("capacidadePessoa").focus();
                } else {
                    if (idTipoVeiculo != null && idTipoVeiculo == 1) {
                        var nrPortas = document.ControleVendasModel.nrPortas.value;
                        if (nrPortas === "") {
                            alert("The number of doors must be informed");
                            document.getElementById("nrPortas").focus();
                        } else {
                            document.ControleVendasModel.action = "ControleVendas.do?action=atualizar";
                            document.ControleVendasModel.submit();
                        }
                    } else {
                        var freio = document.ControleVendasModel.freio.value;
                        if (freio === "") {
                            alert("The type of brake must be informed");
                            document.getElementById("freio").focus();
                        } else {
                            document.ControleVendasModel.action = "ControleVendas.do?action=atualizar";
                            document.ControleVendasModel.submit();
                        }
                    }
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
                <h1>Vehicle Sales Control - Update</h1>
                <hr/>
            </div>
            <html:form action="ControleVendas" enctype="multipart/form-data">
                <html:hidden property="idVendaVeiculo"/>
                <html:hidden property="idTipoVeiculo"/>
                <table width="60%" border="0" align="center" class="table-condensed">
                    <tr>
                        <td>
                            <div class="col-lg-12">
                                Vehicle Type:
                                <html:text property="dsTipoVeiculo" styleId="dsTipoVeiculo" styleClass="form-control" readonly="true"/>
                            </div>
                        </td>
                        <td>
                            <div class="col-lg-12">
                                Vehicle Brand:
                                <html:text property="dsMarcaVeiculo" styleId="dsMarcaVeiculo" styleClass="form-control" readonly="true"/>
                            </div>
                        </td>
                        <td>
                            <div class="col-lg-12">
                                Vehicle:
                                <html:text property="dsVeiculo" styleId="dsVeiculo" styleClass="form-control" readonly="true"/>
                            </div>
                        </td>
                    </tr>
                </table>

                <table width="60%" border="0" align="center" class="table-condensed">
                    <tr style="height: 70px">
                        <td>
                            <div class="col-lg-12">
                                Chassis:
                                <html:text name="ControleVendasModel" property="chassi" styleId="chassi" styleClass="form-control"/>
                            </div>
                        </td>
                        <td>
                            <div class="col-lg-12">
                                Color:
                                <html:text name="ControleVendasModel" property="cor" styleId="cor" styleClass="form-control"/>
                            </div>
                        </td>
                        <td>
                            <div class="col-lg-12">
                                Year:
                                <html:text name="ControleVendasModel" property="ano" styleId="ano" styleClass="form-control"/>
                            </div>
                        </td>
                        <td>
                            <div class="col-lg-12">
                                Gearbox:
                                <html:select name="ControleVendasModel" property="cambio" styleClass="form-control" styleId="cambio">
                                    <html:option value="">Select</html:option>
                                    <html:option value="A">Automatic</html:option>
                                    <html:option value="M">Manual</html:option>
                                    <html:option value="AM">Automatic/Manual</html:option>
                                </html:select> 
                            </div>
                        </td>
                    </tr>
                    <tr style="height: 70px">
                        <td>
                            <div class="col-lg-12">
                                Purchase Price:
                                <html:text name="ControleVendasModel" property="precoCompra" styleId="precoCompra" styleClass="form-control" readonly="true"/>
                            </div>
                        </td>
                        <td>
                            <div class="col-lg-12">
                                Sale price:
                                <html:text name="ControleVendasModel" property="precoVenda" styleId="precoVenda" styleClass="form-control" readonly="true"/>
                            </div>
                        </td>
                        <td>
                            <div class="col-lg-12">
                                Engine:
                                <html:text name="ControleVendasModel" property="motor" styleId="motor" styleClass="form-control"/>
                            </div>
                        </td>
                        <td>
                            <div class="col-lg-12">
                                Fuel:
                                <html:select name="ControleVendasModel" property="combustivel" styleClass="form-control" styleId="combustivel">
                                    <html:option value="">Select</html:option>
                                    <html:option value="E">Ethanol</html:option>
                                    <html:option value="G">Gasoline</html:option>
                                    <html:option value="D">Diesel</html:option>
                                    <html:option value="H">Hybrid</html:option>
                                </html:select> 
                            </div>
                        </td>
                    </tr>
                    <tr style="height: 70px">
                        <td>
                            <div class="col-lg-12">
                                KM driven:
                                <html:text name="ControleVendasModel" property="km" styleId="km" styleClass="form-control"/>
                            </div>
                        </td>
                        <td>
                            <div class="col-lg-12">
                                Car inspection:
                                <html:text name="ControleVendasModel" property="shaken" styleId="shaken" styleClass="form-control"/>
                            </div>
                        </td>
                        <td>
                            <div class="col-lg-12">
                                People Capacity:
                                <html:select name="ControleVendasModel" property="capacidadePessoa" styleClass="form-control" styleId="capacidadePessoa">
                                    <html:option value="">Select</html:option>
                                    <html:option value="1">1</html:option>
                                    <html:option value="2">2</html:option>
                                    <html:option value="3">3</html:option>
                                    <html:option value="4">4</html:option>
                                    <html:option value="5">5</html:option>
                                    <html:option value="6">6</html:option>
                                    <html:option value="7">7</html:option>
                                    <html:option value="8">8</html:option>
                                    <html:option value="9">9</html:option>
                                    <html:option value="10">10</html:option>
                                    <html:option value="11">+10</html:option>
                                </html:select> 
                            </div>
                        </td>
                        <td>
                            <div class="col-lg-12">
                                <logic:equal name="ControleVendasModel" property="idTipoVeiculo" value="1">
                                    Number of doors:
                                    <html:select name="ControleVendasModel" property="nrPortas" styleClass="form-control" styleId="nrPortas">
                                        <html:option value="">Select</html:option>
                                        <html:option value="1">1</html:option>
                                        <html:option value="2">2</html:option>
                                        <html:option value="3">3</html:option>
                                        <html:option value="4">4</html:option>
                                        <html:option value="5">5</html:option>
                                        <html:option value="6">6</html:option>
                                        <html:option value="7">7</html:option>
                                    </html:select>
                                </logic:equal>
                                <logic:equal name="ControleVendasModel" property="idTipoVeiculo" value="2">
                                    Brake Type:
                                    <html:select name="ControleVendasModel" property="freio" styleClass="form-control" styleId="freio">
                                        <html:option value="">Select</html:option>
                                        <html:option value="Tambor">Drum brake</html:option>
                                        <html:option value="Disco">Disc brake</html:option>
                                        <html:option value="ABS">ABS brake</html:option>
                                    </html:select>
                                </logic:equal>
                            </div>
                        </td>
                    </tr>
                    <tr style="height: 70px">
                        <td colspan="4">
                            <div class="col-lg-12">
                                Extra Details:
                                <html:textarea name="ControleVendasModel" property="detalhesExtras" styleClass="form-control" rows="10"></html:textarea>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="4">
                                <div class="col-lg-12">
                                    Choose the image:
                                <html:file name="ControleVendasModel" property="fileImg1" styleClass="form-control"/>
                            </div>
                        </td>
                    </tr>
                    <tr style="height: 70px">
                        <td colspan="4" align="center">
                            <input class="btn btn-success" style="width: 200px" type="button" value="Update" onClick="fAtualizar();">
                            <a href="ControleVendas.do?action=pesquisarVeiculosVenda" class="btn btn-default" style="width: 200px">Go back</a>
                        </td>
                    </tr>
                </table>

            </html:form>

        </logic:notEqual>    
    </body>
</html>
