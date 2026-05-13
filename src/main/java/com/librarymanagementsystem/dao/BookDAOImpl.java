package com.librarymanagementsystem.dao;

import com.librarymanagementsystem.model.Book;
import com.librarymanagementsystem.exception.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAOImpl implements BookDAO {
    private static final Logger logger = LoggerFactory.getLogger(BookDAOImpl.class);

    @Override
    public Book getBookById(int bookId) throws DatabaseException {
        String sql = "SELECT * FROM books WHERE book_id = ?";
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, bookId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapResultSetToBook(resultSet);
            }
            return null;
        } catch (SQLException e) {
            logger.error("Error fetching book by ID: {}", bookId, e);
            throw new DatabaseException("Error fetching book by ID", e);
        } finally {
            DBConnection.releaseConnection(connection);
        }
    }

    @Override
    public List<Book> getAllBooks() throws DatabaseException {
        String sql = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                books.add(mapResultSetToBook(resultSet));
            }
            return books;
        } catch (SQLException e) {
            logger.error("Error fetching all books", e);
            throw new DatabaseException("Error fetching all books", e);
        } finally {
            DBConnection.releaseConnection(connection);
        }
    }

    @Override
    public List<Book> searchBooks(String keyword) throws DatabaseException {
        String sql = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ? OR isbn LIKE ?";
        List<Book> books = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            String searchPattern = "%" + keyword + "%";
            statement.setString(1, searchPattern);
            statement.setString(2, searchPattern);
            statement.setString(3, searchPattern);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                books.add(mapResultSetToBook(resultSet));
            }
            return books;
        } catch (SQLException e) {
            logger.error("Error searching books with keyword: {}", keyword, e);
            throw new DatabaseException("Error searching books", e);
        } finally {
            DBConnection.releaseConnection(connection);
        }
    }

    @Override
    public void addBook(Book book) throws DatabaseException {
        String sql = "INSERT INTO books (title, author, isbn, quantity, available_quantity, category, date_added, price, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getIsbn());
            statement.setInt(4, book.getQuantity());
            statement.setInt(5, book.getAvailableQuantity());
            statement.setString(6, book.getCategory());
            statement.setTimestamp(7, Timestamp.valueOf(book.getDateAdded()));
            statement.setDouble(8, book.getPrice());
            statement.setString(9, book.getDescription());
            statement.executeUpdate();
            logger.info("Book added successfully: {}", book.getTitle());
        } catch (SQLException e) {
            logger.error("Error adding book", e);
            throw new DatabaseException("Error adding book", e);
        } finally {
            DBConnection.releaseConnection(connection);
        }
    }

    @Override
    public void updateBook(Book book) throws DatabaseException {
        String sql = "UPDATE books SET title = ?, author = ?, isbn = ?, quantity = ?, available_quantity = ?, category = ?, price = ?, description = ? WHERE book_id = ?";
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getIsbn());
            statement.setInt(4, book.getQuantity());
            statement.setInt(5, book.getAvailableQuantity());
            statement.setString(6, book.getCategory());
            statement.setDouble(7, book.getPrice());
            statement.setString(8, book.getDescription());
            statement.setInt(9, book.getBookId());
            statement.executeUpdate();
            logger.info("Book updated successfully: {}", book.getTitle());
        } catch (SQLException e) {
            logger.error("Error updating book", e);
            throw new DatabaseException("Error updating book", e);
        } finally {
            DBConnection.releaseConnection(connection);
        }
    }

    @Override
    public void deleteBook(int bookId) throws DatabaseException {
        String sql = "DELETE FROM books WHERE book_id = ?";
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, bookId);
            statement.executeUpdate();
            logger.info("Book deleted successfully with ID: {}", bookId);
        } catch (SQLException e) {
            logger.error("Error deleting book with ID: {}", bookId, e);
            throw new DatabaseException("Error deleting book", e);
        } finally {
            DBConnection.releaseConnection(connection);
        }
    }

    @Override
    public List<Book> getBooksByCategory(String category) throws DatabaseException {
        String sql = "SELECT * FROM books WHERE category = ?";
        List<Book> books = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, category);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                books.add(mapResultSetToBook(resultSet));
            }
            return books;
        } catch (SQLException e) {
            logger.error("Error fetching books by category: {}", category, e);
            throw new DatabaseException("Error fetching books by category", e);
        } finally {
            DBConnection.releaseConnection(connection);
        }
    }

    @Override
    public List<Book> getAvailableBooks() throws DatabaseException {
        String sql = "SELECT * FROM books WHERE available_quantity > 0";
        List<Book> books = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                books.add(mapResultSetToBook(resultSet));
            }
            return books;
        } catch (SQLException e) {
            logger.error("Error fetching available books", e);
            throw new DatabaseException("Error fetching available books", e);
        } finally {
            DBConnection.releaseConnection(connection);
        }
    }

    @Override
    public void updateBookQuantity(int bookId, int quantity) throws DatabaseException {
        String sql = "UPDATE books SET available_quantity = available_quantity + ? WHERE book_id = ?";
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, quantity);
            statement.setInt(2, bookId);
            statement.executeUpdate();
            logger.info("Book quantity updated for book ID: {}", bookId);
        } catch (SQLException e) {
            logger.error("Error updating book quantity for book ID: {}", bookId, e);
            throw new DatabaseException("Error updating book quantity", e);
        } finally {
            DBConnection.releaseConnection(connection);
        }
    }

    private Book mapResultSetToBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setBookId(resultSet.getInt("book_id"));
        book.setTitle(resultSet.getString("title"));
        book.setAuthor(resultSet.getString("author"));
        book.setIsbn(resultSet.getString("isbn"));
        book.setQuantity(resultSet.getInt("quantity"));
        book.setAvailableQuantity(resultSet.getInt("available_quantity"));
        book.setCategory(resultSet.getString("category"));
        book.setDateAdded(resultSet.getTimestamp("date_added").toLocalDateTime());
        book.setPrice(resultSet.getDouble("price"));
        book.setDescription(resultSet.getString("description"));
        return book;
    }
}
