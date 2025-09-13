package main.java.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtils {

    // Gera o hash em SHA-256
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao criptografar senha", e);
        }
    }

    // Compara a senha informada com o hash salvo no banco
    public static boolean verifyPassword(String password, String hashedPassword) {
        String hashDaSenhaDigitada = hashPassword(password);
        return hashDaSenhaDigitada.equals(hashedPassword);
    }
}
