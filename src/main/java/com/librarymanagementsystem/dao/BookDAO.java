package com.librarymanagementsystem.dao;

import com.librarymanagementsystem.model.Book;
import com.librarymanagementsystem.exception.DatabaseException;
import java.util.List;

public interface BookDAO {
    Book getBookById(int bookId) throws DatabaseException;
    List<Book> getAllBooks() throws DatabaseException;
    List<Book> searchBooks(String keyword) throws DatabaseException;
    void addBook(Book book) throws DatabaseException;
    void updateBook(Book book) throws DatabaseException;
    void deleteBook(int bookId) throws DatabaseException;
    List<Book> getBooksByCategory(String category) throws DatabaseException;
    List<Book> getAvailableBooks() throws DatabaseException;
    void updateBookQuantity(int bookId, int quantity) throws DatabaseException;
}
