package com.banking.api.exception;

public class DeviceConflictException extends RuntimeException {

    public DeviceConflictException() {
    }

    public DeviceConflictException(String message) {
        super(message);
    }
}
