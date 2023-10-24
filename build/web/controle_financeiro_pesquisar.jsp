<%-- 
    Document   : controle_financeiro_pesquisar
    Created on : 9 de out de 2021, 10:33:26
    Author     : macuser
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<!DOCTYPE html>
<html lang="pt-br" class="js-disabled">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">
        <link rel="stylesheet" media="all" type="text/css" href="css/estilo.css" />
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/3.5.1/chart.min.js"></script>

        <style>
            body {
                margin:0;
                padding:0;
            }

        </style>
        <script type="text/javascript">
            function fVisualizar() {
            var ano = document.getElementById("ano").value;
            if (ano.length < 4) {
            alert("The year must be informed correctly!!");
            } else {
            document.ControleFinanceiroModel.action = "ControleFinanceiro.do?action=pesquisar";
            document.ControleFinanceiroModel.target = "_self";
            document.ControleFinanceiroModel.submit();
            }
            }

            function fExcluir(id) {
            if (confirm("Do you really want to delete?")) {
            document.ControleFinanceiroModel.action = "ControleFinanceiro.do?action=excluirPorMes&id=" + id;
            document.ControleFinanceiroModel.target = "_self";
            document.ControleFinanceiroModel.submit();
            }
            }

            function fPaginacao() {
            document.ControleFinanceiroModel.action = "ControleFinanceiro.do?action=paginacao";
            document.ControleFinanceiroModel.target = "_blank";
            document.ControleFinanceiroModel.submit();
            }
        </script>


    </head>
    <body>

        <logic:notEqual name="nome" value="" scope="session">
            <jsp:include page="topo.jsp"/>
            <div align="center">
                <h1>Search Financial Control</h1>
                <hr/>
            </div>

            <html:form action="ControleFinanceiro">
                <table width="40%" border="0" align="center" class="table-condensed">
                    <tr>
                        <td width="55%">
                            <label>Month</label>
                            <html:select name="ControleFinanceiroModel" property="mes" styleId="mes" styleClass="form-control">
                                <html:option value="1">January</html:option>
                                <html:option value="2">February</html:option>
                                <html:option value="3">March</html:option>
                                <html:option value="4">April</html:option>
                                <html:option value="5">May</html:option>
                                <html:option value="6">June</html:option>
                                <html:option value="7">July</html:option>
                                <html:option value="8">August</html:option>
                                <html:option value="9">September</html:option>
                                <html:option value="10">October</html:option>
                                <html:option value="11">November</html:option>
                                <html:option value="12">December</html:option>
                            </html:select>
                        </td>
                        <td width="15%">
                            <label>Year</label>
                            <html:text name="ControleFinanceiroModel" property="ano" styleId="ano" styleClass="form-control"/>
                        </td>
                        <td width="10%">
                            <br/>
                            <a href="javascript:fVisualizar()" class="btn btn-success">View</a>
                        </td>
                        <td width="15%">
                            <br/>
                            <a href="javascript:fPaginacao()" class="btn btn-primary">List w/ Pagination</a>
                        </td>
                    </tr>
                </table>
                <hr/>
                <logic:equal name="result" scope="request" value="true">
                    <table width="60%" border="0" align="center" class="table-condensed">

                        <logic:present name="listaControleFinanceiro" scope="request">
                            <tr>
                                <td><label>ID</label></td>
                                <td><label>Type</label></td>
                                <td><label>Reference Date</label></td>
                                <td><label>Value</label></td>
                                <td><label>Description</label></td>
                                <td>&nbsp;</td>
                            </tr>
                            <logic:iterate name="listaControleFinanceiro" id="lista" scope="request">
                                <tr>
                                    <td>
                                        <bean:write name="lista" property="id"/>
                                    </td>
                                    <td>
                                        <bean:write name="lista" property="tipo"/>
                                    </td>
                                    <td>
                                        <bean:write name="lista" property="dataReferencia"/>
                                    </td>
                                    <td>
                                        ¥<bean:write name="lista" property="valor"/>
                                    </td>
                                    <td>
                                        <bean:write name="lista" property="descricao"/>
                                    </td>
                                    <td>
                                        <a href="javascript:fExcluir(<bean:write name="lista" property="id"/>)" class="btn btn-danger">Delete</a>
                                    </td>
                                </tr>
                            </logic:iterate>
                            <tr>
                                <td colspan="5">&nbsp;</td>
                            </tr>
                            <tr>
                                <td colspan="5">
                                    <b>Total Entry Value: <bean:write name="ControleFinanceiroModel" property="vlTotalEntrada"/></b>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="5">
                                    <b>Total Exit Value: <bean:write name="ControleFinanceiroModel" property="vlTotalSaida"/></b>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="5">
                                    <b>Total Net Value: <bean:write name="ControleFinanceiroModel" property="vlTotalLiquido"/></b>
                                </td>
                            </tr>
                        </logic:present>
                        <logic:notPresent name="listaControleFinanceiro" scope="request">
                            <tr>
                                <td colspan="5" align="center">
                                    <div class="alert alert-danger">
                                        No records registered for the month
                                    </div>

                                </td>
                            </tr>
                        </logic:notPresent>
                    </table>

                    <table border="0" width="80%" align="center">
                        <tr>
                            <td>
                                <canvas class="line-chart"></canvas>
                            </td>
                        </tr>
                    </table>
                    <script>
                        var ctx = document.getElementsByClassName("line-chart");
                        var chartGraph = new Chart(ctx, {
                        type: "bar",
                                data: {
                                labels: ["Final result"],
                                        datasets: [{
                                        label: "Entry",
                                                data: [<bean:write name="ControleFinanceiroModel" property="vlTotalEntradaGrafico"/>],
                                                borderWidth: 1,
                                                backgroundColor: "#7CFC00"
                                        }, {
                                        label: "Exit",
                                                data: [<bean:write name="ControleFinanceiroModel" property="vlTotalSaidaGrafico"/>],
                                                borderWidth: 1,
                                                backgroundColor: "#FFA500"
                                        }, {
                                        label: "Liquid",
                                                data: [<bean:write name="ControleFinanceiroModel" property="vlTotalLiquidoGrafico"/>],
                                                borderWidth: 1,
                        <logic:greaterEqual name="ControleFinanceiroModel" property="vlTotalLiquidoGrafico" value="0">
                                        backgroundColor: "#00BFFF"
                        </logic:greaterEqual>
                        <logic:lessThan name="ControleFinanceiroModel" property="vlTotalLiquidoGrafico" value="0">
                                        backgroundColor: "#FF0000"
                        </logic:lessThan>

                                        }
                                        ]
                                },
                                options: {
                                scales: {
                                yAxes: [{
                                display: true,
                                        ticks: {
                                        beginAtZero: true,
                        <logic:greaterEqual name="ControleFinanceiroModel" property="vlTotalLiquidoGrafico" value="0">
                                        min: 0
                        </logic:greaterEqual>
                        <logic:lessThan name="ControleFinanceiroModel" property="vlTotalLiquidoGrafico" value="0">
                                        min: <bean:write name="ControleFinanceiroModel" property="vlTotalLiquidoGrafico"/>
                        </logic:lessThan>

                                        }
                                }]
                                }
                                }
                        });


                    </script>
                </logic:equal>
            </html:form>

        </logic:notEqual>
        <logic:equal name="nome" value="" scope="session">
            <div style="padding-top: 20%">
                <div class="alert alert-danger" align="center">
                    <img src="imagens/cancel.png" width="50px"/>
                    <br/>
                    <br/>
                    You are not logged into the system!!
                    <br/>
                    <br/>
                    <a href="index.jsp" class="btn btn-default">Click here to return to the login page</a>
                </div>
            </div>
        </logic:equal>
    </body>
</html>


