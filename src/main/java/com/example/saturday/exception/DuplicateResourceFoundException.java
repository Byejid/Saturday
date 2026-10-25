package com.example.saturday.exception;

public class DuplicateResourceFoundException extends RuntimeException{
    public DuplicateResourceFoundException(String message){
        super(message);
    }
}
