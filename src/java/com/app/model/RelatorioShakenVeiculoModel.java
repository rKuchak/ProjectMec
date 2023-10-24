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
public class RelatorioShakenVeiculoModel extends FormBasico {

    private int idShaken;
    private String dsTipoVeiculo;
    private String dsMarcaVeiculo;
    private String dsVeiculo;
    private String valorGasto;
    private String valorCobrado;
    private String nomePessoa;
    private String emailPessoa;
    private String telefonePessoa;
    private String tipoTelefone;
    private String dataInicio; //filtro para data da venda
    private String dataFinal; //filtro para data da venda
    private String dataRealizacao;
    private String dataVencimento;

    public int getIdShaken() {
        return idShaken;
    }

    public void setIdShaken(int idShaken) {
        this.idShaken = idShaken;
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

    public String getValorGasto() {
        return valorGasto;
    }

    public void setValorGasto(String valorGasto) {
        this.valorGasto = valorGasto;
    }

    public String getValorCobrado() {
        return valorCobrado;
    }

    public void setValorCobrado(String valorCobrado) {
        this.valorCobrado = valorCobrado;
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

    public String getTipoTelefone() {
        return tipoTelefone;
    }

    public void setTipoTelefone(String tipoTelefone) {
        this.tipoTelefone = tipoTelefone;
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

    public String getDataRealizacao() {
        return dataRealizacao;
    }

    public void setDataRealizacao(String dataRealizacao) {
        this.dataRealizacao = dataRealizacao;
    }

    public String getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
    }
    
    

}
