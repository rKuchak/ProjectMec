<%-- 
    Document   : tela_inicial
    Created on : 6 de mar de 2021, 10:38:56
    Author     : macuser
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<!DOCTYPE html>
<html lang="pt-br" class="js-disabled">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Project Default - Home screen</title>
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">
        <link rel="stylesheet" media="all" type="text/css" href="css/estilo.css" />

        <style>
            body {
                margin:0;
                padding:0;
            }

        </style>
    </head>
    <body>

        <logic:notEqual name="nome" value="" scope="session">
            <jsp:include page="topo.jsp"/>
            <div class="row" align="center" style="padding-top: 6%">
                <div class="col-12 col-s-12">
                    <button class="btn-tela" onclick="window.location.href = 'PessoaFisica.do'">
                        Register <br/>a Person
                    </button>
                    <button class="btn-tela" onclick="window.location.href = 'PessoaFisica.do?action=pagePesquisa'">
                        Search <br/>a Person
                    </button>
                    <button class="btn-tela" onclick="window.location.href = 'MarcaVeiculo.do'">
                        Register <br/>Vehicle Brand
                    </button>
                    <button class="btn-tela" onclick="window.location.href = 'NomeVeiculo.do'">
                        Register <br/>Name Vehicles
                    </button>
                    <button class="btn-tela" onclick="window.location.href = 'controle_financeiro.jsp'">
                        Financial <br/>Register
                    </button>
                    <!--                    <button class="btn-tela" onclick="window.location.href = 'ControleFinanceiro.do?action=pageAdicionar'">
                                            Controle <br/>Financeiro
                                        </button>-->

                </div>
            </div>
            <div class="row" align="center" style="padding-top: 10px">
                <div class="col-12 col-s-12">
                    <button class="btn-tela" onclick="window.location.href = 'ControleVendas.do'">
                        Vehicle <br/>Sales
                    </button>
                    <button class="btn-tela" onclick="window.location.href = 'ControleVendas.do?action=pesquisarVeiculosVenda'">
                        Control of <br/>Vehicles Sold
                    </button>
                    <button class="btn-tela" onclick="window.location.href = 'Shaken.do'">
                        Inspection<br/> Register
                    </button>
                    <button class="btn-tela" onclick="window.location.href = 'Shaken.do?action=pageControle'">
                        Inspection<br/>Performed
                    </button>
                    <button class="btn-tela" onclick="window.location.href = 'gerenciador_relatorios.jsp'">
                        Reports
                    </button>
                </div>
            </div>
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

        <div>
            <%--<jsp:include page="footer.jsp"/>--%>
        </div>
    </body>
</html>

