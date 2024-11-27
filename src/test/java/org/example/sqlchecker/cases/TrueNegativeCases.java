package org.example.sqlchecker.cases;

import org.example.sqlchecker.annotation.Trusted;
import org.example.sqlchecker.annotation.Untrusted;
import org.example.sqlchecker.util.SQLSanitizer;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Nullable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TrueNegativeCases {
    @Nullable
    Connection connection = null;
    @Nullable
    Statement statement = null;
    @Test
    public void safeQuery() {
        @Untrusted String userInput = "' OR '1'='1";
        @Trusted String sanitized = SQLSanitizer.sanitize(userInput);
        @Trusted String query = "SELECT * FROM users WHERE name = " + sanitized; // Should pass

        String expectedQuery = "SELECT * FROM users WHERE name = " + SQLSanitizer.sanitize(userInput);
        Assert.assertEquals(expectedQuery, query);
    }

    @Test
    public void literalQuery() {
        @Trusted String query = "SELECT * FROM users"; // Should pass

        Assert.assertEquals("SELECT * FROM users", query);
    }

    @Test
    public void preparedStatementQuery() {
        @Untrusted String username = "admin";
        @Trusted String query = "SELECT * FROM users WHERE username = ?";

        Assert.assertEquals("SELECT * FROM users WHERE username = ?", query);
    }

    @Test
    public void multiInputQuery() {
        @Untrusted String firstName = "John";
        @Untrusted String lastName = "Doe";

        @Trusted String sanitizedFirst = SQLSanitizer.sanitize(firstName);
        @Trusted String sanitizedLast = SQLSanitizer.sanitize(lastName);
        @Trusted String query = "SELECT * FROM users WHERE first_name = " +
                sanitizedFirst + " AND last_name = " + sanitizedLast;

        String expectedQuery = "SELECT * FROM users WHERE first_name = " +
                SQLSanitizer.sanitize(firstName) + " AND last_name = " + SQLSanitizer.sanitize(lastName);
        Assert.assertEquals(expectedQuery, query);
    }

    @Test
    public void numericQuery() {
        @Untrusted String ageInput = "25";
        @Trusted String sanitizedAge = SQLSanitizer.sanitize(ageInput);
        @Trusted String query = "SELECT * FROM users WHERE age > " + sanitizedAge;

        String expectedQuery = "SELECT * FROM users WHERE age > " + SQLSanitizer.sanitize(ageInput);
        Assert.assertEquals(expectedQuery, query);
    }

    @Test
    public void complexSafeQuery() {
        @Untrusted String department = "Engineering";
        @Untrusted String status = "Active";

        @Trusted String sanitizedDept = SQLSanitizer.sanitize(department);
        @Trusted String sanitizedStatus = SQLSanitizer.sanitize(status);

        @Trusted String query = "SELECT * FROM employees WHERE " +
                "department = " + sanitizedDept +
                " AND status = " + sanitizedStatus;

        String expectedQuery = "SELECT * FROM employees WHERE " +
                "department = " + SQLSanitizer.sanitize(department) +
                " AND status = " + SQLSanitizer.sanitize(status);
        Assert.assertEquals(expectedQuery, query);
    }

    @Test
    public void constantConcatenation() {
        @Untrusted String userRole = "admin";
        @Trusted String sanitizedRole = SQLSanitizer.sanitize(userRole);
        @Trusted String baseQuery = "SELECT * FROM permissions ";
        @Trusted String roleCondition = "WHERE role = " + sanitizedRole;
        @Trusted String fullQuery = baseQuery + roleCondition;

        String expectedQuery = "SELECT * FROM permissions WHERE role = " + SQLSanitizer.sanitize(userRole);
        Assert.assertEquals(expectedQuery, fullQuery);
    }

    @Test
    public void escapedQuery() {
        @Untrusted String comment = "This is a test comment";
        @Trusted String sanitizedComment = SQLSanitizer.sanitize(comment);
        @Trusted String query = "INSERT INTO comments (text) VALUES (" + sanitizedComment + ")";

        String expectedQuery = "INSERT INTO comments (text) VALUES (" + SQLSanitizer.sanitize(comment) + ")";
        Assert.assertEquals(expectedQuery, query);
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
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/yourdb", "username", "password");
        //throw new UnsupportedOperationException("Database connection method not implemented");
    }
}