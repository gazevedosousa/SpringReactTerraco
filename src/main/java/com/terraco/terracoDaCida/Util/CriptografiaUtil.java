package com.terraco.terracoDaCida.Util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CriptografiaUtil {

    public static byte[] criptografar(String valor) throws NoSuchAlgorithmException {
        MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
        return algorithm.digest(valor.getBytes(StandardCharsets.UTF_8));
    }
}
