package com.mv.schelokov.carent.model.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class ShaHash {
    
    private static final String SALT = "NuiF9cD32Kaw3";
    private final String password;
    
    public ShaHash(String password) {
        this.password = password;
    }
    
    public String getSHA512Hash() 
            throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(SALT.getBytes(StandardCharsets.UTF_8));
        byte[] bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }    
}
