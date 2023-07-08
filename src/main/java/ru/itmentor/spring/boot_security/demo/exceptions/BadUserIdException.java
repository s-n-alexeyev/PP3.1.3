package ru.itmentor.spring.boot_security.demo.exceptions;

public class BadUserIdException extends RuntimeException{
    public BadUserIdException(String message) {
        super(message);
    }
}
