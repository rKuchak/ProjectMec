/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.action;

import com.app.dao.PessoaFisicaDAO;
import com.app.dao.ShakenDAO;
import com.app.dao.VeiculoDAO;
import com.app.model.PessoaFisicaModel;
import com.app.model.ShakenModel;
import com.app.model.VeiculoModel;
import com.app.util.Errors;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
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
public class ShakenAction extends IDRAction {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, Errors errors, String action) {
        String forward = action;
        if (action.equals("page")) {
            this.page(form, request, errors);
        } else if (action.equals("carregarMarcaVeiculo")) {
            this.carregarMarcaVeiculo(form, request, errors);
        } else if (action.equals("carregarVeiculos")) {
            this.carregarVeiculos(form, request, errors);
        } else if (action.equals("validarVeiculo")) {
            this.validarVeiculo(form, request, errors);
        } else if (action.equals("salvarShaken")) {
            this.salvarShaken(form, request, errors);
        } else if (action.equals("pageControle")) {
            this.pageControle(form, request, errors);
        } else if (action.equals("pesquisarShaken")) {
            this.pesquisarShaken(form, request, errors);
        } else if (action.equals("editarPagamentoShaken")) {
            this.editarPagamentoShaken(form, request, errors);
        } else if (action.equals("atualizarParcelaShaken")) {
            this.atualizarParcelaShaken(form, request, errors);
        } else if (action.equals("excluirShaken")) {
            this.excluirShaken(form, request, errors);
        }

