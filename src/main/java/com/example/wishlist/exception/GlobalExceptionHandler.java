package com.example.wishlist.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice //Håndterer HTTP fejl-respons
public class GlobalExceptionHandler {

    //Fejlbesked med 404-respons med vores egen 'not found exception'
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleNotFound(ResourceNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
    //Client får eks. denne besked:
    //"WishList with ID 'eks.12' not found."

    //Fejlbesked om foreign key violations
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleSQLConstraint(DataIntegrityViolationException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Error in database: Constraint violation - check relationship or input date");
    }

    //Håndterer generelle fallbacks
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUnexpected(Exception e) {
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Something went wrong. Try again");
    }
}
