/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.santiagocontreras.utils;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author informatica
 */
public class PasswordUtils {
    private static PasswordUtils instance;
    
    private PasswordUtils(){   
    }

    public static PasswordUtils getInstance() {
        if(instance == null){
            instance = new PasswordUtils();
        }
        return instance;
    }
    
    public String encryptedPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());  // Encripta la password
    }
    
    public boolean checkPassword(String pass,String encryptedPass){
        return BCrypt.checkpw(pass, encryptedPass);   // Verifica si la password ingresada es la de la base de datos, la correcta
    }
    
}
