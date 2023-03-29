package com.example.resourceserver.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception throws when account with already existing email is persisted
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public final class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException() {
        super();
    }

    public UserAlreadyExistsException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyExistsException(final String message) {
        super(message);
    }

    public UserAlreadyExistsException(final Throwable cause) {
        super(cause);
    }

}
