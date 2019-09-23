package com.banking.api.exception;

public class DeviceAndYearConflictException extends RuntimeException {

    public DeviceAndYearConflictException() {
    }

    public DeviceAndYearConflictException(String message) {
        super(message);
    }
}
