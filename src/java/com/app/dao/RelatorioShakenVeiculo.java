/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.dao;

import com.app.model.RelatorioShakenVeiculoModel;
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
public class RelatorioShakenVeiculo {

    private static RelatorioShakenVeiculo relatorio = null;

    public static final RelatorioShakenVeiculo getInstance() {
        if (relatorio == null) {
            relatorio = new RelatorioShakenVeiculo();
        }
        return relatorio;
    }

    public List<RelatorioShakenVeiculoModel> obterShakenRealizadoPorData(Connection conn, String dataInicio, String dataFinal) throws SQLException {
        List<RelatorioShakenVeiculoModel> listaVendasVeiculos = new ArrayList<>();
        String query = "select s.id, tv.ds_tipo_veiculo, mv.ds_marca_veiculo, v.nome_veiculo, s.valor_gasto, s.valor_cobrado, s.data_realizacao,"
                + " s.data_vencimento, p.nome, p.email, t.numero"
                + " from shaken s, tipo_veiculo tv, marca_veiculo mv, veiculo v, pessoa p, telefone t"
                + " where s.id_tipo_veiculo = tv.id"
                + " and s.id_marca_veiculo = mv.id"
                + " and s.id_veiculo = v.id"
                + " and s.id_pessoa = p.id"
                + " and p.id = t.id_pessoa"
                + " and s.data_realizacao between ? and ?"
                + " order by s.data_realizacao";

        PreparedStatement prep = conn.prepareStatement(query);
        prep.setString(1, dataInicio);
        prep.setString(2, dataFinal);
        ResultSet rs = prep.executeQuery();
        while (rs.next()) {
            RelatorioShakenVeiculoModel relShaken = new RelatorioShakenVeiculoModel();
            relShaken.setIdShaken(rs.getInt("id"));
            relShaken.setDsTipoVeiculo(rs.getString("ds_tipo_veiculo"));
            relShaken.setDsMarcaVeiculo(rs.getString("ds_marca_veiculo"));
            relShaken.setDsVeiculo(rs.getString("nome_veiculo"));
            relShaken.setValorGasto(rs.getString("valor_gasto"));
            relShaken.setValorCobrado(rs.getString("valor_cobrado"));
            relShaken.setDataRealizacao(rs.getString("data_realizacao"));
            relShaken.setDataVencimento(rs.getString("data_vencimento"));
            relShaken.setNomePessoa(rs.getString("nome"));
            relShaken.setEmailPessoa(rs.getString("email"));
            relShaken.setTelefonePessoa(rs.getString("numero"));

            listaVendasVeiculos.add(relShaken);
        }
        rs.close();
        prep.close();

        return listaVendasVeiculos;
    }

    public List<RelatorioShakenVeiculoModel> obterShakenPorDataVencimento(Connection conn, String dataInicio, String dataFinal) throws SQLException {
        List<RelatorioShakenVeiculoModel> listaVendasVeiculos = new ArrayList<>();
        String query = "select s.id, tv.ds_tipo_veiculo, mv.ds_marca_veiculo, v.nome_veiculo, s.valor_gasto, s.valor_cobrado, s.data_realizacao,"
                + " s.data_vencimento, p.nome, p.email, t.numero, t.tipo_telefone"
                + " from shaken s, tipo_veiculo tv, marca_veiculo mv, veiculo v, pessoa p, telefone t"
                + " where s.id_tipo_veiculo = tv.id"
                + " and s.id_marca_veiculo = mv.id"
                + " and s.id_veiculo = v.id"
                + " and s.id_pessoa = p.id"
                + " and p.id = t.id_pessoa"
                + " and s.data_realizacao between ? and ?"
                + " order by s.data_vencimento";

        PreparedStatement prep = conn.prepareStatement(query);
        prep.setString(1, dataInicio);
        prep.setString(2, dataFinal);
        ResultSet rs = prep.executeQuery();
        while (rs.next()) {
            RelatorioShakenVeiculoModel relShaken = new RelatorioShakenVeiculoModel();
            relShaken.setIdShaken(rs.getInt("id"));
            relShaken.setDsTipoVeiculo(rs.getString("ds_tipo_veiculo"));
            relShaken.setDsMarcaVeiculo(rs.getString("ds_marca_veiculo"));
            relShaken.setDsVeiculo(rs.getString("nome_veiculo"));
            relShaken.setValorGasto(rs.getString("valor_gasto"));
            relShaken.setValorCobrado(rs.getString("valor_cobrado"));
            relShaken.setDataRealizacao(rs.getString("data_realizacao"));
            relShaken.setDataVencimento(rs.getString("data_vencimento"));
            relShaken.setNomePessoa(rs.getString("nome"));
            relShaken.setEmailPessoa(rs.getString("email"));
            relShaken.setTelefonePessoa(rs.getString("numero"));
            relShaken.setTipoTelefone(rs.getString("tipo_telefone"));

            listaVendasVeiculos.add(relShaken);
        }
        rs.close();
        prep.close();

        return listaVendasVeiculos;
    }
}
