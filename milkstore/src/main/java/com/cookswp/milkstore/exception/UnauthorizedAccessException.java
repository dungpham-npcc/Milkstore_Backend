package com.cookswp.milkstore.exception;

public class UnauthorizedAccessException extends RuntimeException{
    public UnauthorizedAccessException(){
        super("Invalid access!");
    }
}
