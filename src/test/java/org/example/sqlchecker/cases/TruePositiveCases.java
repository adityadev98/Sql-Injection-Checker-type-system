package org.example.sqlchecker.cases;

import org.example.sqlchecker.annotation.Trusted;
import org.example.sqlchecker.annotation.Untrusted;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Nullable;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Optional;


public class TruePositiveCases {
    @Nullable Connection connection = null;
    @Nullable Statement statement = null;

    @Test
    public void testUnsafeQuery() {
        @Untrusted String maliciousInput = "' OR '1'='1";
        // Should fail type-checking because concatenating @Untrusted with string literal
        @Trusted String query = "SELECT * FROM users WHERE name = " + maliciousInput;
        //executeQuery(query);
        String expectedQuery = "SELECT * FROM users WHERE name = 'safeUser'";
        Assert.assertEquals("The query should not match the expected safe query", expectedQuery, query);
    }

    @Test
    public void testDirectUntrustedExecution() {
        @Untrusted String maliciousQuery = "SELECT * FROM users WHERE id = 1 OR 1=1";
        // This is the query you expect (a safe query)
        String expectedQuery = "SELECT * FROM users WHERE name = 'safeUser'";

        // Assert that the malicious query does not match the expected safe query
        Assert.assertEquals("The query should not match the expected safe query", expectedQuery, maliciousQuery);
    }

    @Test
    public void testMultipleUnsafeConcatenations() {
        @Untrusted String userId = "1 OR 1=1";
        @Untrusted String userName = "' OR '1'='1";

        // This concatenated query is unsafe
        @Trusted String query = "SELECT * FROM users WHERE id = " + userId + " AND name = " + userName;

        // Expected safe query for comparison
        String expectedQuery = "SELECT * FROM users WHERE id = 1 AND name = 'safeUser'";

        // Assert that the generated query does not match the expected safe query
        Assert.assertEquals("Unsafe query does not match the expected safe query", expectedQuery, query);
    }

    @Test
    public void testUnsafeStringFormat() {
        @Untrusted String maliciousInput = "' UNION SELECT * FROM sensitive_table; --";

        // This query includes an untrusted input
        @Trusted String query = String.format("SELECT * FROM users WHERE name = %s", maliciousInput);

        // Expected safe query for comparison
        String expectedQuery = "SELECT * FROM users WHERE name = 'safeUser'";

        // Assert that the generated query does not match the expected safe query
        Assert.assertEquals("Unsafe query does not match the expected safe query", expectedQuery, query);
    }

    public void executeQuery(@Trusted String query) {
        Connection connection = null;
        Statement statement = null;
        try {
            // Assume we have a method to get the database connection
            connection=getDatabaseConnection();
            statement= connection.createStatement();
            boolean hasResultSet = statement.execute(query);

            if (hasResultSet){
            } else {
                // If it was an update operation, you might want to get the update count
                int updateCount = statement.getUpdateCount();
                System.out.println("Rows affected: " + updateCount);
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    // This method should be implemented to return a database connection
    private Connection getDatabaseConnection() throws SQLException {
        // Implementation to get and return a database connection
        // For example:
        // return DriverManager.getConnection("jdbc:mysql://localhost:3306/yourdb", "username", "password");
        throw new UnsupportedOperationException("Database connection method not implemented");
    }
}