package com.banking.api.exception;

public class DeviceNotFoundException extends RuntimeException {

    public DeviceNotFoundException(){
        super();
    }
    public DeviceNotFoundException(String msg){
        super(msg);
    }
}
