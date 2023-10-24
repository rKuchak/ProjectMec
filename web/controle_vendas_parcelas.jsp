<%-- 
    Document   : controle_vendas_parcelas
    Created on : 31 de jul de 2021, 10:50:01
    Author     : macuser
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Vehicle Sales Control</title>
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">
        <script language="javascript" src="js/mizanscene.js"></script>

        <script type="text/javascript">

            function fAtualizar(idControleVenda, i) {
                var dataPgto = document.getElementById("dataPagamentoRealizado" + i).value;
                var valorParcela = document.getElementById("valorParcela" + i).value;

                if (dataPgto.length < 1 || valorParcela.length < 1) {
                    alert("The payment date and installment amount must be entered!!");
                } else {
                    if (confirm("Do you really want to update this data?")) {
                        document.ControleVendasModel.action = "ControleVendas.do?action=atualizarPacela&idControleVenda=" + idControleVenda+"&dataPagamentoRealizado="+dataPgto+"&valorParcelaPaga="+valorParcela;
                        document.ControleVendasModel.submit();
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
                <h1>List of Installments of Vehicles Sold</h1>
                <hr/>
            </div>
            <html:form action="ControleVendas">
                <div style="border: 0px solid black; padding: 20px; background-color: #FAFAD2;">
                    <table width="60%" border="0" align="center" class="table-condensed">
                        <tr>
                            <td width="25%">
                                <b>ID:</b> <bean:write name="ControleVendasModel" property="idVendaVeiculo"/>
                                <html:hidden name="ControleVendasModel" property="idVendaVeiculo"/>
                            </td>
                            <td width="25%">
                                <b>Date of Sale:</b> <bean:write name="ControleVendasModel" property="dataVenda"/>
                            </td>
                            <td width="25%">
                            </td>
                        </tr>
                        <tr>
                            <td width="25%">
                                <b>Vehicle Type:</b> <bean:write name="ControleVendasModel" property="dsTipoVeiculo"/>
                            </td>
                            <td width="25%">
                                <b>Vehicle Brand:</b> <bean:write name="ControleVendasModel" property="dsMarcaVeiculo"/>
                            </td>
                            <td width="25%">
                                <b>Vehicle Name:</b> <bean:write name="ControleVendasModel" property="dsVeiculo"/>
                            </td>

                        </tr>
                        <tr>
                            <td width="25%">
                                <b>Vehicle Year:</b> <bean:write name="ControleVendasModel" property="ano"/>
                            </td>
                            <td width="25%">
                                <b>Qty. Installments:</b> <bean:write name="ControleVendasModel" property="qtdParcelas"/>
                            </td>
                            <td width="25%">
                                <b>Payment Day:</b> <bean:write name="ControleVendasModel" property="diaPagamentoPrestacao"/>
                            </td>
                        </tr>
                        <tr>
                            <td width="25%">
                                <b>Sale Price:</b> ¥<bean:write name="ControleVendasModel" property="precoVenda"/>
                            </td>
                            <td width="25%">
                                <b>Starting amount:</b> ¥<bean:write name="ControleVendasModel" property="valorEntrada"/>
                            </td>
                            <td width="25%" style="color: red">
                                <b>Remaining Value: ¥<bean:write name="ControleVendasModel" property="valorRestante"/></b>
                                <html:hidden name="ControleVendasModel" property="valorRestante"/>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="3">
                                <b>Observation:</b> <bean:write name="ControleVendasModel" property="detalhesExtras"/>
                            </td>
                        </tr>

                    </table>
                </div>
                <br/>

                <logic:present name="listaParcelas" scope="request">
                    <%int i = 1;%>
                    <logic:iterate id="lista" name="listaParcelas" scope="request">
                        <div style="border: 0px solid black; padding: 20px; background-color: #F4F4F4;">
                            <table width="60%" border="0" align="center" class="table-condensed">
                                <tr>
                                    <td colspan="4" style="color: blue">
                                        <b>Installment <%=i%>: </b>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="5%">
                                        <b>ID:</b> <bean:write name="lista" property="idControleVenda"/>
                                    </td>

                                    <td width="25%">
                                        <b>Payment Date:</b> <bean:write name="lista" property="dataPagamento"/>
                                    </td>
                                    <td width="25%">
                                        <b>Installment Value:</b> <bean:write name="lista" property="valorParcela"/>
                                    </td>
                                    <td width="25%">
                                        <b>Status:</b> 
                                        <logic:equal name="lista" property="status" value="0">Pending</logic:equal>
                                        <logic:equal name="lista" property="status" value="1">Paid</logic:equal>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td></td>
                                        <td>
                                            <b>Payment Date Made:</b>
                                        <logic:equal name="lista" property="status" value="0">
                                            <input type="date" class="form-control" id="dataPagamentoRealizado<%=i%>" name="dataPagamentoRealizado"/>
                                        </logic:equal>
                                        <logic:equal name="lista" property="status" value="1">
                                            <html:text name="lista" property="dataPagamentoRealizado" styleClass="form-control" readonly="true"/>
                                        </logic:equal>
                                    </td>
                                    <td>
                                        <b>Amount paid:</b> <br/>
                                        <logic:equal name="lista" property="status" value="0">
                                            <input type="text" name="valorParcela" value="" onkeypress="return(validarConteudo(event, 'numero'));" id="valorParcela<%=i%>" class="form-control">
                                        </logic:equal>
                                        <logic:equal name="lista" property="status" value="1">
                                            <html:text name="lista" property="valorParcelaPaga" styleId="valorParcelaPaga" styleClass="form-control" onkeypress="return(validarConteudo(event, 'numero'));" readonly="true"/>
                                        </logic:equal>
                                    </td>    
                                    <td>
                                        <br/>
                                        <logic:equal name="lista" property="status" value="0">
                                            <input class="btn btn-success" style="width: 200px" type="button" value="Update" onClick="fAtualizar(<bean:write name="lista" property="idControleVenda"/>, <%=i%>);">
                                        </logic:equal>
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <br/>    
                        <% i++;%>
                    </logic:iterate>

                </logic:present>


            </html:form>

        </logic:notEqual>    
    </body>
</html>



