/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.dao;

import com.app.model.ShakenModel;
import com.app.util.Utilitario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author macuser
 */
public class ShakenDAO {

    private static ShakenDAO shakenDAO = null;

    public static final ShakenDAO getInstance() {
        if (shakenDAO == null) {
            shakenDAO = new ShakenDAO();
        }
        return shakenDAO;
    }

    public int salvarShaken(Connection conn, ShakenModel shakenModel, int vlEntrada, int vlCobrado, int vlRestante) throws SQLException {
        String query = "INSERT INTO shaken (data_realizacao, data_vencimento, id_tipo_veiculo, id_marca_veiculo, id_veiculo, valor_gasto,"
                + " valor_cobrado, dia_pagamento_prestacao, ano_veiculo, chassi, id_pessoa, observacao, qtd_parcelas, valor_entrada, valor_restante) "
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        int idShaken = 0;
        PreparedStatement prep = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        prep.setString(1, shakenModel.getDataRealizacao());
        prep.setString(2, shakenModel.getDataVencimento());
        prep.setInt(3, shakenModel.getIdTipoVeiculo());
        prep.setInt(4, shakenModel.getIdMarcaVeiculo());
        prep.setInt(5, shakenModel.getIdVeiculo());
        prep.setString(6, shakenModel.getValorGasto().replace(",", "").replace(".", ""));
        prep.setInt(7, vlCobrado);
        prep.setInt(8, shakenModel.getDiaPagamentoPrestacao());
        prep.setString(9, shakenModel.getAnoVeiculo());
        prep.setString(10, shakenModel.getChassi());
        prep.setInt(11, shakenModel.getIdPessoa());
        prep.setString(12, shakenModel.getObservacao());
        prep.setInt(13, shakenModel.getQtdParcelas());
        prep.setInt(14, vlEntrada);
        prep.setInt(15, vlRestante);
        prep.execute();
        ResultSet rs = prep.getGeneratedKeys();
        if (rs.next()) {
            idShaken = rs.getInt(1);
        }
        rs.close();
        prep.close();

        return idShaken;

    }

    public void salvarControleShaken(Connection conn, int idShaken, int diaPagamentoPrestacao, String dataPagamento, int vlParcelas) throws SQLException {
        String query = "INSERT INTO controle_shaken (id_shaken, dia_pagamento, data_pagamento, valor) VALUES (?,?,?,?)";
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setInt(1, idShaken);
        prep.setInt(2, diaPagamentoPrestacao);
        prep.setString(3, dataPagamento);
        prep.setInt(4, vlParcelas);
        prep.execute();
        prep.close();
    }

    public List<ShakenModel> obterPessoasComShaken(Connection conn) throws SQLException {
        List<ShakenModel> listaPessoasComShaken = new ArrayList<>();
        String query = "select p.id, p.nome "
                + " from pessoa p, shaken s"
                + " where p.id = s.id_pessoa"
                + " group by p.id"
                + " order by p.nome";

        PreparedStatement prep = conn.prepareStatement(query);
        ResultSet rs = prep.executeQuery();
        while (rs.next()) {
            ShakenModel shakenModel = new ShakenModel();
            shakenModel.setIdPessoa(rs.getInt("id"));
            shakenModel.setNomePessoa(rs.getString("nome"));

            listaPessoasComShaken.add(shakenModel);
        }
        rs.close();
        prep.close();

        return listaPessoasComShaken;
    }

