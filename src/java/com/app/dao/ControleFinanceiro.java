/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.dao;

import com.app.model.ControleFinanceiroModel;
import com.app.util.Utilitario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author macuser
 */
public class ControleFinanceiro {

    private static ControleFinanceiro controleFinanceiro = null;

    public static final ControleFinanceiro getInstance() {
        if (controleFinanceiro == null) {
            controleFinanceiro = new ControleFinanceiro();
        }
        return controleFinanceiro;
    }

    public void inserir(Connection conn, ControleFinanceiroModel controleFinanceiroModel) throws SQLException {
        String query = "INSERT INTO controle_financeiro (tipo, data_referencia, valor, descricao) "
                + " VALUES (?, ?, ?, ?)";
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setString(1, controleFinanceiroModel.getTipo());
        prep.setString(2, controleFinanceiroModel.getDataReferencia());
        prep.setString(3, controleFinanceiroModel.getValor().replace(",", "").replace(".", ""));
        prep.setString(4, controleFinanceiroModel.getDescricao());
        prep.execute();
        prep.close();

    }

    public List<ControleFinanceiroModel> obterControleFinanceiroPorMes(Connection conn, String dataInicio, String dataFinal) throws SQLException {
        List<ControleFinanceiroModel> listaControleFinanceiro = new ArrayList<>();
        String query = "select * from controle_financeiro c where c.data_referencia between ? and ?";
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setString(1, dataInicio);
        prep.setString(2, dataFinal);
        ResultSet rs = prep.executeQuery();
        while (rs.next()) {
            ControleFinanceiroModel controleFinanceiroModel = new ControleFinanceiroModel();
            controleFinanceiroModel.setId(rs.getInt("id"));
            controleFinanceiroModel.setTipo(rs.getString("tipo"));
            controleFinanceiroModel.setDataReferencia(rs.getString("data_referencia"));
            controleFinanceiroModel.setValorInteiro(rs.getInt("valor"));

            Long vl = rs.getLong("valor");
            String vlFormatado = Utilitario.getInstance().formatacaoIene(vl);
            controleFinanceiroModel.setValor(vlFormatado);

            controleFinanceiroModel.setDescricao(rs.getString("descricao"));
            listaControleFinanceiro.add(controleFinanceiroModel);
        }
        rs.close();
        prep.close();

        return listaControleFinanceiro;
    }

    public void excluir(Connection conn, int id) throws SQLException {
        String query = "DELETE FROM controle_financeiro WHERE id=?";
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setInt(1, id);
        prep.execute();
        prep.close();
    }

    public List<ControleFinanceiroModel> obterControleFinanceiroPorMesPaginado(Connection conn, String dataInicio, String dataFinal, int offset) throws SQLException {
        List<ControleFinanceiroModel> listaControleFinanceiro = new ArrayList<>();
        String query = "select * from controle_financeiro c where c.data_referencia between ? and ?";
        if (offset > 0) {
            query += " limit " + offset + ", 5";
        } else {
            query += " limit 0, 5";
        }

        PreparedStatement prep = conn.prepareStatement(query);
        prep.setString(1, dataInicio);
        prep.setString(2, dataFinal);
        ResultSet rs = prep.executeQuery();
        while (rs.next()) {
            ControleFinanceiroModel controleFinanceiroModel = new ControleFinanceiroModel();
            controleFinanceiroModel.setId(rs.getInt("id"));
            controleFinanceiroModel.setTipo(rs.getString("tipo"));
            controleFinanceiroModel.setDataReferencia(rs.getString("data_referencia"));

            Long vl = rs.getLong("valor");
            String vlFormatado = Utilitario.getInstance().formatacaoIene(vl);
            controleFinanceiroModel.setValor(vlFormatado);

            controleFinanceiroModel.setDescricao(rs.getString("descricao"));
            listaControleFinanceiro.add(controleFinanceiroModel);
        }
        rs.close();
        prep.close();

        return listaControleFinanceiro;
    }

    public int obterQtdTotalControleFinanceiroPorMes(Connection conn, String dataInicio, String dataFinal) throws SQLException {
        int qtdTotal = 0;
        String query = "select count(*) as qtd from controle_financeiro c "
                + " where c.data_referencia between ? and ?";
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setString(1, dataInicio);
        prep.setString(2, dataFinal);
        ResultSet rs = prep.executeQuery();
        if(rs.next()) {
            qtdTotal = rs.getInt("qtd");
        }
        rs.close();
        prep.close();
        
        return qtdTotal;
    }
}
