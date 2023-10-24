<%-- 
    Document   : pessoa_fisica_visualizar
    Created on : 17 de abr de 2021, 09:03:17
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

        <html:form action="PessoaFisica">
            <div align="center">
                <img src="imagens/logo_mitsistemas_nova_preta.png"/>
                <h1><bean:write name="PessoaFisicaModel" property="nome"/></h1>
                <hr/>
            </div>
            <bean:define name="PessoaFisicaModel" property="endereco" id="EnderecoModel"/>
            <bean:define name="PessoaFisicaModel" property="telefone" id="TelefoneModel"/>
            <html:hidden name="PessoaFisicaModel" property="id"/>
            <table width="50%" border="0" align="center" class="table-condensed">
                <tr>
                    <td width="40%" align="right"><b>E-mail:</b></td>
                    <td>
                        <bean:write name="PessoaFisicaModel" property="email"/>     
                    </td>
                </tr>
                <tr>
                    <td align="right"><b>Date of birth:</b></td>
                    <td>
                        <bean:write name="PessoaFisicaModel" property="dataNascimento"/>     
                    </td>
                </tr>
                <tr>
                    <td align="right"><b>Gender:</b></td>
                    <td>
                        <logic:equal name="PessoaFisicaModel" property="sexo" value="M">Male</logic:equal>
                        <logic:equal name="PessoaFisicaModel" property="sexo" value="F">Female</logic:equal>
                    </td>
                </tr>
                <tr>
                    <td align="right"><b>My Number:</b></td>
                    <td>
                        <bean:write name="PessoaFisicaModel" property="myNumber"/>     
                    </td>
                </tr>
                <tr>
                    <td align="right"><b>Zip:</b></td>
                    <td>
                        <bean:write name="EnderecoModel" property="cep"/>     
                    </td>
                </tr>
                <tr>
                    <td align="right"><b>Province:</b></td>
                    <td>
                        <bean:write name="EnderecoModel" property="provincia"/>     
                    </td>
                </tr>
                <tr>
                    <td align="right"><b>City:</b></td>
                    <td>
                        <bean:write name="EnderecoModel" property="cidade"/>     
                    </td>
                </tr>
                <tr>
                    <td align="right"><b>Address:</b></td>
                    <td>
                        <bean:write name="EnderecoModel" property="dsEndereco"/>     
                    </td>
                </tr>
                <tr>
                    <td align="right"><b>Neighborhood:</b></td>
                    <td>
                        <bean:write name="EnderecoModel" property="bairro"/>     
                    </td>
                </tr>
                <tr>
                    <td align="right"><b>Telephone:</b></td>
                    <td>
                        <bean:write name="TelefoneModel" property="nrTelefone"/> (<bean:write name="TelefoneModel" property="tipoTelefone"/>)     
                    </td>
                </tr>
                <tr>
                    <td align="right"><b>Registration date:</b></td>
                    <td>
                        <bean:write name="PessoaFisicaModel" property="dataCadastro"/>     
                    </td>
                </tr>
                <tr><td colspan="2">&nbsp;</td></tr>
                <tr><td colspan="2">&nbsp;</td></tr>
                <tr><td colspan="2">&nbsp;</td></tr>
                <tr>
                    <td colspan="2" align="center">
                        <input class="btn btn-default" id="botao" type="button" value="Print" onClick="fImprimir();">
                    </td>
                </tr>
                
            </table>
            
        </html:form>
    </body>
</html>
