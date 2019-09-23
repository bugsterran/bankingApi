package com.banking.api.controllers;

import com.banking.api.domain.dto.DeviceRateDto;
import com.banking.api.domain.response.ResponseDevices;
import com.banking.api.domain.response.ResponseResult;
import com.banking.api.exception.InvalidValueException;
import com.banking.api.module.CommonUtil;
import com.banking.api.service.DeviceRateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * Created by Wony on 2019-09-18.
 */
@Slf4j
@RestController
public class DeviceRateApiController {

    private DeviceRateService deviceRateService;

    DeviceRateApiController(DeviceRateService deviceRateService) {
        this.deviceRateService = deviceRateService;
    }

    @GetMapping("/v1/banking/top/device/eachyear")
    public ResponseDevices<DeviceRateDto> findTopDeviceEachYear() {
        return deviceRateService.findTopDeviceEachYear();
    }

    @GetMapping("/v1/banking/{year}/top/rate/device")
    public ResponseResult<DeviceRateDto> findTopRateDeviceByYear(@NotNull @PathVariable(name = "year") String year) {
        if (CommonUtil.isNotYearFormat(year)) {
            throw new InvalidValueException("NOT YEAR FORMAT");
        }
        return deviceRateService.findTopRateDeviceByYear(year);
    }

    @GetMapping("/v1/banking/{deviceId}/top/rate/year")
    public ResponseResult<DeviceRateDto> findTopRateYearByDeviceId(@NotNull
                                                                   @PathVariable(name = "deviceId") Long deviceId) {
        if (CommonUtil.isNotDeviceIdFormat(deviceId)) {
            throw new InvalidValueException("NOT DEVICE ID FORMAT");
        }
        return deviceRateService.findTopRateYearByDeviceId(deviceId);
    }

    @GetMapping("/v1/banking/predict/next/year/{deviceId}/rate")
    public DeviceRateDto getPredictNextYearRateByDeviceId(@PathVariable(name = "deviceId") Long deviceId){
        if (CommonUtil.isNotDeviceIdFormat(deviceId)) {
            throw new InvalidValueException("NOT DEVICE ID FORMAT");
        }
        return deviceRateService.getPredictNextYearRateByDeviceId(deviceId);
    }

}
