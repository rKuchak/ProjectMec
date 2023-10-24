/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.model;

import java.util.List;

/**
 *
 * @author macuser
 */
public class VeiculoModel extends FormBasico {
    
    private int idTipoVeiculo;
    private String dsTipoVeiculo;
    private int idMarcaVeiculo;
    private String dsMarcaVeiculo;
    private int idVeiculo;
    private String nomeVeiculo;

    public int getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(int idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public String getNomeVeiculo() {
        return nomeVeiculo;
    }

    public void setNomeVeiculo(String nomeVeiculo) {
        this.nomeVeiculo = nomeVeiculo;
    }
    
    public int getIdMarcaVeiculo() {
        return idMarcaVeiculo;
    }

    public void setIdMarcaVeiculo(int idMarcaVeiculo) {
        this.idMarcaVeiculo = idMarcaVeiculo;
    }

    public String getDsMarcaVeiculo() {
        return dsMarcaVeiculo;
    }

    public void setDsMarcaVeiculo(String dsMarcaVeiculo) {
        this.dsMarcaVeiculo = dsMarcaVeiculo;
    }
    
    public int getIdTipoVeiculo() {
        return idTipoVeiculo;
    }

    public void setIdTipoVeiculo(int idTipoVeiculo) {
        this.idTipoVeiculo = idTipoVeiculo;
    }

    public String getDsTipoVeiculo() {
        return dsTipoVeiculo;
    }

    public void setDsTipoVeiculo(String dsTipoVeiculo) {
        this.dsTipoVeiculo = dsTipoVeiculo;
    }
    
    
    
    
}
