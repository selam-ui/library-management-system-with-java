package com.librarymanagementsystem.dao;

import com.librarymanagementsystem.model.BorrowRecord;
import com.librarymanagementsystem.exception.DatabaseException;
import java.util.List;

public interface BorrowDAO {
    BorrowRecord getBorrowById(int borrowId) throws DatabaseException;
    List<BorrowRecord> getBorrowsByUserId(int userId) throws DatabaseException;
    List<BorrowRecord> getBorrowsByBookId(int bookId) throws DatabaseException;
    List<BorrowRecord> getActiveBorrows() throws DatabaseException;
    List<BorrowRecord> getOverdueBorrows() throws DatabaseException;
    void addBorrow(BorrowRecord borrowRecord) throws DatabaseException;
    void updateBorrow(BorrowRecord borrowRecord) throws DatabaseException;
    void returnBook(int borrowId) throws DatabaseException;
    void updateFine(int borrowId, double fine) throws DatabaseException;
    List<BorrowRecord> getAllBorrowRecords() throws DatabaseException;
}
