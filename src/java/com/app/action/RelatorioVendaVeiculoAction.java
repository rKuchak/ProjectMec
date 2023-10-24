/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.action;

import com.app.dao.RelatorioVendaVeiculo;
import com.app.model.RelatorioVendaVeiculoModel;
import com.app.util.Errors;
import com.app.util.Utilitario;
import java.sql.Connection;
import java.time.LocalDate;
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
public class RelatorioVendaVeiculoAction extends IDRAction {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, Errors errors, String action) {
        String forward = action;
        if (action.equals("relVendasVeiculosRealizadas")) {
            this.relVendasVeiculosRealizadas(form, request, errors);
        } else if (action.equals("relParcelasPagasPage")) {
            this.relParcelasPagasPage(form, request, errors);
        } else if (action.equals("relParcelasPagas")) {
            this.relParcelasPagas(form, request, errors);
        }

        return mapping.findForward(forward);
    }

    private void relVendasVeiculosRealizadas(ActionForm form, HttpServletRequest request, Errors errors) {
        RelatorioVendaVeiculoModel relModel = (RelatorioVendaVeiculoModel) form;
        Connection conn = null;
        HttpSession session = request.getSession();
        try {
            conn = connectionPool.getConnection();

            List<RelatorioVendaVeiculoModel> listaVendasVeiculos
                    = RelatorioVendaVeiculo.getInstance().obterVendasRealizadasPorData(conn, relModel.getDataInicio(), relModel.getDataFinal());

            if (!listaVendasVeiculos.isEmpty()) {
                int vlPrecoCompra = 0;
                int vlPrecoVenda = 0;
                for (RelatorioVendaVeiculoModel dadosVeiculo : listaVendasVeiculos) {
                    int precoCompra = Integer.parseInt(dadosVeiculo.getPrecoCompra());
                    vlPrecoCompra += precoCompra;

                    int precoVenda = Integer.parseInt(dadosVeiculo.getPrecoVenda());
                    vlPrecoVenda += precoVenda;
                }
                relModel.setVlTotalCompra(Utilitario.getInstance().formatacaoIene(vlPrecoCompra));
                relModel.setVlTotalVenda(Utilitario.getInstance().formatacaoIene(vlPrecoVenda));

                int vlLucroLiquido = vlPrecoVenda - vlPrecoCompra;
                relModel.setVlTotalLucroLiquido(Utilitario.getInstance().formatacaoIene(vlLucroLiquido));

            }

            session.setAttribute("listaVendasVeiculos", listaVendasVeiculos);
            request.setAttribute("RelatorioVendaVeiculoModel", relModel);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.free(conn);
        }
    }

    private void relParcelasPagasPage(ActionForm form, HttpServletRequest request, Errors errors) {
        RelatorioVendaVeiculoModel relModel = new RelatorioVendaVeiculoModel();
        
        relModel.setMes(LocalDate.now().getMonthValue());
        relModel.setAno(LocalDate.now().getYear());
        
        request.setAttribute("RelatorioVendaVeiculoModel", relModel);
    }

    private void relParcelasPagas(ActionForm form, HttpServletRequest request, Errors errors) {
        RelatorioVendaVeiculoModel relModel = (RelatorioVendaVeiculoModel) form;
        Connection conn = null;
        HttpSession session = request.getSession();
        try {
            conn = connectionPool.getConnection();

            LocalDate dataInicio = Utilitario.getInstance().obterPrimeiroDiaMes(relModel.getMes(), relModel.getAno());
            LocalDate dataFinal = Utilitario.getInstance().obterUltimoDiaMes(relModel.getMes(), relModel.getAno());

            List<RelatorioVendaVeiculoModel> listaParcelasPagas = RelatorioVendaVeiculo.getInstance().obterParcelasPagasPorPeriodo(conn, dataInicio, dataFinal);

            if(!listaParcelasPagas.isEmpty()){
                int vlPago = 0;
                for (RelatorioVendaVeiculoModel parcelasPaga : listaParcelasPagas) {
                    vlPago += Integer.parseInt(parcelasPaga.getValorPago());
                }
                relModel.setVlTotalPago(Utilitario.getInstance().formatacaoIene(vlPago));
            }
            
            session.setAttribute("listaParcelasPagas", listaParcelasPagas);
            request.setAttribute("RelatorioVendaVeiculoModel", relModel);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.free(conn);
        }
    }

}
