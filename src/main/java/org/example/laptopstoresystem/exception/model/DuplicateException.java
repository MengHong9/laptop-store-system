package org.example.laptopstoresystem.exception.model;

public class DuplicateException extends RuntimeException{
    public DuplicateException(String message){
        super(message);
    }
}
