package com.grupo1.alojapp.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CloudFileNotFoundException extends RuntimeException {
    public CloudFileNotFoundException(String message) {
        super(message);
    }

    public CloudFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}