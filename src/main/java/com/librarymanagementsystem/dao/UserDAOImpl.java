package com.librarymanagementsystem.dao;

import com.librarymanagementsystem.model.User;
import com.librarymanagementsystem.exception.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

    @Override
    public User getUserById(int userId) throws DatabaseException {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapResultSetToUser(resultSet);
            }
            return null;
        } catch (SQLException e) {
            logger.error("Error fetching user by ID: {}", userId, e);
            throw new DatabaseException("Error fetching user", e);
        } finally {
            DBConnection.releaseConnection(connection);
        }
    }

    @Override
    public User getUserByUsername(String username) throws DatabaseException {
        String sql = "SELECT * FROM users WHERE username = ?";
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapResultSetToUser(resultSet);
            }
            return null;
        } catch (SQLException e) {
            logger.error("Error fetching user by username: {}", username, e);
            throw new DatabaseException("Error fetching user", e);
        } finally {
            DBConnection.releaseConnection(connection);
        }
    }

    @Override
    public User getUserByEmail(String email) throws DatabaseException {
        String sql = "SELECT * FROM users WHERE email = ?";
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapResultSetToUser(resultSet);
            }
            return null;
        } catch (SQLException e) {
            logger.error("Error fetching user by email: {}", email, e);
            throw new DatabaseException("Error fetching user", e);
        } finally {
            DBConnection.releaseConnection(connection);
        }
    }

    @Override
    public List<User> getAllUsers() throws DatabaseException {
        String sql = "SELECT * FROM users";
        List<User> users = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                users.add(mapResultSetToUser(resultSet));
            }
            return users;
        } catch (SQLException e) {
            logger.error("Error fetching all users", e);
            throw new DatabaseException("Error fetching users", e);
        } finally {
            DBConnection.releaseConnection(connection);
        }
    }

    @Override
    public List<User> getUsersByRole(String role) throws DatabaseException {
        String sql = "SELECT * FROM users WHERE role = ?";
        List<User> users = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, role);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                users.add(mapResultSetToUser(resultSet));
            }
            return users;
        } catch (SQLException e) {
            logger.error("Error fetching users by role: {}", role, e);
            throw new DatabaseException("Error fetching users", e);
        } finally {
            DBConnection.releaseConnection(connection);
        }
    }

    @Override
    public void addUser(User user) throws DatabaseException {
        String sql = "INSERT INTO users (username, email, password_hash, full_name, phone_number, role, registration_date, is_active) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPasswordHash());
            statement.setString(4, user.getFullName());
            statement.setString(5, user.getPhoneNumber());
            statement.setString(6, user.getRole());
            statement.setTimestamp(7, Timestamp.valueOf(user.getRegistrationDate()));
            statement.setBoolean(8, user.isActive());
            statement.executeUpdate();
            logger.info("User added successfully: {}", user.getUsername());
        } catch (SQLException e) {
            logger.error("Error adding user", e);
            throw new DatabaseException("Error adding user", e);
        } finally {
            DBConnection.releaseConnection(connection);
        }
    }

    @Override
    public void updateUser(User user) throws DatabaseException {
        String sql = "UPDATE users SET email = ?, full_name = ?, phone_number = ?, role = ?, is_active = ? WHERE user_id = ?";
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getFullName());
            statement.setString(3, user.getPhoneNumber());
            statement.setString(4, user.getRole());
            statement.setBoolean(5, user.isActive());
            statement.setInt(6, user.getUserId());
            statement.executeUpdate();
            logger.info("User updated successfully: {}", user.getUsername());
        } catch (SQLException e) {
            logger.error("Error updating user", e);
            throw new DatabaseException("Error updating user", e);
        } finally {
            DBConnection.releaseConnection(connection);
        }
    }

    @Override
    public void deleteUser(int userId) throws DatabaseException {
        String sql = "DELETE FROM users WHERE user_id = ?";
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.executeUpdate();
            logger.info("User deleted successfully with ID: {}", userId);
        } catch (SQLException e) {
            logger.error("Error deleting user", e);
            throw new DatabaseException("Error deleting user", e);
        } finally {
            DBConnection.releaseConnection(connection);
        }
    }

    @Override
    public void deactivateUser(int userId) throws DatabaseException {
        String sql = "UPDATE users SET is_active = false WHERE user_id = ?";
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.executeUpdate();
            logger.info("User deactivated with ID: {}", userId);
        } catch (SQLException e) {
            logger.error("Error deactivating user", e);
            throw new DatabaseException("Error deactivating user", e);
        } finally {
            DBConnection.releaseConnection(connection);
        }
    }

    @Override
    public void activateUser(int userId) throws DatabaseException {
        String sql = "UPDATE users SET is_active = true WHERE user_id = ?";
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.executeUpdate();
            logger.info("User activated with ID: {}", userId);
        } catch (SQLException e) {
            logger.error("Error activating user", e);
            throw new DatabaseException("Error activating user", e);
        } finally {
            DBConnection.releaseConnection(connection);
        }
    }

    private User mapResultSetToUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setUserId(resultSet.getInt("user_id"));
        user.setUsername(resultSet.getString("username"));
        user.setEmail(resultSet.getString("email"));
        user.setPasswordHash(resultSet.getString("password_hash"));
        user.setFullName(resultSet.getString("full_name"));
        user.setPhoneNumber(resultSet.getString("phone_number"));
        user.setRole(resultSet.getString("role"));
        user.setRegistrationDate(resultSet.getTimestamp("registration_date").toLocalDateTime());
        user.setActive(resultSet.getBoolean("is_active"));
        return user;
    }
}
