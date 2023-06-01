package com.github.kylerequez.SpringBootUserRestApi.ExceptionHandlers;

import com.github.kylerequez.SpringBootUserRestApi.ExceptionHandlers.Exceptions.UserNotFound;
import com.github.kylerequez.SpringBootUserRestApi.Responses.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
public class UserExceptionHandler {
    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundExceptions(Exception e) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        String stackTrace = stringWriter.toString();

        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        e.getMessage(),
                        stackTrace
                ),
                status
        );
    }
}
