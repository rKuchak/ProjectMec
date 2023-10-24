<%-- 
    Document   : controle_vendas_detalhes
    Created on : 15 de mai de 2021, 10:27:20
    Author     : macuser
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Controle de Veículo</title>
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">
        <script type="text/javascript">
            function fImprimir() {
                window.print();
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
            <html:form action="ControleVendas">
                <h1 align="center">Vehicle Details</h1>
                <hr/>
                <div align="center">
                    <img src="<bean:write name="ControleVendasModel" property="pathImg1"/>" width="80%"/>
                </div>
                <br/>
                <table width="80%" border="1" align="center" class="table-condensed">
                    <tr>
                        <td width="50%">
                            <b>ID:</b> <bean:write name="ControleVendasModel" property="idVendaVeiculo"/>
                        </td>
                        <td>
                            <b>Vehicle Type:</b> <bean:write name="ControleVendasModel" property="dsTipoVeiculo"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <b>Date of Insertion:</b> <bean:write name="ControleVendasModel" property="dataInsercao"/>
                        </td>
                        <td>
                            <b>Sale Date:</b> <bean:write name="ControleVendasModel" property="dataVenda"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <b>Brand:</b> <bean:write name="ControleVendasModel" property="dsMarcaVeiculo"/>
                        </td>
                        <td>
                            <b>Vehicle Name:</b> <bean:write name="ControleVendasModel" property="dsVeiculo"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <b>Color:</b> <bean:write name="ControleVendasModel" property="cor"/>
                        </td>
                        <td>
                            <b>Year:</b> <bean:write name="ControleVendasModel" property="ano"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <b>Purchase Price:</b> <bean:write name="ControleVendasModel" property="precoCompra"/>
                        </td>
                        <td>
                            <b>Sale price:</b> <bean:write name="ControleVendasModel" property="precoVenda"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <b>Gearbox:</b> 
                            <logic:equal name="ControleVendasModel" property="cambio" value="A">Automatic</logic:equal>
                            <logic:equal name="ControleVendasModel" property="cambio" value="M">Manual</logic:equal>
                            <logic:equal name="ControleVendasModel" property="cambio" value="AM">Automatic/Manual</logic:equal>
                        </td>
                        <td>
                            <b>Engine:</b> <bean:write name="ControleVendasModel" property="motor"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <b>Fuel:</b> 
                            <logic:equal name="ControleVendasModel" property="combustivel" value="E">Ethanol</logic:equal>
                            <logic:equal name="ControleVendasModel" property="combustivel" value="G">Gasoline</logic:equal>
                            <logic:equal name="ControleVendasModel" property="combustivel" value="D">Diesel</logic:equal>
                            <logic:equal name="ControleVendasModel" property="combustivel" value="H">Hybrid</logic:equal>
                        </td>
                        <td>
                            <b>Mileage:</b> <bean:write name="ControleVendasModel" property="km"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <b>JCI inspection:</b> <bean:write name="ControleVendasModel" property="shaken"/>
                        </td>
                        <td>
                            <b>Chassis</b> <bean:write name="ControleVendasModel" property="chassi"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <b>Person Capacity:</b> <bean:write name="ControleVendasModel" property="capacidadePessoa"/>
                        </td>
                        <td>
                            <logic:equal name="ControleVendasModel" property="idTipoVeiculo" value="1">
                                <b>Number of Doors</b> <bean:write name="ControleVendasModel" property="nrPortas"/>
                            </logic:equal>
                            <logic:equal name="ControleVendasModel" property="idTipoVeiculo" value="2">
                                <b>Brake</b> <bean:write name="ControleVendasModel" property="freio"/>
                            </logic:equal>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <b>Extra Details</b> <bean:write name="ControleVendasModel" property="detalhesExtras"/>
                        </td>
                    </tr>    
                </table>

                <div align="center" style="padding-top: 50px">
                    <input class="btn btn-default" style="width: 200px" type="button" value="Print out" onClick="fImprimir();">
                </div>
            </html:form>
        </logic:notEqual>    
    </body>
</html>

