package com.github.kylerequez.SpringBootUserRestApi.ExceptionHandlers.Exceptions;

public class UserNotFound extends RuntimeException {
    public UserNotFound() {
        super();
    }

    public UserNotFound(String message) {
        super(message);
    }
}
