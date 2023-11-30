package ua.com.alevel.web.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * GlobalExceptionHandler is a class that provides centralized exception handling for the entire application.
 * It uses Spring's RestControllerAdvice to handle exceptions globally across all controllers.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles MethodArgumentTypeMismatchException, which occurs when there is a mismatch in method argument types,
     * such as attempting to convert a String to a numeric value.
     *
     * @param ex The MethodArgumentTypeMismatchException that occurred.
     * @return ResponseEntity with a BAD_REQUEST status and an error message indicating the invalid parameter value.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Invalid parameter value. " + ex.getMessage());
    }
}
