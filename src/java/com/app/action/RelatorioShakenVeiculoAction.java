/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.action;

import com.app.dao.RelatorioShakenVeiculo;
import com.app.model.RelatorioShakenVeiculoModel;
import com.app.util.Errors;
import java.sql.Connection;
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
public class RelatorioShakenVeiculoAction extends IDRAction {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, Errors errors, String action) {
        String forward = action;
        if (action.equals("relShakenRealizadas")) {
            this.relShakenRealizadas(form, request, errors);
        } else if (action.equals("relShakenVencer")) {
            this.relShakenVencer(form, request, errors);
        }

        return mapping.findForward(forward);
    }

    private void relShakenRealizadas(ActionForm form, HttpServletRequest request, Errors errors) {
        RelatorioShakenVeiculoModel relModel = (RelatorioShakenVeiculoModel) form;
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();

            List<RelatorioShakenVeiculoModel> listaShaken
                    = RelatorioShakenVeiculo.getInstance().obterShakenRealizadoPorData(conn, relModel.getDataInicio(), relModel.getDataFinal());

            request.setAttribute("listaShaken", listaShaken);
            request.setAttribute("RelatorioShakenVeiculoModel", relModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void relShakenVencer(ActionForm form, HttpServletRequest request, Errors errors) {
        RelatorioShakenVeiculoModel relModel = (RelatorioShakenVeiculoModel) form;
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();

            List<RelatorioShakenVeiculoModel> listaShaken
                    = RelatorioShakenVeiculo.getInstance().obterShakenPorDataVencimento(conn, relModel.getDataInicio(), relModel.getDataFinal());

            request.setAttribute("listaShaken", listaShaken);
            request.setAttribute("RelatorioShakenVeiculoModel", relModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
