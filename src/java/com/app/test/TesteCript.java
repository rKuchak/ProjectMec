/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.test;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author macuser
 */
public class TesteCript {

    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        
        String senha = "1";
        
        MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
        byte messageDigest[] = algorithm.digest(senha.getBytes("UTF-8"));
        
        System.out.println(messageDigest);
        
        StringBuilder sb = new StringBuilder();
        for (byte b : messageDigest) {
            sb.append(String.format("%02X", 0xFF & b));
        }
        
        String senhaHex = sb.toString();
        
        System.out.println("Senha Hex Cripto: " + senhaHex);
        
       
 
    }

}
