package com.example.exp;

public class AppMethodNotAllowedException extends RuntimeException{
    public AppMethodNotAllowedException(String message) {
        super(message);
    }
}
