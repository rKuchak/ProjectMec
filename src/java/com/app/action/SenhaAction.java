/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.action;

import com.app.dao.PessoaFisicaDAO;
import com.app.dao.SenhaDAO;
import com.app.model.SenhaModel;
import com.app.util.Criptografia;
import com.app.util.Errors;
import java.sql.Connection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author macuser
 */
public class SenhaAction extends IDRAction {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, Errors errors, String action) {
        String forward = action;
        if (action.equals("pageAlterarSenha")) {
            this.pageAlterarSenha(form, request, errors);
        } else if (action.equals("alterarSenha")){
            this.alterarSenha(form, request, errors);
        }

        return mapping.findForward(forward);
    }

    private void pageAlterarSenha(ActionForm form, HttpServletRequest request, Errors errors) {
        SenhaModel senhaModel = new SenhaModel();
        Connection conn = null;
        HttpSession session = request.getSession();
        try {
            conn = connectionPool.getConnection();
            Object idPessoaSession = session.getAttribute("idPessoa");
            if(idPessoaSession != null){
                int idPessoa = ((Integer) idPessoaSession);
                String nomeUsuario = PessoaFisicaDAO.getInstance().obterNomePessoaPorId(conn, idPessoa);
                senhaModel.setNomePessoa(nomeUsuario);
                senhaModel.setIdPessoa(idPessoa);
            } else {
                errors.error("Your session has expired!! Please log in again");
            }
            
            request.setAttribute("SenhaModel", senhaModel);
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.free(conn);
        }
       
        
        
        
    }

    private void alterarSenha(ActionForm form, HttpServletRequest request, Errors errors) {
        SenhaModel senhaModel = (SenhaModel) form;
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            
            //verificar se a senha Atual informada realmente esta correta
            Criptografia criptografia = new Criptografia();
            String senhaAtualCripto = criptografia.senhaCriptografada(senhaModel.getSenhaAtual());
            boolean isSenhaAtualOk = SenhaDAO.getInstance().verificarSenhaAtual(conn, senhaModel.getIdPessoa(), senhaAtualCripto);
            
            //se ok, atualiza no banco criptografado
            if(isSenhaAtualOk) {
                String senhaNovaCripto = criptografia.senhaCriptografada(senhaModel.getSenhaNova());
                SenhaDAO.getInstance().alterarSenhaPorId(conn, senhaModel.getIdPessoa(), senhaNovaCripto);
                errors.error("Password changed successfully!!");
                request.setAttribute("senhaAlterada", "true");
            } else {
            //se errado, da mensagem de erro
                errors.error("Incorrect Current Password!!");
                request.setAttribute("senhaAlterada", "false");
            }
            
            senhaModel.setSenhaAtual(null);
            senhaModel.setSenhaNova(null);
            
            request.setAttribute("SenhaModel", senhaModel);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.free(conn);
        }
    }
    
}
