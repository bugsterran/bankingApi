package com.banking.api.domain.response;

import lombok.Data;

import java.util.List;

@Data
public class ResponseDevices<T> {
    private List<T> devices;

    public static class Builder<T> {

        private List<T> devices;

        public Builder(List<T> devices) {
            this.devices = devices;
        }

        public ResponseDevices<T> build(){
            return new ResponseDevices<T>(this);
        }
    }

    private ResponseDevices(Builder builder){
        this.devices = builder.devices;
    }
}
