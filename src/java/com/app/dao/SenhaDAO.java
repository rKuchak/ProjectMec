/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author macuser
 */
public class SenhaDAO {

    private static SenhaDAO senhaDAO = null;

    public static final SenhaDAO getInstance() {
        if (senhaDAO == null) {
            senhaDAO = new SenhaDAO();
        }
        return senhaDAO;
    }

    public boolean verificarSenhaAtual(Connection conn, int idPessoa, String senhaAtualCripto) throws SQLException {
        String query = "select * from pessoa where id = ? and senha = ?";
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setInt(1, idPessoa);
        prep.setString(2, senhaAtualCripto);
        ResultSet rs = prep.executeQuery();
        if(rs.next()){
            return true;
        }
        rs.close();
        prep.close();
        
        return false;
                
    }

    public void alterarSenhaPorId(Connection conn, int idPessoa, String senhaNovaCripto) throws SQLException {
        String query = "UPDATE pessoa SET senha=? WHERE id=?";
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setString(1, senhaNovaCripto);
        prep.setInt(2, idPessoa);
        prep.execute();
        prep.close();
    }

}
