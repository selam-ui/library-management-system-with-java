package com.librarymanagementsystem.dao;

import com.librarymanagementsystem.model.User;
import com.librarymanagementsystem.exception.DatabaseException;
import java.util.List;

public interface UserDAO {
    User getUserById(int userId) throws DatabaseException;
    User getUserByUsername(String username) throws DatabaseException;
    User getUserByEmail(String email) throws DatabaseException;
    List<User> getAllUsers() throws DatabaseException;
    List<User> getUsersByRole(String role) throws DatabaseException;
    void addUser(User user) throws DatabaseException;
    void updateUser(User user) throws DatabaseException;
    void deleteUser(int userId) throws DatabaseException;
    void deactivateUser(int userId) throws DatabaseException;
    void activateUser(int userId) throws DatabaseException;
}
