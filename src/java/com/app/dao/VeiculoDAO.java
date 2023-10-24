/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.dao;

import com.app.model.VeiculoModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author macuser
 */
public class VeiculoDAO {

    private static VeiculoDAO veiculoDAO = null;

    public static final VeiculoDAO getInstance() {
        if (veiculoDAO == null) {
            veiculoDAO = new VeiculoDAO();
        }
        return veiculoDAO;
    }

    public void save(Connection conn, VeiculoModel veiculoModel) throws SQLException {
        String query = "INSERT INTO tipo_veiculo (ds_tipo_veiculo) VALUES (?)";

        PreparedStatement prep = conn.prepareStatement(query);
        prep.setString(1, veiculoModel.getDsTipoVeiculo());
        prep.execute();
        prep.close();

    }

    public boolean isExisteTipoVeiculo(Connection conn, VeiculoModel veiculoModel) throws SQLException, SQLException {
        String query = "select * from tipo_veiculo where ds_tipo_veiculo like ?";
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setString(1, veiculoModel.getDsTipoVeiculo());
        ResultSet rs = prep.executeQuery();
        if (rs.next()) {
            return true;
        }
        rs.close();
        prep.close();

        return false;
    }

    public List<VeiculoModel> obterListaTipoVeiculos(Connection conn) throws SQLException {
        List<VeiculoModel> listaTipoVeiculo = new ArrayList<>();
        String query = "select * from tipo_veiculo order by ds_tipo_veiculo";
        PreparedStatement prep = conn.prepareStatement(query);
        ResultSet rs = prep.executeQuery();
        while (rs.next()) {
            VeiculoModel veiculoModel = new VeiculoModel();
            veiculoModel.setIdTipoVeiculo(rs.getInt("id"));
            veiculoModel.setDsTipoVeiculo(rs.getString("ds_tipo_veiculo"));

            listaTipoVeiculo.add(veiculoModel);
        }
        rs.close();
        prep.close();

        return listaTipoVeiculo;

    }

    public void excluirTipoVeiculo(Connection conn, int idTipoVeiculo) throws SQLException {
        String query = "DELETE FROM tipo_veiculo WHERE id=?";
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setInt(1, idTipoVeiculo);
        prep.execute();
        prep.close();
    }

    public boolean isExisteMarcaVeiculo(Connection conn, String dsMarcaVeiculo, int idTipoVeiculo) throws SQLException {
        String query = "select * from marca_veiculo where ds_marca_veiculo like ?  and id_tipo_veiculo = ?";
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setString(1, dsMarcaVeiculo);
        prep.setInt(2, idTipoVeiculo);
        ResultSet rs = prep.executeQuery();
        if (rs.next()) {
            return true;
        }
        rs.close();
        prep.close();

        return false;
    }

    public void saveMarcaVeiculo(Connection conn, VeiculoModel veiculoModel) throws SQLException {
        String query = "INSERT INTO marca_veiculo (id_tipo_veiculo, ds_marca_veiculo) VALUES (?,?)";
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setInt(1, veiculoModel.getIdTipoVeiculo());
        prep.setString(2, veiculoModel.getDsMarcaVeiculo());
        prep.execute();
        prep.close();
    }

    public List<VeiculoModel> obterListaMarcaVeiculo(Connection conn) throws SQLException {
        List<VeiculoModel> listaMarcaVeiculo = new ArrayList<>();
        String query = "select m.id, m.ds_marca_veiculo, t.ds_tipo_veiculo "
                + " from marca_veiculo m, tipo_veiculo t"
                + " where t.id = m.id_tipo_veiculo"
                + " order by t.ds_tipo_veiculo, m.ds_marca_veiculo";
        PreparedStatement prep = conn.prepareStatement(query);
        ResultSet rs = prep.executeQuery();
        while (rs.next()) {
            VeiculoModel marcaForm = new VeiculoModel();
            marcaForm.setIdMarcaVeiculo(rs.getInt("id"));
            marcaForm.setDsMarcaVeiculo(rs.getString("ds_marca_veiculo"));
            marcaForm.setDsTipoVeiculo(rs.getString("ds_tipo_veiculo"));
            listaMarcaVeiculo.add(marcaForm);
        }
        rs.close();
        prep.close();

        return listaMarcaVeiculo;
    }

    public void excluirMarcaVeiculo(Connection conn, int idMarcaVeiculo) throws SQLException {
        String query = "DELETE FROM marca_veiculo WHERE id=?";
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setInt(1, idMarcaVeiculo);
        prep.execute();
        prep.close();
    }

