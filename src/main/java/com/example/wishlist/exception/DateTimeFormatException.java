package com.example.wishlist.exception;

public class DateTimeFormatException extends RuntimeException {
    public DateTimeFormatException() {
        super("Datoen er ugyldig");
    }
}
