package ru.itmentor.spring.boot_security.demo.exceptions;

import lombok.Data;

import java.util.Date;

@Data
public class AppMessage {
    private int status;
    private String message;
    private  Date timestamp;

    public AppMessage(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }
}
