package com.banking.api.domain.response;

import lombok.Data;

import java.util.List;

@Data
public class ResponseResult<T> {

    private List<T> result;

    public static class Builder<T> {

        private List<T> result;

        public Builder(List<T> result) {
            this.result = result;
        }

        public ResponseResult<T> build(){
            return new ResponseResult<T>(this);
        }
    }

    private ResponseResult(Builder builder){
        this.result = builder.result;
    }
}
