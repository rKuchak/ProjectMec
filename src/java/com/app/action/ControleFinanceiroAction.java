/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.action;

import com.app.dao.ControleFinanceiro;
import com.app.model.ControleFinanceiroModel;
import com.app.util.Errors;
import com.app.util.Utilitario;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
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
public class ControleFinanceiroAction extends IDRAction {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, Errors errors, String action) {
        String forward = action;
        if (action.equals("pageAdicionar")) {
            this.pageAdicionar(form, request, errors);
        } else if (action.equals("adicionar")) {
            this.adicionar(form, request, errors);
        } else if (action.equals("excluir") || action.equals("excluirPorMes")) {
            this.excluir(form, request, errors, action);
        } else if (action.equals("pagePesquisar")) {
            this.pagePesquisar(form, request, errors);
        } else if (action.equals("pesquisar")) {
            this.pesquisar(form, request, errors);
        } else if (action.equals("paginacao")) {
            this.paginacao(mapping, form, request, errors);
        } else if (action.equals("pageComparacao")) {
            this.pageComparacao(form, request, errors);
        } else if (action.equals("compararMeses")) {
            this.compararMeses(form, request, errors);
        }

        return mapping.findForward(forward);
    }

    private void pageAdicionar(ActionForm form, HttpServletRequest request, Errors errors) {
        ControleFinanceiroModel controleFinanceiroModel = new ControleFinanceiroModel();
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();

            String dataInicio = String.valueOf(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()));
            String dataFinal = String.valueOf(LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()));

            //obter os dados da tabela controle_financeiro pelo mês vigente
            List<ControleFinanceiroModel> listaControleFinanceiro = ControleFinanceiro.getInstance().obterControleFinanceiroPorMes(conn, dataInicio, dataFinal);

            request.setAttribute("listaControleFinanceiro", listaControleFinanceiro);
            request.setAttribute("ControleFinanceiroModel", controleFinanceiroModel);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.free(conn);
        }
    }

    private void adicionar(ActionForm form, HttpServletRequest request, Errors errors) {
        ControleFinanceiroModel controleFinanceiroModel = (ControleFinanceiroModel) form;
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();

            //adicionar na tabela controle_financeiro
            ControleFinanceiro.getInstance().inserir(conn, controleFinanceiroModel);

            controleFinanceiroModel = new ControleFinanceiroModel();

            this.pageAdicionar(form, request, errors);

            request.setAttribute("ControleFinanceiroModel", controleFinanceiroModel);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.free(conn);
        }
    }

    private void excluir(ActionForm form, HttpServletRequest request, Errors errors, String action) {
        ControleFinanceiroModel controleFinanceiroModel = (ControleFinanceiroModel) form;
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();

            //excluir lancamento por ID
            ControleFinanceiro.getInstance().excluir(conn, controleFinanceiroModel.getId());
            if (action.equals("excluir")) {
                this.pageAdicionar(form, request, errors);
            } else {
                this.pesquisar(form, request, errors);
            }

            request.setAttribute("ControleFinanceiroModel", controleFinanceiroModel);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.free(conn);
        }
    }

    private void pagePesquisar(ActionForm form, HttpServletRequest request, Errors errors) {
        ControleFinanceiroModel controleFinanceiroModel = new ControleFinanceiroModel();
        controleFinanceiroModel.setMes(LocalDate.now().getMonthValue());
        controleFinanceiroModel.setAno(LocalDate.now().getYear());
        request.setAttribute("ControleFinanceiroModel", controleFinanceiroModel);
    }

    private void pesquisar(ActionForm form, HttpServletRequest request, Errors errors) {
        ControleFinanceiroModel controleFinanceiroModel = (ControleFinanceiroModel) form;
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();

            //obter controle financeiro por mes - passar 1 dia e ultimo dia do mes
            LocalDate dataInicioLocal = Utilitario.getInstance().obterPrimeiroDiaMes(controleFinanceiroModel.getMes(), controleFinanceiroModel.getAno());
            LocalDate dataFinalLocal = Utilitario.getInstance().obterUltimoDiaMes(controleFinanceiroModel.getMes(), controleFinanceiroModel.getAno());

            String dataInicio = String.valueOf(dataInicioLocal);
            String dataFinal = String.valueOf(dataFinalLocal);

            //obter os dados da tabela controle_financeiro pelo mês vigente
            List<ControleFinanceiroModel> listaControleFinanceiro = ControleFinanceiro.getInstance().obterControleFinanceiroPorMes(conn, dataInicio, dataFinal);
            if (!listaControleFinanceiro.isEmpty()) {
                int vlTotalEntrada = 0;
                int vlTotalSaida = 0;

                for (ControleFinanceiroModel dadoFinanceiro : listaControleFinanceiro) {
                    if (dadoFinanceiro.getTipo().equals("entrada")) {
                        vlTotalEntrada += dadoFinanceiro.getValorInteiro();
                    }
                    if (dadoFinanceiro.getTipo().equals("saida")) {
                        vlTotalSaida += dadoFinanceiro.getValorInteiro();
                    }
                }

                String vlTotalEntradaFormat = Utilitario.getInstance().formatacaoIeneNegativo(vlTotalEntrada);
                controleFinanceiroModel.setVlTotalEntrada(vlTotalEntradaFormat);
                controleFinanceiroModel.setVlTotalEntradaGrafico(vlTotalEntrada);

                String vlTotalSaidaFormat = Utilitario.getInstance().formatacaoIeneNegativo(vlTotalSaida);
                controleFinanceiroModel.setVlTotalSaida(vlTotalSaidaFormat);
                controleFinanceiroModel.setVlTotalSaidaGrafico(vlTotalSaida);

                int vlTotalLiquido = vlTotalEntrada - vlTotalSaida;
                String vlTotalLiquidoFormat = Utilitario.getInstance().formatacaoIeneNegativo(vlTotalLiquido);
                controleFinanceiroModel.setVlTotalLiquido(vlTotalLiquidoFormat);
                controleFinanceiroModel.setVlTotalLiquidoGrafico(vlTotalLiquido);

                request.setAttribute("listaControleFinanceiro", listaControleFinanceiro);
            }
            request.setAttribute("result", "true");
            request.setAttribute("ControleFinanceiroModel", controleFinanceiroModel);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.free(conn);
        }
    }

    private void paginacao(ActionMapping mapping, ActionForm form, HttpServletRequest request, Errors errors) {
        ControleFinanceiroModel controleFinanceiroModel = (ControleFinanceiroModel) form;
        Connection conn = null;
        int length = 5;
//        String url = request.getContextPath() + mapping.getPath() + ".do?action=paginacao";
        try {
            conn = connectionPool.getConnection();

            //obter controle financeiro por mes - passar 1 dia e ultimo dia do mes
            LocalDate dataInicioLocal = Utilitario.getInstance().obterPrimeiroDiaMes(controleFinanceiroModel.getMes(), controleFinanceiroModel.getAno());
            LocalDate dataFinalLocal = Utilitario.getInstance().obterUltimoDiaMes(controleFinanceiroModel.getMes(), controleFinanceiroModel.getAno());

            String dataInicio = String.valueOf(dataInicioLocal);
            String dataFinal = String.valueOf(dataFinalLocal);

            //obter os dados da tabela controle_financeiro pelo mes vigente paginado
            List<ControleFinanceiroModel> listaControleFinanceiro = ControleFinanceiro.getInstance().obterControleFinanceiroPorMesPaginado(conn, dataInicio, dataFinal, controleFinanceiroModel.getOffset());

            //obter qtd total pelo mes vigente
            int qtdTotal = ControleFinanceiro.getInstance().obterQtdTotalControleFinanceiroPorMes(conn, dataInicio, dataFinal);

            //criar tezto das paginas
            String pagerHeader = Utilitario.getInstance().pageGenerate(controleFinanceiroModel.getOffset(), qtdTotal, length);

            request.setAttribute("pagerHeader", pagerHeader);
            request.setAttribute("listaControleFinanceiro", listaControleFinanceiro);
            request.setAttribute("result", "true");
            request.setAttribute("ControleFinanceiroModel", controleFinanceiroModel);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.free(conn);
        }
    }

    private void pageComparacao(ActionForm form, HttpServletRequest request, Errors errors) {
        ControleFinanceiroModel controleFinanceiroModel = new ControleFinanceiroModel();

        //pegar mes e ano vigente
        controleFinanceiroModel.setMes(LocalDate.now().getMonthValue());
        controleFinanceiroModel.setAno(LocalDate.now().getYear());

        //pegar mes anterior
        controleFinanceiroModel.setMesComparacao(LocalDate.now().minusMonths(1).getMonthValue());
        controleFinanceiroModel.setAnoComparacao(LocalDate.now().minusMonths(1).getYear());

        request.setAttribute("ControleFinanceiroModel", controleFinanceiroModel);
    }

    private void compararMeses(ActionForm form, HttpServletRequest request, Errors errors) {
        ControleFinanceiroModel controleFinanceiroModel = (ControleFinanceiroModel) form;
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();

            //obter controle financeiro por mes - passar 1 dia e ultimo dia do mes
            LocalDate dataInicioLocal = Utilitario.getInstance().obterPrimeiroDiaMes(controleFinanceiroModel.getMes(), controleFinanceiroModel.getAno());
            LocalDate dataFinalLocal = Utilitario.getInstance().obterUltimoDiaMes(controleFinanceiroModel.getMes(), controleFinanceiroModel.getAno());

            String dataInicio = String.valueOf(dataInicioLocal);
            String dataFinal = String.valueOf(dataFinalLocal);

            //obter os dados da tabela controle_financeiro pelo mês vigente
            List<ControleFinanceiroModel> listaControleFinanceiro = ControleFinanceiro.getInstance().obterControleFinanceiroPorMes(conn, dataInicio, dataFinal);
            if (!listaControleFinanceiro.isEmpty()) {
                int vlTotalEntrada = 0;
                int vlTotalSaida = 0;

                for (ControleFinanceiroModel dadoFinanceiro : listaControleFinanceiro) {
                    if (dadoFinanceiro.getTipo().equals("entrada")) {
                        vlTotalEntrada += dadoFinanceiro.getValorInteiro();
                    }
                    if (dadoFinanceiro.getTipo().equals("saida")) {
                        vlTotalSaida += dadoFinanceiro.getValorInteiro();
                    }
                }

                String vlTotalEntradaFormat = Utilitario.getInstance().formatacaoIeneNegativo(vlTotalEntrada);
                controleFinanceiroModel.setVlTotalEntrada(vlTotalEntradaFormat);
                controleFinanceiroModel.setVlTotalEntradaGrafico(vlTotalEntrada);

                String vlTotalSaidaFormat = Utilitario.getInstance().formatacaoIeneNegativo(vlTotalSaida);
                controleFinanceiroModel.setVlTotalSaida(vlTotalSaidaFormat);
                controleFinanceiroModel.setVlTotalSaidaGrafico(vlTotalSaida);

                int vlTotalLiquido = vlTotalEntrada - vlTotalSaida;
                String vlTotalLiquidoFormat = Utilitario.getInstance().formatacaoIeneNegativo(vlTotalLiquido);
                controleFinanceiroModel.setVlTotalLiquido(vlTotalLiquidoFormat);
                controleFinanceiroModel.setVlTotalLiquidoGrafico(vlTotalLiquido);

                request.setAttribute("listaControleFinanceiro", listaControleFinanceiro);
            }
            request.setAttribute("ControleFinanceiroModel", controleFinanceiroModel);
            request.setAttribute("result", "true");
            
            //obter dados do mes de comparacao
            ControleFinanceiroModel controleFinanceiroComparacao = new ControleFinanceiroModel();
                    

            String dataInicioComparacao = String.valueOf(Utilitario.getInstance().obterPrimeiroDiaMes(controleFinanceiroModel.getMesComparacao(), controleFinanceiroModel.getAnoComparacao()));
            String dataFinalComparacao = String.valueOf(Utilitario.getInstance().obterUltimoDiaMes(controleFinanceiroModel.getMesComparacao(), controleFinanceiroModel.getAnoComparacao()));
            
            //obter os dados da tabela controle_financeiro pelo mês vigente
            List<ControleFinanceiroModel> listaControleFinanceiroComparacao = ControleFinanceiro.getInstance().obterControleFinanceiroPorMes(conn, dataInicioComparacao, dataFinalComparacao);
            if (!listaControleFinanceiroComparacao.isEmpty()) {
                int vlTotalEntrada = 0;
                int vlTotalSaida = 0;

                for (ControleFinanceiroModel dadoFinanceiro : listaControleFinanceiroComparacao) {
                    if (dadoFinanceiro.getTipo().equals("entrada")) {
                        vlTotalEntrada += dadoFinanceiro.getValorInteiro();
                    }
                    if (dadoFinanceiro.getTipo().equals("saida")) {
                        vlTotalSaida += dadoFinanceiro.getValorInteiro();
                    }
                }

                String vlTotalEntradaFormat = Utilitario.getInstance().formatacaoIeneNegativo(vlTotalEntrada);
                controleFinanceiroComparacao.setVlTotalEntrada(vlTotalEntradaFormat);
                controleFinanceiroComparacao.setVlTotalEntradaGrafico(vlTotalEntrada);

                String vlTotalSaidaFormat = Utilitario.getInstance().formatacaoIeneNegativo(vlTotalSaida);
                controleFinanceiroComparacao.setVlTotalSaida(vlTotalSaidaFormat);
                controleFinanceiroComparacao.setVlTotalSaidaGrafico(vlTotalSaida);

                int vlTotalLiquido = vlTotalEntrada - vlTotalSaida;
                String vlTotalLiquidoFormat = Utilitario.getInstance().formatacaoIeneNegativo(vlTotalLiquido);
                controleFinanceiroComparacao.setVlTotalLiquido(vlTotalLiquidoFormat);
                controleFinanceiroComparacao.setVlTotalLiquidoGrafico(vlTotalLiquido);

                request.setAttribute("listaControleFinanceiroComparacao", listaControleFinanceiroComparacao);
            }
            
            request.setAttribute("ControleFinanceiroModelComparacao", controleFinanceiroComparacao);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.free(conn);
        }
    }
}
