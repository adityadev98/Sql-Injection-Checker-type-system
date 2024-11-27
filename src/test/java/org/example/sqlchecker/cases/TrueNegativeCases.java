package org.example.sqlchecker.cases;

import org.example.sqlchecker.annotation.Trusted;
import org.example.sqlchecker.annotation.Untrusted;
import org.example.sqlchecker.util.SQLSanitizer;

public class TrueNegativeCases {

    public void safeQuery(@Untrusted String userInput) {
        @Trusted String sanitized = SQLSanitizer.sanitize(userInput);
        @Trusted String query = "SELECT * FROM users WHERE name = " + sanitized; // Should pass
    }

    public void literalQuery() {
        @Trusted String query = "SELECT * FROM users"; // Should pass
    }
}