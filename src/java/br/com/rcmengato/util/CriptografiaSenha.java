/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.rcmengato.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Erick
 */

public class CriptografiaSenha {
    
    /**
     * Retorna a senha criptografada, utiliza o algoritmo SHA-384. É necessário o commons-codec-1.9.jar
     * @param senha - String com a senha a ser criptografada.
     * @return 
     */
    public static String encripta(String senha) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-384"); //utilização de SHA-384
            digest.update(senha.getBytes());
            Base64 encoder = new Base64();
            return encoder.encodeAsString(digest.digest()); // retorno de uma String de 64 caracteres
        } catch (NoSuchAlgorithmException ns){
            return senha;
        }
    }
}
