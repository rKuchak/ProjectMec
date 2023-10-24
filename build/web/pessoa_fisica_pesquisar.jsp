<%-- 
    Document   : pessoa_fisica_pesquisar
    Created on : 13/fev/2021, 11:01:19
    Author     : macuser
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<!DOCTYPE html>
<html>
    <head>
        <title>JSP Page</title>
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">

        <script type="text/javascript">
            function fPesquisar() {
                var nome = document.getElementById("nome").value;
                var email = document.getElementById("email").value;
                var sexo = document.getElementById("sexo").value;
                if (nome.length < 3 && email.search('@') < 0 && sexo === "") {
                    alert("One of the fields must be filled in!!")
                } else {
                    document.PessoaFisicaModel.action = "PessoaFisica.do?action=pesquisar";
                    document.PessoaFisicaModel.target = "_self";
                    document.PessoaFisicaModel.submit();
                }
            }

            function fEditar(idPessoa) {
                document.PessoaFisicaModel.action = "PessoaFisica.do?action=pageEditar&idPessoa=" + idPessoa;
                document.PessoaFisicaModel.target = "_self";
                document.PessoaFisicaModel.submit();
            }

            function fVisualizar(idPessoa) {
//                window.open("PessoaFisica.do?action=visualizar&idPessoa=" + idPessoa, "VisualizarCadastro", 400, 500);

                document.PessoaFisicaModel.action = "PessoaFisica.do?action=visualizar&idPessoa=" + idPessoa;
                document.PessoaFisicaModel.target = "_blank";
                document.PessoaFisicaModel.submit();
            }

            function fExcluir(idPessoa) {
                if (confirm("Do you really want to delete???")) {
                    document.PessoaFisicaModel.action = "PessoaFisica.do?action=excluir&idPessoa=" + idPessoa;
                    document.PessoaFisicaModel.target = "_self";
                    document.PessoaFisicaModel.submit();
                }
            }
        </script>

    </head>
    <body>
        <logic:notEqual name="nome" value="" scope="session">

            <logic:present name="errors">
                <script>
                    alert('<bean:write name="errors"/>');
                </script>
            </logic:present>
            <jsp:include page="topo.jsp"/>

            <div align="center">
                <h1>Person Search</h1>
                <hr/>
            </div>
            <html:form action="PessoaFisica">
                <table width="80%" border="0" align="center" class="table-condensed">
                    <tr>
                        <td width="25%">
                            <div class="col-lg-12">
                                Name:
                                <html:text styleClass="form-control" name="PessoaFisicaModel" property="nome" styleId="nome"/>        
                            </div>
                        </td>
                        <td width="25%" colspan="2">
                            <div class="col-lg-12">
                                E-mail:
                                <html:text styleClass="form-control" name="PessoaFisicaModel" property="email" styleId="email"/>        
                            </div>
                        </td>
                        <td width="25%">
                            <div class="col-lg-12">
                                Gender:
                                <html:select styleClass="form-control" name="PessoaFisicaModel" property="sexo" styleId="sexo">
                                    <html:option value="">Select</html:option>
                                    <html:option value="M">Male</html:option>
                                    <html:option value="F">Female</html:option>
                                </html:select>
                            </div>
                        </td>
                        <td width="25%">
                            <div class="col-lg-12">
                                &nbsp; <br/>
                                <input class="btn btn-success" type="button" value="Search" onClick="fPesquisar();">
                            </div>
                        </td>

                    </tr>
                </table>

                <logic:present name="listaPessoaFisica" scope="request">

                    <table align="center" class="table">
                        <tr style="background-color: #F4F4F4">
                            <td><b>ID</b></td>
                            <td><b>Name</b></td>
                            <td><b>E-mail</b></td>
                            <td><b>Gender</b></td>
                            <td><b>Date of birth</b></td>
                            <td><b>My Number</b></td>
                            <td>&nbsp;</td>
                        </tr>

                        <logic:iterate name="listaPessoaFisica" scope="request" id="listaP">
                            <tr>
                                <td><bean:write name="listaP" property="id"/></td>
                                <td><bean:write name="listaP" property="nome"/></td>
                                <td><bean:write name="listaP" property="email"/></td>
                                <td><bean:write name="listaP" property="sexo"/></td>
                                <td><bean:write name="listaP" property="dataNascimento"/></td>
                                <td><bean:write name="listaP" property="myNumber"/></td>
                                <td>
                                    <input type="button" class="btn btn-success" value="View" onclick="fVisualizar(<bean:write name="listaP" property="id"/>)"/>
                                    <input type="button" class="btn btn-info" value="Edit" onclick="fEditar(<bean:write name="listaP" property="id"/>)"/>
                                    <input type="button" class="btn btn-danger" value="Delete" onclick="fExcluir(<bean:write name="listaP" property="id"/>)"/>
                                </td>
                            </tr>
                        </logic:iterate>

                    </table>

                </logic:present>
                <logic:equal name="show" value="false" scope="request">
                    <div class="alert alert-danger" align="center">
                        Data not found
                    </div>
                </logic:equal>

            </html:form>
        </logic:notEqual>
    </body>
</html>
