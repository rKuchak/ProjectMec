/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.dao;

import com.app.model.ControleVendasModel;
import com.app.util.Utilitario;
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
public class ControleVendasDAO {

    private static ControleVendasDAO controleVendasDAO = null;

    public static final ControleVendasDAO getInstance() {
        if (controleVendasDAO == null) {
            controleVendasDAO = new ControleVendasDAO();
        }
        return controleVendasDAO;
    }

    public int save(Connection conn, ControleVendasModel controleVendasModel, int vlEntrada, int vlCobrado, int vlRestante) throws SQLException {
        int idVendaVeiculo = 0;
        String query = "INSERT INTO venda_veiculo (id_tipo_veiculo, id_marca_veiculo, id_veiculo, chassi, cor, ano, preco_compra, preco_venda,"
                + " cambio, motor, combustivel, km, shaken, capacidade_pessoa, nr_portas, detalhes_extras, freio, data_venda, data_insercao,"
                + " path_img_1, id_pessoa, qtd_parcelas, valor_entrada, valor_restante, dia_pagamento_prestacao) "
                + " VALUES (?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, now(),?,?,?,?,?,?)";

        PreparedStatement prep = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        prep.setInt(1, controleVendasModel.getIdTipoVeiculo());
        prep.setInt(2, controleVendasModel.getIdMarcaVeiculo());
        prep.setInt(3, controleVendasModel.getIdVeiculo());
        prep.setString(4, controleVendasModel.getChassi());
        prep.setString(5, controleVendasModel.getCor());
        prep.setString(6, controleVendasModel.getAno());
        prep.setString(7, controleVendasModel.getPrecoCompra().replace(",", "").replace(".", ""));
        prep.setInt(8, vlCobrado);
        prep.setString(9, controleVendasModel.getCambio());
        prep.setString(10, controleVendasModel.getMotor());
        prep.setString(11, controleVendasModel.getCombustivel());
        prep.setString(12, controleVendasModel.getKm());
        prep.setString(13, controleVendasModel.getShaken());
        prep.setInt(14, controleVendasModel.getCapacidadePessoa());
        prep.setInt(15, controleVendasModel.getNrPortas());
        prep.setString(16, controleVendasModel.getDetalhesExtras());
        prep.setString(17, controleVendasModel.getFreio());
        prep.setString(18, controleVendasModel.getDataVenda());
        prep.setString(19, controleVendasModel.getPathImg1());
        prep.setInt(20, controleVendasModel.getIdPessoa());
        prep.setInt(21, controleVendasModel.getQtdParcelas());
        prep.setInt(22, vlEntrada);
        prep.setInt(23, vlRestante);
        prep.setInt(24, controleVendasModel.getDiaPagamentoPrestacao());
        prep.execute();
        ResultSet rs = prep.getGeneratedKeys();
        if (rs.next()) {
            idVendaVeiculo = rs.getInt(1);
        }
        rs.close();
        prep.close();

        return idVendaVeiculo;
    }

