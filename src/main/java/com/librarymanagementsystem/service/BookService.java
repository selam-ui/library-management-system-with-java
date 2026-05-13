package com.librarymanagementsystem.service;

import com.librarymanagementsystem.model.Book;
import com.librarymanagementsystem.dao.BookDAOImpl;
import com.librarymanagementsystem.exception.DatabaseException;
import com.librarymanagementsystem.exception.ValidationException;
import com.librarymanagementsystem.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
    private BookDAOImpl bookDAO = new BookDAOImpl();

    /**
     * Add a new book
     */
    public void addBook(Book book) throws ValidationException, DatabaseException {
        validateBook(book);
        bookDAO.addBook(book);
        logger.info("Book service: Book added - {}", book.getTitle());
    }

    /**
     * Get book by ID
     */
    public Book getBookById(int bookId) throws DatabaseException {
        Book book = bookDAO.getBookById(bookId);
        if (book == null) {
            throw new DatabaseException("Book not found with ID: " + bookId);
        }
        return book;
    }

    /**
     * Get all books
     */
    public List<Book> getAllBooks() throws DatabaseException {
        return bookDAO.getAllBooks();
    }

    /**
     * Search books
     */
    public List<Book> searchBooks(String keyword) throws ValidationException, DatabaseException {
        if (keyword == null || keyword.isEmpty()) {
            throw new ValidationException("Search keyword cannot be empty");
        }
        return bookDAO.searchBooks(keyword);
    }

    /**
     * Update book
     */
    public void updateBook(Book book) throws ValidationException, DatabaseException {
        validateBook(book);
        bookDAO.updateBook(book);
        logger.info("Book service: Book updated - {}", book.getTitle());
    }

    /**
     * Delete book
     */
    public void deleteBook(int bookId) throws DatabaseException {
        bookDAO.deleteBook(bookId);
        logger.info("Book service: Book deleted with ID - {}", bookId);
    }

    /**
     * Get books by category
     */
    public List<Book> getBooksByCategory(String category) throws ValidationException, DatabaseException {
        if (category == null || category.isEmpty()) {
            throw new ValidationException("Category cannot be empty");
        }
        return bookDAO.getBooksByCategory(category);
    }

    /**
     * Get available books
     */
    public List<Book> getAvailableBooks() throws DatabaseException {
        return bookDAO.getAvailableBooks();
    }

    /**
     * Validate book
     */
    private void validateBook(Book book) throws ValidationException {
        if (book == null) {
            throw new ValidationException("Book cannot be null");
        }

        Validator.validateStringLength(book.getTitle(), 1, 200, "Title");
        Validator.validateStringLength(book.getAuthor(), 1, 100, "Author");
        Validator.validateISBN(book.getIsbn());
        Validator.validateStringLength(book.getCategory(), 1, 50, "Category");
        Validator.validateNumberRange(book.getQuantity(), 0, 10000, "Quantity");
        Validator.validateNonNegative(book.getPrice(), "Price");
    }
}
