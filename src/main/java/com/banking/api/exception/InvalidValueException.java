package com.banking.api.exception;

public class InvalidValueException extends RuntimeException {

    public InvalidValueException(){
        super();
    }

    public InvalidValueException(String msg){
        super(msg);
    }
}