    public List<ControleVendasModel> pesquisarVendaVeiculos(Connection conn, ControleVendasModel controleVendasModel) throws SQLException {
        List<ControleVendasModel> listaVeiculos = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append(" select c.*, tp.ds_tipo_veiculo, mv.ds_marca_veiculo, v.nome_veiculo, p.nome");
        sb.append(" from venda_veiculo c, tipo_veiculo tp, marca_veiculo mv, veiculo v, pessoa p");
        sb.append(" where c.id_tipo_veiculo = tp.id");
        sb.append(" and c.id_marca_veiculo = mv.id");
        sb.append(" and c.id_veiculo = v.id");
        sb.append(" and c.id_pessoa = p.id");
        sb.append(" and c.id_tipo_veiculo = ").append(controleVendasModel.getIdTipoVeiculo());
        if (controleVendasModel.getIdMarcaVeiculo() != 0) {
            sb.append(" and c.id_marca_veiculo = ").append(controleVendasModel.getIdMarcaVeiculo());
        }
        if (controleVendasModel.getIdVeiculo() != 0) {
            sb.append(" and c.id_veiculo = ").append(controleVendasModel.getIdVeiculo());
        }
        if(controleVendasModel.getIdPessoa() != 0) {
           sb.append(" and c.id_pessoa = ").append(controleVendasModel.getIdPessoa());
        }

        PreparedStatement prep = conn.prepareStatement(sb.toString());
        ResultSet rs = prep.executeQuery();
        while (rs.next()) {
            ControleVendasModel controlForm = new ControleVendasModel();
            controlForm.setIdVendaVeiculo(rs.getInt("id"));
            controlForm.setIdVeiculo(rs.getInt("id_veiculo"));
            controlForm.setAno(rs.getString("ano"));
            controlForm.setKm(rs.getString("km"));
            controlForm.setCor(rs.getString("cor"));
            controlForm.setDsTipoVeiculo(rs.getString("ds_tipo_veiculo"));
            controlForm.setDsMarcaVeiculo(rs.getString("ds_marca_veiculo"));
            controlForm.setDsVeiculo(rs.getString("nome_veiculo"));
            controlForm.setPathImg1(rs.getString("path_img_1"));
            controlForm.setIdPessoa(rs.getInt("id_pessoa"));
            controlForm.setNomePessoa(rs.getString("nome"));
            controlForm.setQtdParcelas(rs.getInt("qtd_parcelas"));

            long vlEntrada = rs.getLong("valor_entrada");
            String vlEntradaFormatado = Utilitario.getInstance().formatacaoIene(vlEntrada);
            controlForm.setValorEntrada(vlEntradaFormatado);

            long vlRestante = rs.getLong("valor_restante");
            String vlRestanteFormatado = Utilitario.getInstance().formatacaoIene(vlRestante);
            controlForm.setValorRestante(vlRestanteFormatado);

            controlForm.setDiaPagamentoPrestacao(rs.getInt("dia_pagamento_prestacao"));

            long precoVendaSemFormatacao = rs.getLong("preco_venda");
            String precoVendaFormatado = Utilitario.getInstance().formatacaoIene(precoVendaSemFormatacao);
            controlForm.setPrecoVenda(precoVendaFormatado);

            listaVeiculos.add(controlForm);
        }
        rs.close();
        prep.close();

        return listaVeiculos;

    }

    public ControleVendasModel detalhesVeiculo(Connection conn, int idControleVendas) throws SQLException {
        ControleVendasModel controlForm = new ControleVendasModel();

        String query = "select c.*, tp.ds_tipo_veiculo, mv.ds_marca_veiculo, v.nome_veiculo "
                + " from venda_veiculo c, tipo_veiculo tp, marca_veiculo mv, veiculo v"
                + " where c.id_tipo_veiculo = tp.id"
                + " and c.id_marca_veiculo = mv.id"
                + " and c.id_veiculo = v.id"
                + " and c.id = ?";

        PreparedStatement prep = conn.prepareStatement(query);
        prep.setInt(1, idControleVendas);
        ResultSet rs = prep.executeQuery();
        if (rs.next()) {
            controlForm.setIdVendaVeiculo(rs.getInt("id"));
            controlForm.setIdTipoVeiculo(rs.getInt("id_tipo_veiculo"));
            controlForm.setIdMarcaVeiculo(rs.getInt("id_marca_veiculo"));
            controlForm.setIdVeiculo(rs.getInt("id_veiculo"));
            controlForm.setChassi(rs.getString("chassi"));
            controlForm.setAno(rs.getString("ano"));
            controlForm.setCambio(rs.getString("cambio"));
            controlForm.setMotor(rs.getString("motor"));
            controlForm.setCombustivel(rs.getString("combustivel"));
            controlForm.setKm(rs.getString("km"));
            controlForm.setShaken(rs.getString("shaken"));
            controlForm.setCapacidadePessoa(rs.getInt("capacidade_pessoa"));
            controlForm.setNrPortas(rs.getInt("nr_portas"));
            controlForm.setFreio(rs.getString("freio"));
            controlForm.setDetalhesExtras(rs.getString("detalhes_extras"));
            controlForm.setDataInsercao(rs.getString("data_insercao"));
            controlForm.setDataVenda(rs.getString("data_venda"));
            controlForm.setCor(rs.getString("cor"));
            controlForm.setDsTipoVeiculo(rs.getString("ds_tipo_veiculo"));
            controlForm.setDsMarcaVeiculo(rs.getString("ds_marca_veiculo"));
            controlForm.setDsVeiculo(rs.getString("nome_veiculo"));

            long precoCompraSemFormatacao = rs.getLong("preco_compra");
            String precoCompraFormatado = Utilitario.getInstance().formatacaoIene(precoCompraSemFormatacao);
            controlForm.setPrecoCompra(precoCompraFormatado);

            long precoVendaSemFormatacao = rs.getLong("preco_venda");
            String precoVendaFormatado = Utilitario.getInstance().formatacaoIene(precoVendaSemFormatacao);
            controlForm.setPrecoVenda(precoVendaFormatado);

            controlForm.setPathImg1(rs.getString("path_img_1"));

        }
        rs.close();
        prep.close();
        return controlForm;
    }

