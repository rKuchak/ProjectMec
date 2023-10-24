<%-- 
    Document   : shaken_editar_pagamento
    Created on : 4 de jul de 2021, 14:34:23
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

            function fAtualizar(idControle, i) {
                var dataPgto = document.getElementById("dataPagamentoRealizado" + i).value;
                var valorParcela = document.getElementById("valorParcela" + i).value;

                if (dataPgto.length < 1 || valorParcela.length < 1) {
                    alert("The payment date and installment amount must be entered!!");
                } else {
                    if (confirm("Do you really want to update this data?")) {
                        document.ShakenModel.action = "Shaken.do?action=atualizarParcelaShaken&idControle=" + idControle+"&dataPagamentoRealizado="+dataPgto+"&valorParcelaPaga="+valorParcela;
                        document.ShakenModel.submit();
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
                <h1>List of Realized Shaken Installments</h1>
                <hr/>
            </div>
            <html:form action="Shaken">
                <div style="border: 0px solid black; padding: 20px; background-color: #FAFAD2;">
                    <table width="60%" border="0" align="center" class="table-condensed">
                        <tr>
                            <td width="25%">
                                <b>ID:</b> <bean:write name="ShakenModel" property="id"/>
                                <html:hidden name="ShakenModel" property="id"/>
                            </td>
                            <td width="25%">
                                <b>Date of Accomplishment:</b> <bean:write name="ShakenModel" property="dataRealizacao"/>
                            </td>
                            <td width="25%">
                                <b>Next Due Date:</b> <bean:write name="ShakenModel" property="dataVencimento"/>
                            </td>
                        </tr>
                        <tr>
                            <td width="25%">
                                <b>Vehicle Type:</b> <bean:write name="ShakenModel" property="dsTipoVeiculo"/>
                            </td>
                            <td width="25%">
                                <b>Vehicle Brand:</b> <bean:write name="ShakenModel" property="dsMarcaVeiculo"/>
                            </td>
                            <td width="25%">
                                <b>Vehicle Name:</b> <bean:write name="ShakenModel" property="nomeVeiculo"/>
                            </td>

                        </tr>
                        <tr>
                            <td width="25%">
                                <b>Vehicle Year:</b> <bean:write name="ShakenModel" property="anoVeiculo"/>
                            </td>
                            <td width="25%">
                                <b>Qty. Installments:</b> <bean:write name="ShakenModel" property="qtdParcelas"/>
                            </td>
                            <td width="25%">
                                <b>Payment Day:</b> <bean:write name="ShakenModel" property="diaPagamentoPrestacao"/>
                            </td>
                        </tr>
                        <tr>
                            <td width="25%">
                                <b>Amount charged:</b> ¥<bean:write name="ShakenModel" property="valorCobrado"/>
                            </td>
                            <td width="25%">
                                <b>Start Value:</b> ¥<bean:write name="ShakenModel" property="valorEntrada"/>
                            </td>
                            <td width="25%" style="color: red">
                                <b>Remaining Value: ¥<bean:write name="ShakenModel" property="valorRestante"/></b>
                                <html:hidden name="ShakenModel" property="valorRestante"/>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="3">
                                <b>Observation:</b> <bean:write name="ShakenModel" property="observacao"/>
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
                                        <b>Installments <%=i%>: </b>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="5%">
                                        <b>ID:</b> <bean:write name="lista" property="idControle"/>
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
                                            <input class="btn btn-success" style="width: 200px" type="button" value="Update" onClick="fAtualizar(<bean:write name="lista" property="idControle"/>, <%=i%>);">
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


