package com.librarymanagementsystem.dao;

import com.librarymanagementsystem.util.DBConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;

public class DBConnection {
    private static final Logger logger = LoggerFactory.getLogger(DBConnection.class);
    private static final int MAX_POOL_SIZE = 10;
    private static Queue<Connection> connectionPool;
    private static int activeConnections = 0;

    static {
        connectionPool = new LinkedList<>();
        initializePool();
    }

    /**
     * Initialize connection pool
     */
    private static void initializePool() {
        try {
            Class.forName(DBConfig.getDBDriver());
            logger.info("Database driver loaded successfully");
        } catch (ClassNotFoundException e) {
            logger.error("Error loading database driver", e);
        }
    }

    /**
     * Get connection from pool
     */
    public static synchronized Connection getConnection() throws DatabaseException {
        try {
            Connection connection;
            if (!connectionPool.isEmpty()) {
                connection = connectionPool.poll();
                if (connection.isValid(5)) {
                    return connection;
                }
            }

            if (activeConnections < MAX_POOL_SIZE) {
                connection = DriverManager.getConnection(
                        DBConfig.getDBUrl(),
                        DBConfig.getDBUsername(),
                        DBConfig.getDBPassword()
                );
                activeConnections++;
                logger.info("New database connection created. Active connections: " + activeConnections);
                return connection;
            }

            throw new DatabaseException("Connection pool exhausted");
        } catch (SQLException e) {
            logger.error("Error getting database connection", e);
            throw new DatabaseException("Error getting database connection", e);
        }
    }

    /**
     * Return connection to pool
     */
    public static synchronized void releaseConnection(Connection connection) {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connectionPool.add(connection);
                }
            } catch (SQLException e) {
                logger.error("Error returning connection to pool", e);
                try {
                    connection.close();
                    activeConnections--;
                } catch (SQLException ex) {
                    logger.error("Error closing connection", ex);
                }
            }
        }
    }

    /**
     * Close all connections
     */
    public static synchronized void closeAllConnections() {
        Connection connection;
        while ((connection = connectionPool.poll()) != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                    activeConnections--;
                }
            } catch (SQLException e) {
                logger.error("Error closing connection", e);
            }
        }
        logger.info("All database connections closed");
    }

    /**
     * Get pool statistics
     */
    public static String getPoolStats() {
        return "Active Connections: " + activeConnections + ", Pooled Connections: " + connectionPool.size();
    }
}
