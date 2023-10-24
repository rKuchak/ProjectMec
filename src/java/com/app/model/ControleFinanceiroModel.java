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
public class ControleFinanceiroModel extends FormBasico {
    
    private int id;
    private String tipo; //entrada ou saida
    private String dataReferencia;
    private int valorInteiro;
    private String valor;
    private String descricao;
    
    private String vlTotalSaida;
    private String vlTotalEntrada;
    private String vlTotalLiquido;
    private int vlTotalSaidaGrafico;
    private int vlTotalEntradaGrafico;
    private int vlTotalLiquidoGrafico;
    
    private int mes;
    private int ano;
    
    private int mesComparacao;
    private int anoComparacao;
    
    private int offset;

    public int getVlTotalSaidaGrafico() {
        return vlTotalSaidaGrafico;
    }

    public void setVlTotalSaidaGrafico(int vlTotalSaidaGrafico) {
        this.vlTotalSaidaGrafico = vlTotalSaidaGrafico;
    }

    public int getVlTotalEntradaGrafico() {
        return vlTotalEntradaGrafico;
    }

    public void setVlTotalEntradaGrafico(int vlTotalEntradaGrafico) {
        this.vlTotalEntradaGrafico = vlTotalEntradaGrafico;
    }

    public int getVlTotalLiquidoGrafico() {
        return vlTotalLiquidoGrafico;
    }

    public void setVlTotalLiquidoGrafico(int vlTotalLiquidoGrafico) {
        this.vlTotalLiquidoGrafico = vlTotalLiquidoGrafico;
    }

    
    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
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

    public int getMesComparacao() {
        return mesComparacao;
    }

    public void setMesComparacao(int mesComparacao) {
        this.mesComparacao = mesComparacao;
    }

    public int getAnoComparacao() {
        return anoComparacao;
    }

    public void setAnoComparacao(int anoComparacao) {
        this.anoComparacao = anoComparacao;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDataReferencia() {
        return dataReferencia;
    }

    public void setDataReferencia(String dataReferencia) {
        this.dataReferencia = dataReferencia;
    }

    public String getValor() {
        return valor;
    }

    public int getValorInteiro() {
        return valorInteiro;
    }

    public void setValorInteiro(int valorInteiro) {
        this.valorInteiro = valorInteiro;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getVlTotalSaida() {
        return vlTotalSaida;
    }

    public void setVlTotalSaida(String vlTotalSaida) {
        this.vlTotalSaida = vlTotalSaida;
    }

    public String getVlTotalEntrada() {
        return vlTotalEntrada;
    }

    public void setVlTotalEntrada(String vlTotalEntrada) {
        this.vlTotalEntrada = vlTotalEntrada;
    }

    public String getVlTotalLiquido() {
        return vlTotalLiquido;
    }

    public void setVlTotalLiquido(String vlTotalLiquido) {
        this.vlTotalLiquido = vlTotalLiquido;
    }
    
    
    
    
}
