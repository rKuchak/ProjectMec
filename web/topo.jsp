<style>
    .topoHeader {
        left: 0;
        bottom: 0;
        width: 100%;
        background-color: #F4F4F4;
        text-align: center;
    }
</style>

<div class="topoHeader" style="color: black">
    <table width="90%" align="center">
        <tr>
            <td colspan="3">&nbsp;</td>
        </tr>
        <tr>
            <td width="25%">
                <a href="tela_inicial.jsp">
                    <img src="imagens/logo_mitsistemas_nova_preta.png" width="100%"/>
                </a>
            </td>
            <td align="center" width="50%" style="font-size: 11px">
                Welcome to the System, 
                <b> 
                    <%if (session.getAttribute("nome") != null) {%>
                    <%=session.getAttribute("nome").toString()%>
                    <%}%>
                </b>
            </td>
            <td width="25%" align="right">
                <a href="tela_inicial.jsp" class="btn btn-info">Home screen</a>
                <a href="Senha.do?action=pageAlterarSenha" class="btn btn-default">Change Password</a>
                <a href="deslogar.jsp" class="btn btn-danger">Log out</a>
            </td>
        </tr>
        <tr>
            <td colspan="3" style="border-bottom: 0px solid #CCCCCC;">&nbsp;</td>
        </tr>
    </table>
</div>