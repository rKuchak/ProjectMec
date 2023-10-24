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
public class MarcaVeiculoAction extends IDRAction {

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

            //obter lista dos tipos de veiculos
            List<VeiculoModel> listaTipoVeiculo = VeiculoDAO.getInstance().obterListaTipoVeiculos(conn);
            request.setAttribute("listaTipoVeiculo", listaTipoVeiculo);

            //obter lista das marcas de veiculos ja cadastradas
            List<VeiculoModel> listaMarcaVeiculo = VeiculoDAO.getInstance().obterListaMarcaVeiculo(conn);

            request.setAttribute("listaMarcaVeiculo", listaMarcaVeiculo);
            request.setAttribute("VeiculoModel", veiculoModel);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.free(conn);
        }

    }

    private void save(ActionForm form, HttpServletRequest request, Errors errors) {
        VeiculoModel veiculoModel = (VeiculoModel) form;
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();

            //validar se a marca ja existe cadastrada
            boolean isExisteMarca = VeiculoDAO.getInstance().isExisteMarcaVeiculo(conn, veiculoModel.getDsMarcaVeiculo(), veiculoModel.getIdTipoVeiculo());
            if (!isExisteMarca) {
                //se nao existe pode realizar o cadastro
                VeiculoDAO.getInstance().saveMarcaVeiculo(conn, veiculoModel);
                veiculoModel.setDsMarcaVeiculo(null);
                errors.error("Registered vehicle brand!!");
            } else {
                //caso exista nao cadastra e manda mensagem na tela
                errors.error("This Vehicle Brand is already registered!!");
            }
            this.page(form, request, errors);
            request.setAttribute("VeiculoModel", veiculoModel);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.free(conn);
        }

    }

    private void excluir(ActionForm form, HttpServletRequest request, Errors errors) {
        VeiculoModel veiculoModel = (VeiculoModel) form;
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();

            //verificar se a marca ja esta em uso
            boolean isExisteMarcaEmUso = VeiculoDAO.getInstance().isExisteMarcaEmUso(conn, veiculoModel.getIdMarcaVeiculo());
            if (isExisteMarcaEmUso) {
                errors.error("There is a vehicle registered for this Brand!!");
            } else {
                //excluir marca do veiculo
                VeiculoDAO.getInstance().excluirMarcaVeiculo(conn, veiculoModel.getIdMarcaVeiculo());
                errors.error("Vehicle Brand Deleted Successfully!!");

            }
            this.page(form, request, errors);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.free(conn);
        }
    }

}
