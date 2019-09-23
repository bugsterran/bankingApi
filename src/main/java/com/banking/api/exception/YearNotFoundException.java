package com.banking.api.exception;

public class YearNotFoundException extends RuntimeException {

    public YearNotFoundException() {
        super();
    }

    public YearNotFoundException(String msg) {
        super(msg);
    }
}
