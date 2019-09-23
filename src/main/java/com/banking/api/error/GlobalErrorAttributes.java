/* WEBFLUX - GLOBAL EXCEPTION 처리
package com.banking.api.error;

import com.banking.api.exception.*;
import com.banking.api.exception.message.ErrorMessage;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    public GlobalErrorAttributes() {
        super(true);
    }

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
        Map<String, Object> map = super.getErrorAttributes(request, includeStackTrace);
        map.remove("trace");
        Throwable t = getError(request);

        if (getError(request) instanceof DeviceNotFoundException) {
            DeviceNotFoundException ex = (DeviceNotFoundException) getError(request);
            map.put("detail", ErrorMessage.ERROR_DEVICE_NOT_FOUND.getMessage());
            map.put("message", HttpStatus.NOT_FOUND.name());
            map.put("status", HttpStatus.NOT_FOUND.value());

            return map;
        } else if (getError(request) instanceof DeviceAndYearConflictException) {
            DeviceAndYearConflictException ex = (DeviceAndYearConflictException) getError(request);
            map.put("detail", ErrorMessage.ERROR_DEVICE_AND_YEAR_CONFLICT.getMessage());
            map.put("message", HttpStatus.NO_CONTENT.name());
            map.put("status", HttpStatus.NO_CONTENT.value());
            return map;
        } else if (getError(request) instanceof YearNotFoundException) {
            YearNotFoundException ex = (YearNotFoundException) getError(request);
            map.put("detail", ErrorMessage.ERROR_YEAR_NOT_FOUND.getMessage());
            map.put("message", HttpStatus.NOT_FOUND.name());
            map.put("status", HttpStatus.NOT_FOUND.value());
            return map;
        } else if (getError(request) instanceof DeviceConflictException) {
            DeviceConflictException ex = (DeviceConflictException) getError(request);
            map.put("detail", ErrorMessage.ERROR_DEVICE_CONFLICT.getMessage());
            map.put("message", HttpStatus.NO_CONTENT.name());
            map.put("status", HttpStatus.NO_CONTENT.value());
            return map;
        } else if (getError(request) instanceof InvalidValueException) {
            InvalidValueException ex = (InvalidValueException) getError(request);
            map.put("detail", ErrorMessage.ERROR_BAD_REQUEST.getMessage());
            map.put("message", HttpStatus.BAD_REQUEST.name());
            map.put("status", HttpStatus.BAD_REQUEST.value());
            return map;
        } else {
            Exception ex = (Exception) getError(request);
            map.put("detail", ErrorMessage.ERROR_INTERNAL_SERVER.getMessage());
            map.put("message", HttpStatus.INTERNAL_SERVER_ERROR.name());
            map.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return map;
        }
    }
}
*/