        return mapping.findForward(forward);
    }

    private void page(ActionForm form, HttpServletRequest request, Errors errors) {
        ShakenModel shakenModel = new ShakenModel();
        HttpSession session = request.getSession();
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();

            session.removeAttribute("listaTipoVeiculo");
            session.removeAttribute("listaMarcaVeiculo");
            session.removeAttribute("listaVeiculoPorMarca");

            //obter lista de pessoas cadastradas
            PessoaFisicaModel pessoaFisicaModel = new PessoaFisicaModel();
            List<PessoaFisicaModel> listaPessoaFisica = PessoaFisicaDAO.getInstance().searchAll(conn, pessoaFisicaModel);
            session.setAttribute("listaPessoaFisica", listaPessoaFisica);

            //obter a lista do tipo
            List<VeiculoModel> listaTipoVeiculo = VeiculoDAO.getInstance().obterListaTipoVeiculos(conn);
            session.setAttribute("listaTipoVeiculo", listaTipoVeiculo);

            request.setAttribute("ShakenModel", shakenModel);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.free(conn);
        }
    }

    private void carregarMarcaVeiculo(ActionForm form, HttpServletRequest request, Errors errors) {
        ShakenModel shakenModel = (ShakenModel) form;
        HttpSession session = request.getSession();
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();

            session.removeAttribute("listaVeiculoPorMarca");

            //obter lista das marcas de veiculos ja cadastradas
            List<VeiculoModel> listaMarcaVeiculo = VeiculoDAO.getInstance().obterListaMarcaVeiculoPorTipo(conn, shakenModel.getIdTipoVeiculo());
            session.setAttribute("listaMarcaVeiculo", listaMarcaVeiculo);

            session.setAttribute("ShakenModel", shakenModel);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.free(conn);
        }
    }

    private void carregarVeiculos(ActionForm form, HttpServletRequest request, Errors errors) {
        ShakenModel shakenModel = (ShakenModel) form;
        HttpSession session = request.getSession();
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();

            //obter lista dos veiculos cadastrado por marca
            List<VeiculoModel> listaVeiculoPorMarca = VeiculoDAO.getInstance().obterListaVeiculoPorMarca(conn, shakenModel.getIdMarcaVeiculo());
            session.setAttribute("listaVeiculoPorMarca", listaVeiculoPorMarca);

            session.setAttribute("ShakenModel", shakenModel);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.free(conn);
        }
    }

    private void validarVeiculo(ActionForm form, HttpServletRequest request, Errors errors) {
        ShakenModel shakenModel = (ShakenModel) form;
        HttpSession session = request.getSession();

        request.setAttribute("detalhesVeiculo", "true");
        session.setAttribute("ShakenModel", shakenModel);
    }

    private void salvarShaken(ActionForm form, HttpServletRequest request, Errors errors) {
        ShakenModel shakenModel = (ShakenModel) form;
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();

            int vlEntrada = Integer.parseInt(shakenModel.getValorEntrada().replace(",", "").replace(".", ""));
            int vlCobrado = Integer.parseInt(shakenModel.getValorCobrado().replace(",", "").replace(".", ""));
            int vlRestante = vlCobrado - vlEntrada;
            int vlParcelas = vlRestante / shakenModel.getQtdParcelas();

            boolean isOK = false;

            if (shakenModel.getDiaPagamentoPrestacao() == 0) {
                //pagamento a vista
                //nesse caso, temos que verificar se o vaor de Entrada eh igual ao valor cobrado
                if (vlCobrado <= vlEntrada) {
                    //ok pode salvar no banco
                    isOK = true;
                } else {
                    isOK = false;
                    errors.error("The Entry value cannot be less than the Charged value!!");
                }
            } else {
                //pagamento parcelado, nem que seja 1 parcela
                isOK = true;
            }

            if (isOK) {
                //salva os dados no banco de dados
                int idShaken = ShakenDAO.getInstance().salvarShaken(conn, shakenModel, vlEntrada, vlCobrado, vlRestante);

                //verificar se eu preciso salvar na tabela controle_shaken
                if (shakenModel.getQtdParcelas() > 1) {
                    LocalDate dataAtual = LocalDate.now(ZoneId.systemDefault());
                    for (int i = 1; i <= shakenModel.getQtdParcelas(); i++) {
                        dataAtual = dataAtual.plusMonths(1);

                        if (shakenModel.getDiaPagamentoPrestacao() == 30 && dataAtual.getMonth().getValue() == 2) {
                            dataAtual = dataAtual.withDayOfMonth(28);
                        } else {
                            dataAtual = dataAtual.withDayOfMonth(shakenModel.getDiaPagamentoPrestacao());
                        }

                        String dataFormatada = String.valueOf(dataAtual);
                        ShakenDAO.getInstance().salvarControleShaken(conn, idShaken, shakenModel.getDiaPagamentoPrestacao(), dataFormatada, vlParcelas);
                    }
                }
            }
            errors.error("Inspection registered successfully!!!");
            this.page(form, request, errors);

            request.setAttribute("detalhesVeiculo", "true");
            request.setAttribute("ShakenModel", shakenModel);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.free(conn);
        }
    }

    private void pageControle(ActionForm form, HttpServletRequest request, Errors errors) {
        ShakenModel shakenModel = new ShakenModel();
        HttpSession session = request.getSession();
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();

            //obter lista de pessoas cadastradas com shaken
            List<ShakenModel> listaPessoasComShaken = ShakenDAO.getInstance().obterPessoasComShaken(conn);

            session.setAttribute("listaPessoasComShaken", listaPessoasComShaken);
            request.setAttribute("ShakenModel", shakenModel);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.free(conn);
        }
    }

    private void pesquisarShaken(ActionForm form, HttpServletRequest request, Errors errors) {
        ShakenModel shakenModel = (ShakenModel) form;

        Connection conn = null;
        try {
            conn = connectionPool.getConnection();

            //pesquisar os shakens de uma determinada pessoa
            List<ShakenModel> listaShaken = ShakenDAO.getInstance().obterListaShakenPorPessoa(conn, shakenModel.getIdPessoa());

            request.setAttribute("listaShaken", listaShaken);
            request.setAttribute("ShakenModel", shakenModel);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.free(conn);
        }
    }

    private void editarPagamentoShaken(ActionForm form, HttpServletRequest request, Errors errors) {
        ShakenModel shakenModel = (ShakenModel) form;
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();

            //obter dados do shaken por ID shaken
            shakenModel = ShakenDAO.getInstance().obterDadosShakenPorID(conn, shakenModel.getId());

            //pesquisar os shakens para pagamento por ID shaken
            List<ShakenModel> listaParcelas = ShakenDAO.getInstance().obterListaParcelasPorID(conn, shakenModel.getId());

            request.setAttribute("listaParcelas", listaParcelas);
            request.setAttribute("ShakenModel", shakenModel);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.free(conn);
        }
    }

    private void atualizarParcelaShaken(ActionForm form, HttpServletRequest request, Errors errors) {
        ShakenModel shakenModel = (ShakenModel) form;
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();

            int vlParcelaPaga = Integer.parseInt(shakenModel.getValorParcelaPaga().replace(",", "").replace(".", "").replace(" ", ""));
            int vlValorRestante = Integer.parseInt(shakenModel.getValorRestante().replace(",", "").replace(".", ""));
            int valorRestanteAtualizado = vlValorRestante - vlParcelaPaga;

            //verificar se é a ultima parcela, se for eu preciso fazer uma logica para que o valor seja igual o valor restante
            boolean isUltimaParcela = ShakenDAO.getInstance().isUltimaParcelaAberta(conn, shakenModel.getId());
            if (isUltimaParcela && valorRestanteAtualizado != 0) {
                //se for a ultima parcela aberta -> o valor pago deve ser igual ao valor restante
                errors.error("The value of the last installment must be equal to the Remaining value. The value must be: " + vlValorRestante);

            } else {
                //atualizar os dados da tabela controle_shaken
                ShakenDAO.getInstance().atualizarControleShaken(conn, shakenModel);

                //atualizar o valor restante na tabela shaken
                ShakenDAO.getInstance().atualizarValorRestante(conn, valorRestanteAtualizado, shakenModel.getId());

            }

            //obter dados do shaken atualizar por ID shaken
            shakenModel = ShakenDAO.getInstance().obterDadosShakenPorID(conn, shakenModel.getId());

            //pesquisar os shakens atualizado para pagamento por ID shaken
            List<ShakenModel> listaParcelas = ShakenDAO.getInstance().obterListaParcelasPorID(conn, shakenModel.getId());

            request.setAttribute("listaParcelas", listaParcelas);

            request.setAttribute("ShakenModel", shakenModel);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.free(conn);
        }
    }

    private void excluirShaken(ActionForm form, HttpServletRequest request, Errors errors) {
        ShakenModel shakenModel = (ShakenModel) form;
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();

            //verificar se existe pelo menos uma parcela paga para o controle_shaken
            boolean isExistePagamento = ShakenDAO.getInstance().verificaPagamentoShaken(conn, shakenModel.getId());

            //verificar se o valor restante eh maior que 0
            boolean isExisteValorRestante = ShakenDAO.getInstance().verificaValorRestante(conn, shakenModel.getId());

            if (!isExistePagamento && isExisteValorRestante) {
                //se nao existe nenhum pagamento, eu devo pegar todas as parcelas pelo ID do Shaken para poder excluir
                List<ShakenModel> listaParcelas = ShakenDAO.getInstance().obterListaParcelasPorID(conn, shakenModel.getId());
                for (ShakenModel parcela : listaParcelas) {
                    //deletar cada parcela pelo id do IDCONTROLE
                    ShakenDAO.getInstance().excluirControleShaken(conn, parcela.getIdControle());
                }

                //e por fim eu excluo da tabela shaken pelo ID shaken
                ShakenDAO.getInstance().excluirShaken(conn, shakenModel.getId());
                errors.error("Deletion Completed Successfully!!");

            } else {
                errors.error("It cannot be excluded, as it already has a paid installment.");
            }

            //pesquisar os shakens de uma determinada pessoa
            List<ShakenModel> listaShaken = ShakenDAO.getInstance().obterListaShakenPorPessoa(conn, shakenModel.getIdPessoa());

            request.setAttribute("listaShaken", listaShaken);
            request.setAttribute("ShakenModel", shakenModel);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.free(conn);
        }
    }

}
