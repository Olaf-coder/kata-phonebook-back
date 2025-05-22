package com.kata.kataphonebookback.advice;

import java.time.LocalDateTime;

public class ApiError {
    private int code;
    private String message;
    private LocalDateTime timestamp;

    public ApiError(int code, String message, LocalDateTime timestamp) {
        this.code = code;
        this.message = message;
        this.timestamp = timestamp;
    }


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