    public List<ShakenModel> obterListaShakenPorPessoa(Connection conn, int idPessoa) throws SQLException {
        List<ShakenModel> listaShaken = new ArrayList<>();
        String query = "select tp.ds_tipo_veiculo, mc.ds_marca_veiculo, v.nome_veiculo, s.id, s.data_realizacao, s.data_vencimento, "
                + " s.valor_cobrado, s.dia_pagamento_prestacao, s.id_pessoa, "
                + " s.ano_veiculo, s.chassi, s.observacao, s.qtd_parcelas, s.valor_entrada, s.valor_restante"
                + " from shaken s, tipo_veiculo tp, marca_veiculo mc, veiculo v"
                + " where"
                + " s.id_tipo_veiculo = tp.id"
                + " and s.id_marca_veiculo = mc.id"
                + " and s.id_veiculo = v.id"
                + " and s.id_pessoa = ?"
                + " order by s.data_vencimento desc";
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setInt(1, idPessoa);
        ResultSet rs = prep.executeQuery();
        while (rs.next()) {
            ShakenModel shakenModel = new ShakenModel();
            shakenModel.setDsTipoVeiculo(rs.getString("ds_tipo_veiculo"));
            shakenModel.setDsMarcaVeiculo(rs.getString("ds_marca_veiculo"));
            shakenModel.setNomeVeiculo(rs.getString("nome_veiculo"));
            shakenModel.setId(rs.getInt("id"));
            shakenModel.setIdPessoa(rs.getInt("id_pessoa"));
            shakenModel.setDataRealizacao(rs.getString("data_realizacao"));
            shakenModel.setDataVencimento(rs.getString("data_vencimento"));
            shakenModel.setDiaPagamentoPrestacao(rs.getInt("dia_pagamento_prestacao"));
            shakenModel.setAnoVeiculo(rs.getString("ano_veiculo"));
            shakenModel.setChassi(rs.getString("chassi"));
            shakenModel.setObservacao(rs.getString("observacao"));
            shakenModel.setQtdParcelas(rs.getInt("qtd_parcelas"));

            long vlCobrado = rs.getLong("valor_cobrado");
            String vlCobradoFormatado = Utilitario.getInstance().formatacaoIene(vlCobrado);
            shakenModel.setValorCobrado(vlCobradoFormatado);

            long vlEntrada = rs.getLong("valor_entrada");
            String vlEntradaFormatado = Utilitario.getInstance().formatacaoIene(vlEntrada);
            shakenModel.setValorEntrada(vlEntradaFormatado);

            long vlRestante = rs.getLong("valor_restante");
            String vlRestanteFormatado = Utilitario.getInstance().formatacaoIene(vlRestante);
            shakenModel.setValorRestante(vlRestanteFormatado);

            listaShaken.add(shakenModel);
        }
        rs.close();
        prep.close();

        return listaShaken;
    }

    public List<ShakenModel> obterListaParcelasPorID(Connection conn, int id) throws SQLException {
        List<ShakenModel> listaParcelas = new ArrayList<>();
        String query = "select * from controle_shaken where id_shaken = ?";
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setInt(1, id);
        ResultSet rs = prep.executeQuery();
        while (rs.next()) {
            ShakenModel shakenModel = new ShakenModel();
            shakenModel.setIdControle(rs.getInt("id"));
            shakenModel.setId(rs.getInt("id_shaken"));
            shakenModel.setDiaPagamentoPrestacao(rs.getInt("dia_pagamento"));
            shakenModel.setDataPagamento(rs.getString("data_pagamento"));
            shakenModel.setDataPagamentoRealizado(rs.getString("data_pagamento_realizado"));
            shakenModel.setStatus(rs.getInt("status"));

            long vlCobrado = rs.getLong("valor");
            String vlCobradoFormatado = Utilitario.getInstance().formatacaoIene(vlCobrado);
            shakenModel.setValorParcela(vlCobradoFormatado);
            
            long vlPago = rs.getLong("valor_pago");
            String vlPagoFormatado = Utilitario.getInstance().formatacaoIene(vlPago);
            shakenModel.setValorParcelaPaga(vlPagoFormatado);

            listaParcelas.add(shakenModel);
        }
        rs.close();
        prep.close();

        return listaParcelas;
    }

