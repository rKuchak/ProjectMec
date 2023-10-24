<%-- 
    Document   : tipo_veiculo
    Created on : 3 de abr de 2021, 09:57:10
    Author     : macuser
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Vehicle Type</title>
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">

        <script type="text/javascript">
            function fCadastrar() {
                var tipoVeiculo = document.getElementById("dsTipoVeiculo").value;

                if (tipoVeiculo.length < 3) {
                    alert("The Vehicle Type must be entered correctly!!");
                    document.getElementById("dsTipoVeiculo").focus();
                } else {
                    document.VeiculoModel.action = "TipoVeiculo.do?action=save";
                    document.VeiculoModel.submit();
                }

            }

            function fExcluir(idTipoVeiculo) {
                if (confirm("Do you really want to delete???")) {
                    document.VeiculoModel.action = "TipoVeiculo.do?action=excluir&idTipoVeiculo=" + idTipoVeiculo;
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
                <h1>Vehicle Type Manager</h1>
                <hr/>
            </div>
            <html:form action="TipoVeiculo">
                <table width="60%" border="0" align="center" class="table-condensed">
                    <tr>
                        <td width="90%">
                            <div class="col-lg-12">
                                Vehicle Type:
                                <html:text styleClass="form-control" name="VeiculoModel" property="dsTipoVeiculo" styleId="dsTipoVeiculo"/>        
                            </div>
                        </td>
                        <td>
                            <br/>
                            <input class="btn btn-success" type="button" value="Register" onClick="fCadastrar();">
                        </td>
                    </tr>
                </table>
            </html:form>

            <div align="center" style="margin-top: 150px">
                <h1>Registered Vehicle Type List</h1>
            </div>

            <logic:present name="listaTipoVeiculos" scope="request">
                <table align="center" class="table">
                    <tr style="background-color: #F4F4F4">
                        <td width='10%'>&nbsp;</td>
                        <td>
                            <b>ID</b>
                        </td>
                        <td>
                            <b>Vehicle Type</b>
                        </td>
                        <td width='10%'>&nbsp;</td>
                        <td width='10%'>&nbsp;</td>
                    </tr>
                    <logic:iterate name="listaTipoVeiculos" id="listaTipo" scope="request">
                        <tr>
                            <td width='10%'>&nbsp;</td>
                            <td>
                                <bean:write name="listaTipo" property="idTipoVeiculo"/>
                            </td>
                            <td>
                                <bean:write name="listaTipo" property="dsTipoVeiculo"/>
                            </td>
                            <td width='10%'>
                                <input type="button" class="btn btn-danger" value="Delete" onclick="fExcluir(<bean:write name="listaTipo" property="idTipoVeiculo"/>)"/>
                            </td>
                            <td width='10%'>&nbsp;</td>
                        </tr>
                    </logic:iterate>
                </table>
            </logic:present>

        </logic:notEqual>    
    </body>
</html>
