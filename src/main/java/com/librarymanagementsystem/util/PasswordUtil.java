package com.librarymanagementsystem.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PasswordUtil {
    private static final Logger logger = LoggerFactory.getLogger(PasswordUtil.class);
    private static final int MINIMUM_PASSWORD_LENGTH = 8;

    /**
     * Hash password using SHA-256
     */
    public static String hashPassword(String password) throws Exception {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            logger.error("Error hashing password", e);
            throw new Exception("Error hashing password", e);
        }
    }

    /**
     * Verify password against hash
     */
    public static boolean verifyPassword(String password, String hash) {
        try {
            String passwordHash = hashPassword(password);
            return passwordHash.equals(hash);
        } catch (Exception e) {
            logger.error("Error verifying password", e);
            return false;
        }
    }

    /**
     * Validate password strength
     */
    public static boolean isPasswordStrong(String password) {
        if (password == null || password.length() < MINIMUM_PASSWORD_LENGTH) {
            return false;
        }

        boolean hasUpperCase = password.matches(".*[A-Z].*");
        boolean hasLowerCase = password.matches(".*[a-z].*");
        boolean hasDigits = password.matches(".*\\d.*");
        boolean hasSpecialChar = password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};:'\",.<>?/`~|\\\\].*");

        return hasUpperCase && hasLowerCase && hasDigits && hasSpecialChar;
    }

    /**
     * Generate random salt for password
     */
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
}
