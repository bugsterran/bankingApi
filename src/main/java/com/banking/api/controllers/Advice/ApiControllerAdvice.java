package com.banking.api.controllers.Advice;

import com.banking.api.controllers.DeviceApiController;
import com.banking.api.controllers.DeviceRateApiController;
import com.banking.api.domain.response.ResponseError;
import com.banking.api.exception.*;
import com.banking.api.exception.message.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * SPRING MVC 에서만 사용 가능 WEB-FLUX 사용 불가
 */
@Slf4j
@ControllerAdvice(basePackageClasses = {DeviceApiController.class, DeviceRateApiController.class})
public class ApiControllerAdvice {

    @ExceptionHandler(value= {Exception.class})
    @ResponseBody
    public ResponseEntity<ResponseError> handlerException(Exception ex, HttpServletRequest request) {

        ResponseError error = new ResponseError();
        error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        error.setMsg(HttpStatus.INTERNAL_SERVER_ERROR.name());
        error.setDetail(ErrorMessage.ERROR_INTERNAL_SERVER.getMessage());
        error.setPath(request.getRequestURL().toString());
        ex.printStackTrace();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidValueException.class)
    @ResponseBody
    public ResponseEntity<ResponseError> InvalidValueException(Exception ex, HttpServletRequest request) {
        log.error("handleEntityNotFoundException", ex);
        ResponseError error = new ResponseError();
        error.setCode(HttpStatus.BAD_REQUEST.toString());
        error.setMsg(HttpStatus.BAD_REQUEST.name());
        error.setDetail(ErrorMessage.ERROR_BAD_REQUEST.getMessage());
        error.setPath(request.getRequestURL().toString());
        ex.printStackTrace();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ResponseBody
    @ExceptionHandler(DeviceConflictException.class)
    public ResponseEntity<ResponseError> deviceConflictException(Exception ex, HttpServletRequest request) {
        log.error("deviceConflictException", ex);
        return getConflictErrorResponseEntity(ex, request, ErrorMessage.ERROR_DEVICE_CONFLICT);
    }

    @ResponseBody
    @ExceptionHandler(DeviceAndYearConflictException.class)
    public ResponseEntity<ResponseError> deviceAndYearConflictException(Exception ex, HttpServletRequest request) {
        log.error("deviceAndYearConflictException", ex);
        return getConflictErrorResponseEntity(ex, request, ErrorMessage.ERROR_DEVICE_AND_YEAR_CONFLICT);
    }

    @ResponseBody
    @ExceptionHandler(value= {DeviceNotFoundException.class})
    public ResponseEntity<ResponseError> deviceNotFoundException(Exception ex, HttpServletRequest request) {
        log.error("deviceNotFoundException", ex);
        return getNotFoundResponseEntity(ex, request, ErrorMessage.ERROR_DEVICE_NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(YearNotFoundException.class)
    public ResponseEntity<ResponseError> yearNotFoundException(Exception ex, HttpServletRequest request) {
        log.error("yearNotFoundException", ex);
        return getNotFoundResponseEntity(ex, request, ErrorMessage.ERROR_YEAR_NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ResponseError> dataNotFoundException(Exception ex, HttpServletRequest request) {
        log.error("DataNotFoundException", ex);
        return getNotFoundResponseEntity(ex, request, ErrorMessage.ERROR_NOT_FOUND);
    }

    private ResponseEntity<ResponseError> getConflictErrorResponseEntity(Exception ex,
                                                                         HttpServletRequest request,
                                                                         ErrorMessage errorDeviceAndYearConflict) {
        ResponseError error = new ResponseError();
        error.setCode(HttpStatus.CONFLICT.toString());
        error.setMsg(HttpStatus.CONFLICT.name());
        error.setDetail(errorDeviceAndYearConflict.getMessage());
        error.setPath(request.getRequestURL().toString());
        ex.printStackTrace();
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    private ResponseEntity<ResponseError> getNotFoundResponseEntity(Exception ex,
                                                                    HttpServletRequest request,
                                                                    ErrorMessage errorYearNotFound) {
        ResponseError error = new ResponseError();
        error.setCode(HttpStatus.NOT_FOUND.toString());
        error.setMsg(HttpStatus.NOT_FOUND.name());
        error.setDetail(errorYearNotFound.getMessage());
        error.setPath(request.getRequestURL().toString());
        ex.printStackTrace();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}
