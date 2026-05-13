package com.librarymanagementsystem.service;

import com.librarymanagementsystem.model.User;
import com.librarymanagementsystem.dao.UserDAOImpl;
import com.librarymanagementsystem.exception.AuthenticationException;
import com.librarymanagementsystem.exception.DatabaseException;
import com.librarymanagementsystem.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private UserDAOImpl userDAO = new UserDAOImpl();
    private User currentUser;

    /**
     * Login user
     */
    public User login(String username, String password) throws AuthenticationException {
        try {
            User user = userDAO.getUserByUsername(username);
            if (user == null) {
                throw new AuthenticationException("User not found");
            }

            if (!user.isActive()) {
                throw new AuthenticationException("User account is deactivated");
            }

            if (!PasswordUtil.verifyPassword(password, user.getPasswordHash())) {
                throw new AuthenticationException("Invalid password");
            }

            this.currentUser = user;
            logger.info("User logged in successfully: {}", username);
            return user;
        } catch (DatabaseException e) {
            logger.error("Database error during login", e);
            throw new AuthenticationException("Login failed", e);
        }
    }

    /**
     * Logout user
     */
    public void logout() {
        if (currentUser != null) {
            logger.info("User logged out: {}", currentUser.getUsername());
            currentUser = null;
        }
    }

    /**
     * Get current user
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Check if user is logged in
     */
    public boolean isLoggedIn() {
        return currentUser != null;
    }
}
