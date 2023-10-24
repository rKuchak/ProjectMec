/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.action;

import com.app.dao.VeiculoDAO;
import com.app.model.VeiculoModel;
import com.app.util.Errors;
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
public class TipoVeiculoAction extends IDRAction {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, Errors errors, String action) {
        String forward = action;
        if (action.equals("page")) {
            this.page(form, request, errors);
        } else if (action.equals("save")) {
            this.save(form, request, errors);
        } else if (action.equals("excluir")) {
            this.excluir(form, request, errors);
        }
        return mapping.findForward(forward);
    }

    private void page(ActionForm form, HttpServletRequest request, Errors errors) {
        VeiculoModel veiculoModel = new VeiculoModel();
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();

            //obter lista dos tipos cadastrados
            List<VeiculoModel> listaTipoVeiculos = VeiculoDAO.getInstance().obterListaTipoVeiculos(conn);
            if (listaTipoVeiculos.size() > 0) {
                request.setAttribute("listaTipoVeiculos", listaTipoVeiculos);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("VeiculoModel", veiculoModel);
    }

    private void save(ActionForm form, HttpServletRequest request, Errors errors) {
        VeiculoModel veiculoModel = (VeiculoModel) form;
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();

            boolean isExisteTipoVeiculo = VeiculoDAO.getInstance().isExisteTipoVeiculo(conn, veiculoModel);
            if (!isExisteTipoVeiculo) {
                VeiculoDAO.getInstance().save(conn, veiculoModel);
                errors.error("Vehicle Type Registration Completed Successfully!!");
                veiculoModel.setDsTipoVeiculo(null);
                request.setAttribute("save", "true");
            } else {
                errors.error("Type of Vehicle is already registered");
                request.setAttribute("save", "false");
            }

            this.page(form, request, errors);
            request.setAttribute("VeiculoModel", veiculoModel);
        } catch (Exception e) {
            e.printStackTrace();
            errors.error("Error when registering vehicle type!!");
            request.setAttribute("save", "false");
        } finally {
            connectionPool.free(conn);
        }
    }

    private void excluir(ActionForm form, HttpServletRequest request, Errors errors) {
        VeiculoModel veiculoModel = new VeiculoModel();
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            String tipoVeiculo = request.getParameter("idTipoVeiculo");
            if (tipoVeiculo != null && !tipoVeiculo.equals("")) {
                int idTipoVeiculo = Integer.parseInt(tipoVeiculo);
                //verificar se existe marca cadastrada para esse tipo. Se tiver nao deixa excluir
                boolean isExisteMarcaTipoVeiculo = VeiculoDAO.getInstance().isExisteMarcaTipoVeiculo(conn, tipoVeiculo);
                if (isExisteMarcaTipoVeiculo) {
                    errors.error("It was not possible to perform the Exclusion, as there is a Brand linked to this type of vehicle!!");
                } else {
                    VeiculoDAO.getInstance().excluirTipoVeiculo(conn, idTipoVeiculo);
                    errors.error("Deletion carried out successfully!!");
                }
            } else {
                errors.error("An error occurred while deleting vehicle type. Contact support.");
            }

            this.page(form, request, errors);
            request.setAttribute("VeiculoModel", veiculoModel);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.free(conn);
        }
    }

}
