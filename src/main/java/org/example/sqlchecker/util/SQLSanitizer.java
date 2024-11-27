package org.example.sqlchecker.util;

import org.example.sqlchecker.annotation.Trusted;
import org.example.sqlchecker.annotation.Untrusted;

public class SQLSanitizer {
    @Trusted
    public static String prepareSQLValue(@Untrusted String input) {
        return "'" + sanitize(input) + "'";
    }
    @Trusted
    public static String sanitize(@Untrusted String input) {
        if (input == null) {
            return "";
        }

        // Basic SQL injection prevention
        return input.replaceAll("['\"\\\\]", "")  // Remove quotes and backslashes
                .replaceAll("--;?", "")        // Remove comment markers
                .replaceAll("/\\*.*?\\*/", "") // Remove block comments
                .replaceAll("\\s+", " ")       // Normalize whitespace
                .trim();
    }


}