    public boolean isExisteMarcaTipoVeiculo(Connection conn, String tipoVeiculo) throws SQLException {
        String query = "select * from marca_veiculo m where m.id_tipo_veiculo = ?";
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setString(1, tipoVeiculo);
        ResultSet rs = prep.executeQuery();
        if (rs.next()) {
            return true;
        }
        rs.close();
        prep.close();

        return false;
    }

    public List<VeiculoModel> obterListaMarcaVeiculoPorTipo(Connection conn, int idTipoVeiculo) throws SQLException {
        List<VeiculoModel> listaMarcaVeiculo = new ArrayList<>();
        String query = "select * from marca_veiculo m where m.id_tipo_veiculo = ? order by m.ds_marca_veiculo";
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setInt(1, idTipoVeiculo);
        ResultSet rs = prep.executeQuery();
        while (rs.next()) {
            VeiculoModel marcaForm = new VeiculoModel();
            marcaForm.setIdMarcaVeiculo(rs.getInt("id"));
            marcaForm.setDsMarcaVeiculo(rs.getString("ds_marca_veiculo"));
            marcaForm.setIdTipoVeiculo(rs.getInt("id_tipo_veiculo"));
            listaMarcaVeiculo.add(marcaForm);
        }
        rs.close();
        prep.close();

        return listaMarcaVeiculo;
    }

    public boolean isExisteNomeVeiculo(Connection conn, String nomeVeiculo) throws SQLException {
        String query = "select * from veiculo where nome_veiculo like ?";
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setString(1, nomeVeiculo);
        ResultSet rs = prep.executeQuery();
        if (rs.next()) {
            return true;
        }
        rs.close();
        prep.close();

        return false;
    }

    public void saveNomeVeiculo(Connection conn, VeiculoModel veiculoModel) throws SQLException {
        String query = "INSERT INTO veiculo (id_tipo_veiculo, id_marca_veiculo, nome_veiculo) VALUES (?,?,?)";
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setInt(1, veiculoModel.getIdTipoVeiculo());
        prep.setInt(2, veiculoModel.getIdMarcaVeiculo());
        prep.setString(3, veiculoModel.getNomeVeiculo());
        prep.execute();
        prep.close();
    }

    public void excluirVeiculo(Connection conn, int idVeiculo) throws SQLException {
        String query = "DELETE FROM veiculo WHERE id=?";
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setInt(1, idVeiculo);
        prep.execute();
        prep.close();
    }

    public List<VeiculoModel> obterListaVeiculoPorMarca(Connection conn, int idMarcaVeiculo) throws SQLException {
        List<VeiculoModel> listaMarcaVeiculo = new ArrayList<>();
        String query = "select mv.ds_marca_veiculo, v.nome_veiculo, v.id"
                + " from veiculo v, marca_veiculo mv"
                + " where v.id_marca_veiculo = mv.id"
                + " and v.id_marca_veiculo = ?"
                + " order by v.nome_veiculo";
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setInt(1, idMarcaVeiculo);
        ResultSet rs = prep.executeQuery();
        while (rs.next()) {
            VeiculoModel marcaForm = new VeiculoModel();
            marcaForm.setDsMarcaVeiculo(rs.getString("ds_marca_veiculo"));
            marcaForm.setNomeVeiculo(rs.getString("nome_veiculo"));
            marcaForm.setIdVeiculo(rs.getInt("id"));

            listaMarcaVeiculo.add(marcaForm);
        }
        rs.close();
        prep.close();

        return listaMarcaVeiculo;
    }

    public boolean isExisteMarcaEmUso(Connection conn, int idMarcaVeiculo) throws SQLException {
        String query = "select * from veiculo v where v.id_marca_veiculo = ?";
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setInt(1, idMarcaVeiculo);
        ResultSet rs = prep.executeQuery();
        if (rs.next()) {
            return true;
        }
        rs.close();
        prep.close();

        return false;
    }

    public boolean isExisteVeiculoEmUso(Connection conn, int idVeiculo) throws SQLException {
        String query = "select id_veiculo from venda_veiculo v where v.id_veiculo = ?"
                + " union"
                + " select id_veiculo from shaken s where s.id_veiculo = ?";
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setInt(1, idVeiculo);
        prep.setInt(2, idVeiculo);
        ResultSet rs = prep.executeQuery();
        if (rs.next()) {
            return true;
        }
        rs.close();
        prep.close();

        return false;
    }

}
