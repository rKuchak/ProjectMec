/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.test;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author macuser
 */
public class TesteConnection {

    private static final String USUARIO = "sitesn59_java";
    private static final String SENHA = "admin";
    private static final String URL = "jdbc:mysql://br966.hostgator.com.br:3306/sitesn59_java";
    private static final String DRIVER = "com.mysql.jdbc.Driver";

    public static void main(String[] args) {
        try {
            Class.forName(DRIVER);
            Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA);

            String query = "select * from pessoa";
            PreparedStatement prep = conn.prepareStatement(query);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString("nome"));
                System.out.println(rs.getString("data_nascimento"));
            }
            rs.close();
            prep.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