    public void atualizar(Connection conn, ControleVendasModel controleVendasModel) throws SQLException {

        String query = "UPDATE venda_veiculo SET chassi=?, cor=?, ano=?, preco_compra=?, preco_venda=?, "
                + " cambio=?, motor=?, combustivel=?, km=?, shaken=?, capacidade_pessoa=?, nr_portas=?, "
                + " detalhes_extras=?, freio=?, data_venda = ?, path_img_1 = ? "
                + " WHERE id=?";

        PreparedStatement prep = conn.prepareStatement(query);
        prep.setString(1, controleVendasModel.getChassi());
        prep.setString(2, controleVendasModel.getCor());
        prep.setString(3, controleVendasModel.getAno());
        prep.setString(4, controleVendasModel.getPrecoCompra().replace(",", "").replace(".", ""));
        prep.setString(5, controleVendasModel.getPrecoVenda().replace(",", "").replace(".", ""));
        prep.setString(6, controleVendasModel.getCambio());
        prep.setString(7, controleVendasModel.getMotor());
        prep.setString(8, controleVendasModel.getCombustivel());
        prep.setString(9, controleVendasModel.getKm());
        prep.setString(10, controleVendasModel.getShaken());
        prep.setInt(11, controleVendasModel.getCapacidadePessoa());
        prep.setInt(12, controleVendasModel.getNrPortas());
        prep.setString(13, controleVendasModel.getDetalhesExtras());
        prep.setString(14, controleVendasModel.getFreio());
        prep.setString(15, controleVendasModel.getDataVenda());
        prep.setString(16, controleVendasModel.getPathImg1());
        prep.setInt(17, controleVendasModel.getIdVendaVeiculo());
        prep.execute();
        prep.close();

    }

    public void excluir(Connection conn, int idVendaVeiculo) throws SQLException {
        String query = "DELETE FROM venda_veiculo WHERE id=?";
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setInt(1, idVendaVeiculo);
        prep.execute();
        prep.close();
    }

    public void salvarControleVendaVeiculo(Connection conn, int idVendaVeiculo, int diaPagamentoPrestacao, String dataPagamento, int vlParcelas) throws SQLException {
        String query = "INSERT INTO controle_venda_veiculo (id_venda_veiculo, dia_pagamento, data_pagamento, valor) VALUES (?,?,?,?)";
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setInt(1, idVendaVeiculo);
        prep.setInt(2, diaPagamentoPrestacao);
        prep.setString(3, dataPagamento);
        prep.setInt(4, vlParcelas);
        prep.execute();
        prep.close();
    }

