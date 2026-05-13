package com.librarymanagementsystem.config;

public class Constants {
    // User Roles
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_LIBRARIAN = "LIBRARIAN";
    public static final String ROLE_MEMBER = "MEMBER";

    // Borrow Settings
    public static final int BORROW_DURATION_DAYS = 14;
    public static final double FINE_PER_DAY = 5.0;
    public static final int MAX_BOOKS_PER_MEMBER = 5;

    // Validation Rules
    public static final int MIN_USERNAME_LENGTH = 3;
    public static final int MAX_USERNAME_LENGTH = 20;
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MAX_PASSWORD_LENGTH = 50;
    public static final int MIN_FULLNAME_LENGTH = 2;
    public static final int MAX_FULLNAME_LENGTH = 100;
    public static final int MIN_PHONE_LENGTH = 10;
    public static final int MAX_PHONE_LENGTH = 15;
    public static final int MIN_TITLE_LENGTH = 1;
    public static final int MAX_TITLE_LENGTH = 200;
    public static final int MIN_AUTHOR_LENGTH = 1;
    public static final int MAX_AUTHOR_LENGTH = 100;
    public static final int MIN_ISBN_LENGTH = 10;
    public static final int MAX_ISBN_LENGTH = 20;
    public static final int MIN_CATEGORY_LENGTH = 1;
    public static final int MAX_CATEGORY_LENGTH = 50;

    // Borrow Status
    public static final String BORROW_STATUS_ACTIVE = "ACTIVE";
    public static final String BORROW_STATUS_RETURNED = "RETURNED";
    public static final String BORROW_STATUS_OVERDUE = "OVERDUE";

    // Error Messages
    public static final String ERROR_DATABASE = "Database operation failed";
    public static final String ERROR_AUTHENTICATION = "Authentication failed";
    public static final String ERROR_VALIDATION = "Validation failed";
    public static final String ERROR_NOT_FOUND = "Resource not found";
    public static final String ERROR_INVALID_CREDENTIALS = "Invalid username or password";
    public static final String ERROR_USER_EXISTS = "User already exists";
    public static final String ERROR_BOOK_NOT_AVAILABLE = "Book is not available";
    public static final String ERROR_BOOK_LIMIT_EXCEEDED = "Maximum book borrow limit exceeded";

    // Success Messages
    public static final String SUCCESS_LOGIN = "Login successful";
    public static final String SUCCESS_REGISTRATION = "Registration successful";
    public static final String SUCCESS_BORROW = "Book borrowed successfully";
    public static final String SUCCESS_RETURN = "Book returned successfully";
}
