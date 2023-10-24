/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.model;

import java.sql.Connection;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

/**
 *
 * @author macuser
 */
public class FormBasico extends ValidatorForm {

    protected String acao = "";
    protected Connection conexao = null;
    protected Statement st = null;

    public FormBasico() {
    }

    public Connection getConexao() {
        return conexao;
    }

    public void setConexao(Connection aConexao) {
        conexao = aConexao;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = null;
        //NOVO: verificacao: se acao dif de null e de vazio
        //**if ( acao != null && !acao.equals("") && !acao.equalsIgnoreCase(Acoes.NovaInclusao) && !acao.equalsIgnoreCase(Acoes.CarregarAlteracao) && !acao.equalsIgnoreCase(Acoes.CarregarConsulta))
        if (acao != null && !acao.equals("")) {
            errors = super.validate(mapping, request);
            if (errors == null) {
                errors = new ActionErrors();
            }
            if (errors.isEmpty()) {
                return null;
            }
        }
        return errors;
    }

}
