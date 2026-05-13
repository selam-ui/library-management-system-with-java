package com.librarymanagementsystem.util;

import java.util.regex.Pattern;
import com.librarymanagementsystem.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Validator {
    private static final Logger logger = LoggerFactory.getLogger(Validator.class);

    // Regex patterns
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final String PHONE_PATTERN = "^[0-9]{10,15}$";
    private static final String ISBN_PATTERN = "^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?:(?=(?:[0-9]+[- ]){3})[0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[0-9]{17}$)|(?:97[89][- ]?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9]))";  
    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9_]{3,20}$";

    /**
     * Validate email format
     */
    public static void validateEmail(String email) throws ValidationException {
        if (email == null || email.isEmpty()) {
            throw new ValidationException("Email cannot be null or empty");
        }

        if (!Pattern.matches(EMAIL_PATTERN, email)) {
            throw new ValidationException("Invalid email format: " + email);
        }
    }

    /**
     * Validate phone number
     */
    public static void validatePhoneNumber(String phoneNumber) throws ValidationException {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            throw new ValidationException("Phone number cannot be null or empty");
        }

        if (!Pattern.matches(PHONE_PATTERN, phoneNumber)) {
            throw new ValidationException("Invalid phone number format: " + phoneNumber);
        }
    }

    /**
     * Validate ISBN
     */
    public static void validateISBN(String isbn) throws ValidationException {
        if (isbn == null || isbn.isEmpty()) {
            throw new ValidationException("ISBN cannot be null or empty");
        }

        String cleanISBN = isbn.replaceAll("-", "");
        if (!Pattern.matches("[0-9]{10}|[0-9]{13}", cleanISBN)) {
            throw new ValidationException("Invalid ISBN format: " + isbn);
        }
    }

    /**
     * Validate username
     */
    public static void validateUsername(String username) throws ValidationException {
        if (username == null || username.isEmpty()) {
            throw new ValidationException("Username cannot be null or empty");
        }

        if (!Pattern.matches(USERNAME_PATTERN, username)) {
            throw new ValidationException("Username must be 3-20 characters, alphanumeric and underscore only");
        }
    }

    /**
     * Validate string length
     */
    public static void validateStringLength(String value, int minLength, int maxLength, String fieldName) throws ValidationException {
        if (value == null) {
            throw new ValidationException(fieldName + " cannot be null");
        }

        if (value.length() < minLength) {
            throw new ValidationException(fieldName + " must be at least " + minLength + " characters");
        }

        if (value.length() > maxLength) {
            throw new ValidationException(fieldName + " cannot exceed " + maxLength + " characters");
        }
    }

    /**
     * Validate number range
     */
    public static void validateNumberRange(int value, int min, int max, String fieldName) throws ValidationException {
        if (value < min || value > max) {
            throw new ValidationException(fieldName + " must be between " + min + " and " + max);
        }
    }

    /**
     * Validate non-negative number
     */
    public static void validateNonNegative(double value, String fieldName) throws ValidationException {
        if (value < 0) {
            throw new ValidationException(fieldName + " cannot be negative");
        }
    }
}
