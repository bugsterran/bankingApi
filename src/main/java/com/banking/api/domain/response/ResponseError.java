package com.banking.api.domain.response;

import lombok.Data;

@Data
public class ResponseError {

    private String code;
    private String msg;
    private String detail;
    private String path;

}
