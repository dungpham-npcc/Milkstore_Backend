package com.cookswp.milkstore.exception;

public class MissingRequiredFieldException extends RuntimeException{
    public MissingRequiredFieldException(String fieldName){
        super(fieldName + " cannot be blank");
    }
}
