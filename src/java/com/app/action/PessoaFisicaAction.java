/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.action;

import com.app.dao.PessoaFisicaDAO;
import com.app.model.EnderecoModel;
import com.app.model.PessoaFisicaModel;
import com.app.model.TelefoneModel;
import com.app.util.Errors;
import com.app.util.Utilitario;
import java.sql.Connection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author macuser
 */
public class PessoaFisicaAction extends IDRAction {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, Errors errors, String action) {
        String forward = action;
        if (action.equals("page") || action.equals("pagePesquisa")) {
            this.page(form, request, errors);
        } else if (action.equals("save")) {
            this.save(form, request, errors);
        } else if (action.equals("pesquisar")) {
            this.pesquisar(form, request, errors);
        } else if (action.equals("pageEditar")) {
            this.pageEditar(form, request, errors);
        } else if (action.equals("atualizar")) {
            this.atualizar(form, request, errors);
        } else if (action.equals("excluir")) {
            this.excluir(form, request, errors);
        } else if (action.equals("visualizar")) {
            this.visualizar(form, request, errors);
        }

        return mapping.findForward(forward);
    }

    private void page(ActionForm form, HttpServletRequest request, Errors errors) {
        PessoaFisicaModel pessoaFisicaModel = new PessoaFisicaModel();
        EnderecoModel endereco = new EnderecoModel();
        TelefoneModel telefone = new TelefoneModel();
        pessoaFisicaModel.setEndereco(endereco);
        pessoaFisicaModel.setTelefone(telefone);

        request.setAttribute("PessoaFisicaModel", pessoaFisicaModel);
    }

    private void save(ActionForm form, HttpServletRequest request, Errors errors) {
        PessoaFisicaModel pessoaFisicaModel = (PessoaFisicaModel) form;

        //setar endereco no form
        EnderecoModel endereco = new EnderecoModel();

        String provincia = request.getParameter("provincia");
        String cidade = request.getParameter("cidade");
        String bairro = request.getParameter("bairro");
        String dsEndereco = request.getParameter("dsEndereco");
        String cep = request.getParameter("cep");

        endereco.setProvincia(provincia);
        endereco.setCidade(cidade);
        endereco.setBairro(bairro);
        endereco.setDsEndereco(dsEndereco);
        endereco.setCep(cep);

        pessoaFisicaModel.setEndereco(endereco);

        //setar telefone no form
        TelefoneModel telefone = new TelefoneModel();

        String nrTelefone = request.getParameter("nrTelefone");
        String tipoTelefone = request.getParameter("tipoTelefone");
        
        String telefoneFormatado = Utilitario.getInstance().telefoneFormatado(nrTelefone, tipoTelefone);

        telefone.setNrTelefone(telefoneFormatado);
        telefone.setTipoTelefone(tipoTelefone);

        pessoaFisicaModel.setTelefone(telefone);

        Connection conn = null;
        try {
            conn = connectionPool.getConnection();

            //verificar se ja existe email cadastrado
            //se existir nao permite cadastrar novo usuario
            boolean isExisteEmail = PessoaFisicaDAO.getInstance().isExisteEmail(conn, pessoaFisicaModel.getEmail());

            if (isExisteEmail) {
                errors.error("This email is already registered in our database. Please inform another");
            } else {
                //salvar dados da pessoa e retornar o ID
                int idPessoa = PessoaFisicaDAO.getInstance().savePessoa(conn, pessoaFisicaModel);

                if (idPessoa != 0) {
                    //salvar os dados de endereco pelo idPessoa
                    PessoaFisicaDAO.getInstance().saveAddress(conn, idPessoa, endereco);

                    //salvar os dados de telefone pelo idPessoa
                    PessoaFisicaDAO.getInstance().savePhone(conn, idPessoa, telefone);

                    errors.error("Successful Registration!!");

                    this.page(form, request, errors);
                } else {
                    errors.error("An error occurred in the system!!Please contact support!");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.free(conn);
        }

    }

    private void pesquisar(ActionForm form, HttpServletRequest request, Errors errors) {
        PessoaFisicaModel pessoaFisicaModel = (PessoaFisicaModel) form;
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            //pesquisar a pessoa ou por nome, email ou sexo
            List<PessoaFisicaModel> listaPessoaFisica = PessoaFisicaDAO.getInstance().searchAll(conn, pessoaFisicaModel);
            if (listaPessoaFisica.size() > 0) {
                request.setAttribute("listaPessoaFisica", listaPessoaFisica);
                request.setAttribute("show", "true");
            } else {
                request.setAttribute("show", "false");
            }
            request.setAttribute("PessoaFisicaModel", pessoaFisicaModel);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.free(conn);
        }

    }

    private void pageEditar(ActionForm form, HttpServletRequest request, Errors errors) {
        PessoaFisicaModel pessoaFisicaModel = new PessoaFisicaModel();
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();

            String idPessoa = request.getParameter("idPessoa");

            pessoaFisicaModel = PessoaFisicaDAO.getInstance().obterDadosPessoaPorId(conn, idPessoa);

            request.setAttribute("PessoaFisicaModel", pessoaFisicaModel);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.free(conn);
        }

    }

    private void atualizar(ActionForm form, HttpServletRequest request, Errors errors) {
        PessoaFisicaModel pessoaFisicaModel = (PessoaFisicaModel) form;

        //setar endereco no form
        EnderecoModel endereco = new EnderecoModel();

        String provincia = request.getParameter("provincia");
        String cidade = request.getParameter("cidade");
        String bairro = request.getParameter("bairro");
        String dsEndereco = request.getParameter("dsEndereco");
        String cep = request.getParameter("cep");

        endereco.setProvincia(provincia);
        endereco.setCidade(cidade);
        endereco.setBairro(bairro);
        endereco.setDsEndereco(dsEndereco);
        endereco.setCep(cep);

        pessoaFisicaModel.setEndereco(endereco);

        //setar telefone no form
        TelefoneModel telefone = new TelefoneModel();

        String nrTelefone = request.getParameter("nrTelefone");
        String tipoTelefone = request.getParameter("tipoTelefone");

        telefone.setNrTelefone(nrTelefone);
        telefone.setTipoTelefone(tipoTelefone);

        pessoaFisicaModel.setTelefone(telefone);

        Connection conn = null;
        try {
            conn = connectionPool.getConnection();

            //verificar se ja existe email cadastrado para outro usuario
            //se existir nao permite cadastrar novo usuario
            boolean isExisteEmail = PessoaFisicaDAO.getInstance().isExisteEmailOutroUsuario(conn, pessoaFisicaModel.getEmail(), pessoaFisicaModel.getId());

            if (isExisteEmail) {
                errors.error("This email is already registered in our database. Please inform another.");
            } else {
                //update dados pesssoa
                PessoaFisicaDAO.getInstance().updatePessoa(conn, pessoaFisicaModel);

                //update dados de endereco pelo idPessoa
                PessoaFisicaDAO.getInstance().updateAddress(conn, pessoaFisicaModel.getId(), endereco);

                //update dados de telefone pelo idPessoa
                PessoaFisicaDAO.getInstance().updatePhone(conn, pessoaFisicaModel.getId(), telefone);

                errors.error("Updated Successfully!!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.free(conn);
        }
    }

    private void excluir(ActionForm form, HttpServletRequest request, Errors errors) {
        PessoaFisicaModel pessoaFisicaModel = (PessoaFisicaModel) form;
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            String idPessoa = request.getParameter("idPessoa");

            //verificar se esse idPessoa, ja esta vinculado em alguma funcionalidade no sistema
            //ou seja, se ele ja teve shaken ou venda de veiculo realizada
            boolean isExisteVinculo = PessoaFisicaDAO.getInstance().isExisteVinculo(conn, idPessoa);
            if (isExisteVinculo) {
                errors.error("It cannot be excluded, as this person is linked to some functionality of the system!!");
            } else {
                //excluir pessoa por idPessoa
                PessoaFisicaDAO.getInstance().deletePessoa(conn, idPessoa);

                //excluir telefone
                PessoaFisicaDAO.getInstance().deletePhone(conn, idPessoa);
                
                //excluir endereco
                PessoaFisicaDAO.getInstance().deleteAddress(conn, idPessoa);
                errors.error("Person Deleted Successfully!!");

            }
            this.pesquisar(pessoaFisicaModel, request, errors);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.free(conn);
        }
    }

    private void visualizar(ActionForm form, HttpServletRequest request, Errors errors) {
        PessoaFisicaModel pessoaFisicaModel = new PessoaFisicaModel();
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();

            String idPessoa = request.getParameter("idPessoa");

            pessoaFisicaModel = PessoaFisicaDAO.getInstance().obterDadosPessoaPorId(conn, idPessoa);

            request.setAttribute("PessoaFisicaModel", pessoaFisicaModel);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.free(conn);
        }
    }

}
