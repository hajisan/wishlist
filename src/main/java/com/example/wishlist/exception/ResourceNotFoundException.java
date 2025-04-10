package com.example.wishlist.exception;

public class ResourceNotFoundException extends RuntimeException {
    // Arver fra RunTimeException = behøver ikke throws i metoder (unchecked exception)
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
