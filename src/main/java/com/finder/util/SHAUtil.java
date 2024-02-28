package com.finder.util;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class SHAUtil {
    public static final SecureRandom random = new SecureRandom();

    public static String getSalt() {
        byte[] salt = new byte[32];
        random.nextBytes(salt);

        return Base64.getEncoder().encodeToString(salt);
    }

    public static String encryptWithSalt(String plainText, String salt) {
        return Hashing.sha256().hashString(plainText + salt, StandardCharsets.UTF_8).toString();
    }
}
