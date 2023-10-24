/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.dao;

import com.app.model.LoginModel;
import com.app.util.Criptografia;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author macuser
 */
public class LoginDAO {

    private static LoginDAO loginDao = null;

    public static final LoginDAO getInstance() {
        if (loginDao == null) {
            loginDao = new LoginDAO();
        }
        return loginDao;
    }

    public LoginModel verificarLoginExistente(Connection conn, LoginModel loginModel) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        Criptografia criptografia = new Criptografia();
        String query = "select * from pessoa p where p.email = ? and p.senha = ? and p.tipo_login = 1";
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setString(1, loginModel.getLogin());
        prep.setString(2, criptografia.senhaCriptografada(loginModel.getSenha()));
        ResultSet rs = prep.executeQuery();
        LoginModel login = new LoginModel();
        if(rs.next()) {
            login.setIdPessoa(rs.getInt("id"));
            login.setNome(rs.getString("nome"));
            login.setTipoLogin(rs.getString("tipo_login"));
        }
        rs.close();
        prep.close();
        
        return login;
    }
    
    

}
