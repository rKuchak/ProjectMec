/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.connection;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author macuser
 */
public class JDCConnectionDriver {

    private DataSource ds = null;
    private String lookupName;

    public JDCConnectionDriver(String lookupName) {
        super();
        try {
            this.lookupName = lookupName;

            Context context = new InitialContext();
            Context envCtx = (Context) context.lookup("java:comp/env");
            ds = (DataSource) envCtx.lookup(lookupName);
        } catch (NamingException e) {
            //TODO: Use um sistema de log adequado
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        Connection con = null;
        try {
            if (ds == null) {
                Context context = new InitialContext();
                Context envCtx = (Context) context.lookup("java:comp/env");
                ds = (DataSource) envCtx.lookup(lookupName);
            }
            con = ds.getConnection();

        } catch (NamingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public void free(Connection conexao) {
        try {
            if (conexao != null) {
                conexao.close();
            }
        } catch (SQLException sql) {
            System.out.println("Erro: Liberar a conexao do pool");
        }
    }

    public void reap() {
    }
}
