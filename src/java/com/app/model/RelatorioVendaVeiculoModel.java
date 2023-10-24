/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.model;

/**
 *
 * @author macuser
 */
public class RelatorioVendaVeiculoModel extends FormBasico {
    
    private int idVenda;
    private String dsTipoVeiculo;
    private String dsMarcaVeiculo;
    private String dsVeiculo;
    private String precoVenda;
    private String precoCompra;
    private String dataVenda;
    private int qtdParcelas;
    private String nomePessoa;
    private String emailPessoa;
    private String telefonePessoa;
    private String dataInicio; //filtro para data da venda
    private String dataFinal; //filtro para data da venda
    
    private String vlTotalCompra;
    private String vlTotalVenda;
    private String vlTotalLucroLiquido;
    private String vlTotalPago;
    
    private int mes;
    private int ano;
    
    private String dataPagamentoRealizado;
    private String valorPago;

    public String getVlTotalPago() {
        return vlTotalPago;
    }

    public void setVlTotalPago(String vlTotalPago) {
        this.vlTotalPago = vlTotalPago;
    }
    
    public String getDataPagamentoRealizado() {
        return dataPagamentoRealizado;
    }

    public void setDataPagamentoRealizado(String dataPagamentoRealizado) {
        this.dataPagamentoRealizado = dataPagamentoRealizado;
    }

    public String getValorPago() {
        return valorPago;
    }

    public void setValorPago(String valorPago) {
        this.valorPago = valorPago;
    }

    
    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }
    
    

    public int getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }

    public String getDsTipoVeiculo() {
        return dsTipoVeiculo;
    }

    public void setDsTipoVeiculo(String dsTipoVeiculo) {
        this.dsTipoVeiculo = dsTipoVeiculo;
    }

    public String getDsMarcaVeiculo() {
        return dsMarcaVeiculo;
    }

    public void setDsMarcaVeiculo(String dsMarcaVeiculo) {
        this.dsMarcaVeiculo = dsMarcaVeiculo;
    }

    public String getDsVeiculo() {
        return dsVeiculo;
    }

    public void setDsVeiculo(String dsVeiculo) {
        this.dsVeiculo = dsVeiculo;
    }

    public String getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(String precoVenda) {
        this.precoVenda = precoVenda;
    }

    public String getPrecoCompra() {
        return precoCompra;
    }

    public void setPrecoCompra(String precoCompra) {
        this.precoCompra = precoCompra;
    }

    public String getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(String dataVenda) {
        this.dataVenda = dataVenda;
    }

    public int getQtdParcelas() {
        return qtdParcelas;
    }

    public void setQtdParcelas(int qtdParcelas) {
        this.qtdParcelas = qtdParcelas;
    }

    public String getNomePessoa() {
        return nomePessoa;
    }

    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa = nomePessoa;
    }

    public String getEmailPessoa() {
        return emailPessoa;
    }

    public void setEmailPessoa(String emailPessoa) {
        this.emailPessoa = emailPessoa;
    }

    public String getTelefonePessoa() {
        return telefonePessoa;
    }

    public void setTelefonePessoa(String telefonePessoa) {
        this.telefonePessoa = telefonePessoa;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(String dataFinal) {
        this.dataFinal = dataFinal;
    }

    public String getVlTotalCompra() {
        return vlTotalCompra;
    }

    public void setVlTotalCompra(String vlTotalCompra) {
        this.vlTotalCompra = vlTotalCompra;
    }

    public String getVlTotalVenda() {
        return vlTotalVenda;
    }

    public void setVlTotalVenda(String vlTotalVenda) {
        this.vlTotalVenda = vlTotalVenda;
    }

    public String getVlTotalLucroLiquido() {
        return vlTotalLucroLiquido;
    }

    public void setVlTotalLucroLiquido(String vlTotalLucroLiquido) {
        this.vlTotalLucroLiquido = vlTotalLucroLiquido;
    }
    
    
    
}
