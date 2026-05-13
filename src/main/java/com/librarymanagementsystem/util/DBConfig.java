package com.librarymanagementsystem.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBConfig {
    private static final Logger logger = LoggerFactory.getLogger(DBConfig.class);
    private static Properties properties = new Properties();

    static {
        try {
            InputStream input = DBConfig.class.getClassLoader()
                    .getResourceAsStream("db.properties");
            if (input == null) {
                logger.error("db.properties file not found");
            } else {
                properties.load(input);
                logger.info("Database configuration loaded successfully");
            }
        } catch (IOException e) {
            logger.error("Error loading database configuration", e);
        }
    }

    /**
     * Get database URL
     */
    public static String getDBUrl() {
        return properties.getProperty("db.url", "jdbc:mysql://localhost:3306/library_db");
    }

    /**
     * Get database username
     */
    public static String getDBUsername() {
        return properties.getProperty("db.username", "root");
    }

    /**
     * Get database password
     */
    public static String getDBPassword() {
        return properties.getProperty("db.password", "");
    }

    /**
     * Get database driver
     */
    public static String getDBDriver() {
        return properties.getProperty("db.driver", "com.mysql.cj.jdbc.Driver");
    }

    /**
     * Get property value
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Get property value with default
     */
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}
