package com.banking.api.exception.message;

public enum ErrorMessage {

    ERROR_BAD_REQUEST("400","요청에 대한 올바르지 않은 구문이 제공되었습니다.(요청 경로 확인)"),
    ERROR_ACCESS_DENIED("403", "접근이 불가능 합니다.(관리자에게 문의)"),
    ERROR_NOT_FOUND("404", "요청한 자원을 찾을 수 없습니다."),
    ERROR_DEVICE_NOT_FOUND("404", "요청한 자원을 찾을 수 없습니다.(입력하신 DEVICE ID를 확인)"),
    ERROR_YEAR_NOT_FOUND("404", "요청한 자원을 찾을 수 없습니다.(입력하신 년도 확인)"),
    ERROR_DEVICE_CONFLICT("409", "자원의 현재 상태와 충돌되기 때문에 요청을 완료할 수 없습니다.(등록정보(DEVICE ID) 확인)"),
    ERROR_DEVICE_AND_YEAR_CONFLICT("409", "자원의 현재 상태와 충돌되기 때문에 요청을 완료할 수 없습니다.(등록정보(DEVICE ID && 년도) 확인)"),
    ERROR_INTERNAL_SERVER("500", "예기치 않은 내부 서버 오류가 발생했습니다.(관리자에게 문의)");

    private String code;
    private String message;

    private ErrorMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return this.code;
    }
}