    public ControleVendasModel obterDadosVeiculosIdControleVendas(Connection conn, int idVendaVeiculo) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append(" select c.*, tp.ds_tipo_veiculo, mv.ds_marca_veiculo, v.nome_veiculo, p.nome");
        sb.append(" from venda_veiculo c, tipo_veiculo tp, marca_veiculo mv, veiculo v, pessoa p");
        sb.append(" where c.id_tipo_veiculo = tp.id");
        sb.append(" and c.id_marca_veiculo = mv.id");
        sb.append(" and c.id_veiculo = v.id");
        sb.append(" and c.id_pessoa = p.id");
        sb.append(" and c.id = ").append(idVendaVeiculo);

        PreparedStatement prep = conn.prepareStatement(sb.toString());
        ResultSet rs = prep.executeQuery();
        ControleVendasModel controlForm = new ControleVendasModel();
        if (rs.next()) {
            controlForm.setIdVendaVeiculo(rs.getInt("id"));
            controlForm.setIdVeiculo(rs.getInt("id_veiculo"));
            controlForm.setAno(rs.getString("ano"));
            controlForm.setKm(rs.getString("km"));
            controlForm.setCor(rs.getString("cor"));
            controlForm.setDsTipoVeiculo(rs.getString("ds_tipo_veiculo"));
            controlForm.setDsMarcaVeiculo(rs.getString("ds_marca_veiculo"));
            controlForm.setDsVeiculo(rs.getString("nome_veiculo"));
            controlForm.setPathImg1(rs.getString("path_img_1"));
            controlForm.setIdPessoa(rs.getInt("id_pessoa"));
            controlForm.setNomePessoa(rs.getString("nome"));
            controlForm.setQtdParcelas(rs.getInt("qtd_parcelas"));
            controlForm.setDataVenda(rs.getString("data_venda"));

            long vlEntrada = rs.getLong("valor_entrada");
            String vlEntradaFormatado = Utilitario.getInstance().formatacaoIene(vlEntrada);
            controlForm.setValorEntrada(vlEntradaFormatado);

            long vlRestante = rs.getLong("valor_restante");
            String vlRestanteFormatado = Utilitario.getInstance().formatacaoIene(vlRestante);
            controlForm.setValorRestante(vlRestanteFormatado);

            controlForm.setDiaPagamentoPrestacao(rs.getInt("dia_pagamento_prestacao"));

            long precoVendaSemFormatacao = rs.getLong("preco_venda");
            String precoVendaFormatado = Utilitario.getInstance().formatacaoIene(precoVendaSemFormatacao);
            controlForm.setPrecoVenda(precoVendaFormatado);

        }
        rs.close();
        prep.close();
        return controlForm;
    }

    public List<ControleVendasModel> obterListaParcelasPorID(Connection conn, int idVendaVeiculo) throws SQLException {
        List<ControleVendasModel> listaParcelas = new ArrayList<>();
        String query = "select * from controle_venda_veiculo where id_venda_veiculo = ? order by id";
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setInt(1, idVendaVeiculo);

        ResultSet rs = prep.executeQuery();
        while (rs.next()) {
            ControleVendasModel controleVendas = new ControleVendasModel();
            controleVendas.setIdControleVenda(rs.getInt("id"));
            controleVendas.setIdVendaVeiculo(rs.getInt("id_venda_veiculo"));
            controleVendas.setDiaPagamentoPrestacao(rs.getInt("dia_pagamento"));
            controleVendas.setDataPagamento(rs.getString("data_pagamento"));
            controleVendas.setDataPagamentoRealizado(rs.getString("data_pagamento_realizado"));
            controleVendas.setStatus(rs.getInt("status"));

            long vlCobrado = rs.getLong("valor");
            String vlCobradoFormatado = Utilitario.getInstance().formatacaoIene(vlCobrado);
            controleVendas.setValorParcela(vlCobradoFormatado);

            long vlPago = rs.getLong("valor_pago");
            String vlPagoFormatado = Utilitario.getInstance().formatacaoIene(vlPago);
            controleVendas.setValorParcelaPaga(vlPagoFormatado);

            listaParcelas.add(controleVendas);
        }
        rs.close();
        prep.close();

        return listaParcelas;

    }

    public boolean verificaPagamentoVeiculo(Connection conn, int idVendaVeiculo) throws SQLException {
        String query = "select * from controle_venda_veiculo c where c.id_venda_veiculo = ? and c.status = 1";
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setInt(1, idVendaVeiculo);
        ResultSet rs = prep.executeQuery();
        if (rs.next()) {
            return true;
        }
        rs.close();
        prep.close();

        return false;
    }

    public boolean verificaValorRestante(Connection conn, int idVendaVeiculo) throws SQLException {
        String query = "select * from venda_veiculo v where v.id = ? and v.valor_restante > 0";
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setInt(1, idVendaVeiculo);
        ResultSet rs = prep.executeQuery();
        if (rs.next()) {
            return true;
        }
        rs.close();
        prep.close();

        return false;
    }

    public void excluirControleVendaVeiculo(Connection conn, int idControleVenda) throws SQLException {
        String query = "DELETE FROM controle_venda_veiculo WHERE id = ?";
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setInt(1, idControleVenda);
        prep.execute();
        prep.close();
    }

    public boolean isUltimaParcelaAberta(Connection conn, int id) throws SQLException {
        boolean isUltimaParcela = true;
        String query = "select * from controle_venda_veiculo where id_venda_veiculo = ? and status = 0 order by id";
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setInt(1, id);
        ResultSet rs = prep.executeQuery();
        List<ControleVendasModel> listaParcelasAbertas = new ArrayList<>();
        while (rs.next()) {
            ControleVendasModel shakenModel = new ControleVendasModel();
            shakenModel.setIdControleVenda(rs.getInt("id"));
            listaParcelasAbertas.add(shakenModel);
        }
        rs.close();
        prep.close();

        if (listaParcelasAbertas.size() > 1) {
            isUltimaParcela = false;
        }

        return isUltimaParcela;
    }

    public void atualizarParcelaVeiculo(Connection conn, ControleVendasModel controleVendas) throws SQLException {
        String query = "UPDATE controle_venda_veiculo SET data_pagamento_realizado=?, valor_pago=?, status=1 WHERE id=?";
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setString(1, controleVendas.getDataPagamentoRealizado());
        prep.setString(2, controleVendas.getValorParcelaPaga().replace(",", "").replace(".", "").replace(" ", ""));
        prep.setInt(3, controleVendas.getIdControleVenda());
        prep.execute();
        prep.close();
    }

    public void atualizarValorRestante(Connection conn, int valorRestanteAtualizado, int id) throws SQLException {
        String query = "UPDATE venda_veiculo SET valor_restante=? WHERE id=?";
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setInt(1, valorRestanteAtualizado);
        prep.setInt(2, id);
        prep.execute();
        prep.close();
    }

    public List<ControleVendasModel> obterPessoasComVendaVeiculo(Connection conn) throws SQLException {
        List<ControleVendasModel> listaPessoasVendaVeiculo = new ArrayList<>();
        String query = "select p.id, p.nome "
                + " from pessoa p, venda_veiculo v"
                + " where p.id = v.id_pessoa"
                + " group by p.id"
                + " order by p.nome;";

        PreparedStatement prep = conn.prepareStatement(query);
        ResultSet rs = prep.executeQuery();
        while (rs.next()) {
            ControleVendasModel controleVendas = new ControleVendasModel();
            controleVendas.setIdPessoa(rs.getInt("id"));
            controleVendas.setNomePessoa(rs.getString("nome"));

            listaPessoasVendaVeiculo.add(controleVendas);
        }
        rs.close();
        prep.close();

        return listaPessoasVendaVeiculo;
    }

}
