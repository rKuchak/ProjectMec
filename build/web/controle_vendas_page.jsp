<%-- 
    Document   : controle_vendas_page
    Created on : 1 de mai de 2021, 09:58:49
    Author     : macuser
--%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Vehicle Control</title>
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">
        <script language="javascript" src="js/mizanscene.js"></script>

        <script type="text/javascript">
            function fCarregarMarcaVeiculo() {
                document.ControleVendasModel.action = "ControleVendas.do?action=carregarMarcaVeiculo";
                document.ControleVendasModel.submit();
            }

            function fCarregarVeiculosPorMarca() {
                document.ControleVendasModel.action = "ControleVendas.do?action=carregarVeiculos";
                document.ControleVendasModel.submit();
            }

            function fValidarVeiculo() {
                document.ControleVendasModel.action = "ControleVendas.do?action=validarVeiculo";
                document.ControleVendasModel.submit();
            }

            function fSalvar() {
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
                var dataVenda = document.getElementById("dataVenda").value;
                var idPessoa = document.ControleVendasModel.idPessoa.value;
                var qtdParcelas = document.ControleVendasModel.qtdParcelas.value;
                var valorEntrada = document.ControleVendasModel.valorEntrada.value;
                var diaPagamentoPrestacao = document.ControleVendasModel.diaPagamentoPrestacao.value;

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
                    alert("The gearbox must be informed");
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
                    alert("The Inspection of the vehicle must be informed");
                    document.getElementById("shaken").focus();
                } else if (capacidadePessoa === "") {
                    alert("The capacity of people must be informed");
                    document.getElementById("capacidadePessoa").focus();
                }  else if (idPessoa === "") {
                    alert("The Person must be informed that the Vehicle has been sold");
                    document.getElementById("idPessoa").focus();
                } else if (valorEntrada.length < 1) {
                    alert("The Input value must be informed even if it is 0 (Zero) ");
                    document.getElementById("valorEntrada").focus();
                } else if (qtdParcelas > 1 && diaPagamentoPrestacao == 0){
                    alert("The installment amount cannot be greater than 1, if payment is made in cash. Either you choose a payment day or enter the amount of installment 1 for cash payment")
                } 
                else {
                    if (idTipoVeiculo != null && idTipoVeiculo == 1) {
                        var nrPortas = document.ControleVendasModel.nrPortas.value;
                        if (nrPortas === "") {
                            alert("The number of doors must be informed");
                            document.getElementById("nrPortas").focus();
                        } else {
                            document.ControleVendasModel.action = "ControleVendas.do?action=save&dataVenda=" + dataVenda;
                            document.ControleVendasModel.submit();
                        }
                    } else {
                        var freio = document.ControleVendasModel.freio.value;
                        if (freio === "") {
                            alert("The type of brake must be informed");
                            document.getElementById("freio").focus();
                        } else {
                            document.ControleVendasModel.action = "ControleVendas.do?action=save&dataVenda=" + dataVenda;
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
                <h1>Control Vehicle Sales</h1>
                <hr/>
            </div>
            <html:form action="ControleVendas" enctype="multipart/form-data">
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
                        </logic:present>
                        <logic:present name="listaVeiculoPorMarca" scope="session">
                            <td>
                                <div class="col-lg-12">
                                    Vehicle:
                                    <html:select property="idVeiculo" styleClass="form-control" styleId="idVeiculo" onchange="fValidarVeiculo()">
                                        <html:option value="">Select</html:option>
                                        <html:options collection="listaVeiculoPorMarca" property="idVeiculo" labelProperty="nomeVeiculo"></html:options>
                                    </html:select>   
                                </div>
                            </td>
                            <td>
                                <br/>
                                <a href="NomeVeiculo.do" style="color: red">
                                    If the vehicle is not registered, <br/>
                                    Click here to register
                                </a>
                            </td>
                        </logic:present>
                    </tr>
                </table>

                <logic:equal name="detalhesVeiculo" value="true">
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
                                    <html:text name="ControleVendasModel" property="precoCompra" styleId="precoCompra" styleClass="form-control" onkeypress="return(validarConteudo(event, 'numero'));"/>
                                </div>
                            </td>
                            <td>
                                <div class="col-lg-12">
                                    Sale price:
                                    <html:text name="ControleVendasModel" property="precoVenda" styleId="precoVenda" styleClass="form-control" onkeypress="return(validarConteudo(event, 'numero'));"/>
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
                                    km driven:
                                    <html:text name="ControleVendasModel" property="km" styleId="km" styleClass="form-control"/>
                                </div>
                            </td>
                            <td>
                                <div class="col-lg-12">
                                    Inspection:
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
                                        Number of Doors:
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
                                            <html:option value="Tambor">Drum Brake</html:option>
                                            <html:option value="Disco">Disc Brake</html:option>
                                            <html:option value="ABS">ABS Brake</html:option>
                                        </html:select>
                                    </logic:equal>
                                </div>
                            </td>
                        </tr>
                        <tr style="height: 70px">
                            <td colspan="4">
                                <div class="col-lg-12">
                                    Extra Details:
                                    <html:textarea name="ControleVendasModel" property="detalhesExtras" styleClass="form-control" rows="10">
                                    </html:textarea>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="col-lg-12">
                                    Date of Sale:
                                    <input type="date" class="form-control" id="dataVenda" name="dataVenda"/>
                                </div>
                            </td>
                            <td colspan="3">
                                <div class="col-lg-12">
                                    Choose the image:
                                    <html:file property="fileImg1" styleClass="form-control"/>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="col-lg-12">
                                    Person:
                                    <html:select property="idPessoa" styleClass="form-control" styleId="idPessoa">
                                        <html:option value="">Select</html:option>
                                        <html:options collection="listaPessoaFisica" property="id" labelProperty="nome"></html:options>
                                    </html:select>
                                </div>
                            </td>
                            <td>
                                <div class="col-lg-12">
                                    Entry Value:
                                    <html:text name="ControleVendasModel" property="valorEntrada" styleId="valorEntrada" styleClass="form-control" onkeypress="return(validarConteudo(event,'numero'));"/>
                                </div>
                            </td>
                            <td>
                                <div class="col-lg-12">
                                    Quantity Installments:
                                    <html:select property="qtdParcelas" styleClass="form-control" styleId="qtdParcelas">
                                        <html:option value="1">1</html:option>
                                        <html:option value="2">2</html:option>
                                        <html:option value="3">3</html:option>
                                        <html:option value="4">4</html:option>
                                        <html:option value="5">5</html:option>
                                        <html:option value="5">5</html:option>
                                        <html:option value="6">6</html:option>
                                        <html:option value="7">7</html:option>
                                        <html:option value="8">8</html:option>
                                        <html:option value="9">9</html:option>
                                        <html:option value="10">10</html:option>
                                        <html:option value="11">11</html:option>
                                        <html:option value="12">12</html:option>
                                    </html:select>
                                </div>
                            </td>
                            <td>
                                <div class="col-lg-12">
                                    Payment Day Installment:
                                    <html:select name="ControleVendasModel" property="diaPagamentoPrestacao" styleClass="form-control">
                                        <html:option value="0">Cash payment at sight</html:option>
                                        <html:option value="1">1</html:option>
                                        <html:option value="5">5</html:option>
                                        <html:option value="10">10</html:option>
                                        <html:option value="15">15</html:option>
                                        <html:option value="20">20</html:option>
                                        <html:option value="25">25</html:option>
                                        <html:option value="30">30</html:option>
                                    </html:select>
                                </div>
                            </td>
                        </tr>

                        <tr style="height: 70px">
                            <td colspan="4" align="center">
                                <input class="btn btn-success" style="width: 200px" type="button" value="Save" onClick="fSalvar();">
                            </td>
                        </tr>
                    </table>
                    <div style="padding-top: 150px;">
                        &n&nbsp;
                    </div>
                </logic:equal>

            </html:form>

        </logic:notEqual>    
    </body>
</html>
