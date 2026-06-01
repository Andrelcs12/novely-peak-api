package org.example.novelypeakapi.exception;

import java.time.LocalDateTime;

public record StandardError(
        String title,
        int status,
        String detail,
        LocalDateTime timestamp
) {

    public StandardError(String title, org.springframework.http.HttpStatus status, String detail) {
        this(title, status.value(), detail, LocalDateTime.now());
    }
}