    public ShakenModel obterDadosShakenPorID(Connection conn, int id) throws SQLException {
        String query = "select tp.ds_tipo_veiculo, mc.ds_marca_veiculo, v.nome_veiculo, s.id, s.data_realizacao, s.data_vencimento, "
                + " s.valor_cobrado, s.dia_pagamento_prestacao,"
                + " s.ano_veiculo, s.chassi, s.observacao, s.qtd_parcelas, s.valor_entrada, s.valor_restante"
                + " from shaken s, tipo_veiculo tp, marca_veiculo mc, veiculo v"
                + " where"
                + " s.id_tipo_veiculo = tp.id"
                + " and s.id_marca_veiculo = mc.id"
                + " and s.id_veiculo = v.id"
                + " and s.id = ?"
                + " order by s.data_vencimento desc";
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setInt(1, id);
        ResultSet rs = prep.executeQuery();
        ShakenModel shakenModel = new ShakenModel();
        while (rs.next()) {
            shakenModel.setDsTipoVeiculo(rs.getString("ds_tipo_veiculo"));
            shakenModel.setDsMarcaVeiculo(rs.getString("ds_marca_veiculo"));
            shakenModel.setNomeVeiculo(rs.getString("nome_veiculo"));
            shakenModel.setId(rs.getInt("id"));
            shakenModel.setDataRealizacao(rs.getString("data_realizacao"));
            shakenModel.setDataVencimento(rs.getString("data_vencimento"));
            shakenModel.setDiaPagamentoPrestacao(rs.getInt("dia_pagamento_prestacao"));
            shakenModel.setAnoVeiculo(rs.getString("ano_veiculo"));
            shakenModel.setChassi(rs.getString("chassi"));
            shakenModel.setObservacao(rs.getString("observacao"));
            shakenModel.setQtdParcelas(rs.getInt("qtd_parcelas"));

            long vlCobrado = rs.getLong("valor_cobrado");
            String vlCobradoFormatado = Utilitario.getInstance().formatacaoIene(vlCobrado);
            shakenModel.setValorCobrado(vlCobradoFormatado);

            long vlEntrada = rs.getLong("valor_entrada");
            String vlEntradaFormatado = Utilitario.getInstance().formatacaoIene(vlEntrada);
            shakenModel.setValorEntrada(vlEntradaFormatado);

            long vlRestante = rs.getLong("valor_restante");
            String vlRestanteFormatado = Utilitario.getInstance().formatacaoIene(vlRestante);
            shakenModel.setValorRestante(vlRestanteFormatado);
        }
        rs.close();
        prep.close();

        return shakenModel;
    }

    public void atualizarControleShaken(Connection conn, ShakenModel shakenModel) throws SQLException {
        String query = "UPDATE controle_shaken SET data_pagamento_realizado=?, valor_pago=?, status=1 WHERE id=?";
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setString(1, shakenModel.getDataPagamentoRealizado());
        prep.setString(2, shakenModel.getValorParcelaPaga().replace(",", "").replace(".", "").replace(" ", ""));
        prep.setInt(3, shakenModel.getIdControle());
        prep.execute();
        prep.close();
    }

    public void atualizarValorRestante(Connection conn, int valorRestanteAtualizado, int id) throws SQLException {
        String query = "UPDATE shaken SET valor_restante=? WHERE id=?";
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setInt(1, valorRestanteAtualizado);
        prep.setInt(2, id);
        prep.execute();
        prep.close();
    }

    public boolean verificaPagamentoShaken(Connection conn, int id) throws SQLException {
        boolean isExistePagamento = false;
        String query = "select * from controle_shaken c where c.id_shaken = ? and c.status = 1";
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setInt(1, id);
        ResultSet rs = prep.executeQuery();
        if(rs.next()) {
            isExistePagamento = true;
        }
        rs.close();
        prep.close();
        
        return isExistePagamento;
    }

    public boolean verificaValorRestante(Connection conn, int id) throws SQLException {
        boolean isExisteValorRestante = false;
        String query = "select * from shaken s where s.id = ? and s.valor_restante > 0";
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setInt(1, id);
        ResultSet rs = prep.executeQuery();
        if(rs.next()) {
            isExisteValorRestante = true;
        }
        rs.close();
        prep.close();
        
        return isExisteValorRestante;
    }

    public void excluirControleShaken(Connection conn, int idControle) throws SQLException {
        String query = "DELETE FROM controle_shaken WHERE id=?";
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setInt(1, idControle);
        prep.execute();
        prep.close();
    }

    public void excluirShaken(Connection conn, int id) throws SQLException {
        String query = "DELETE FROM shaken WHERE id=?";
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setInt(1, id);
        prep.execute();
        prep.close();
    }

    public boolean isUltimaParcelaAberta(Connection conn, int id) throws SQLException {
        boolean isUltimaParcela = true;
        String query = "select * from controle_shaken where id_shaken = ? and status = 0 order by id";
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setInt(1, id);
        ResultSet rs = prep.executeQuery();
        List<ShakenModel> listaParcelasAbertas = new ArrayList<>();
        while(rs.next()) {
            ShakenModel shakenModel = new ShakenModel();
            shakenModel.setIdControle(rs.getInt("id"));
            listaParcelasAbertas.add(shakenModel);
        }
        rs.close();
        prep.close();
        
        if(listaParcelasAbertas.size() > 1) {
            isUltimaParcela = false;
        }
        
        return isUltimaParcela;
    }

}
