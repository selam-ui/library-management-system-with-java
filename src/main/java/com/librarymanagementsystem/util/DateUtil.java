package com.librarymanagementsystem.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {
    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final int BORROW_DURATION_DAYS = 14;

    /**
     * Get current date and time
     */
    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }

    /**
     * Format LocalDateTime to string
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.format(DATE_TIME_FORMATTER);
    }

    /**
     * Format LocalDateTime to date string
     */
    public static String formatDate(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.format(DATE_FORMATTER);
    }

    /**
     * Parse date string to LocalDateTime
     */
    public static LocalDateTime parseDateTime(String dateTimeString) {
        if (dateTimeString == null || dateTimeString.isEmpty()) {
            return null;
        }
        try {
            return LocalDateTime.parse(dateTimeString, DATE_TIME_FORMATTER);
        } catch (Exception e) {
            logger.error("Error parsing date time: {}", dateTimeString, e);
            return null;
        }
    }

    /**
     * Calculate due date (14 days from borrow date)
     */
    public static LocalDateTime calculateDueDate(LocalDateTime borrowDate) {
        if (borrowDate == null) {
            borrowDate = getCurrentDateTime();
        }
        return borrowDate.plusDays(BORROW_DURATION_DAYS);
    }

    /**
     * Calculate fine amount based on overdue days
     */
    public static double calculateFine(LocalDateTime dueDate, LocalDateTime returnDate) {
        if (returnDate == null) {
            returnDate = getCurrentDateTime();
        }

        if (returnDate.isBefore(dueDate) || returnDate.isEqual(dueDate)) {
            return 0.0;
        }

        long overdaysDays = ChronoUnit.DAYS.between(dueDate, returnDate);
        double finePerDay = 5.0; // 5 currency units per day
        return overdaysDays * finePerDay;
    }

    /**
     * Check if borrow is overdue
     */
    public static boolean isOverdue(LocalDateTime dueDate) {
        return getCurrentDateTime().isAfter(dueDate);
    }

    /**
     * Get days remaining until due date
     */
    public static long getDaysRemaining(LocalDateTime dueDate) {
        return ChronoUnit.DAYS.between(getCurrentDateTime(), dueDate);
    }
}
