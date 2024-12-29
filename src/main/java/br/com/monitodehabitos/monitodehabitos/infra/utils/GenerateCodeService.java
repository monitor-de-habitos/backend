package br.com.monitodehabitos.monitodehabitos.infra.utils;

import java.security.SecureRandom;

public class GenerateCodeService {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int CODE_LENGTH = 8;
    private SecureRandom secureRandom = new SecureRandom();
    private String newCode = "";

    public String generateRandomCode() {
        char[] newCodeArray = new char[CODE_LENGTH];
        int size = CHARACTERS.length();

        for (int i = 0; i < CODE_LENGTH; i++) {
            int position = secureRandom.nextInt(size);
            char character = CHARACTERS.charAt(position);
            newCodeArray[i] = character;        }
        this.newCode = new String(newCodeArray);
        return newCode;
    }
}
