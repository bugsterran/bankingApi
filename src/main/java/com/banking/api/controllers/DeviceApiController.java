package com.banking.api.controllers;

import com.banking.api.domain.Device;
import com.banking.api.domain.response.ResponseDevices;
import com.banking.api.exception.InvalidValueException;
import com.banking.api.module.CommonUtil;
import com.banking.api.service.DeviceService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
public class DeviceApiController {

    private DeviceService deviceService;

    public DeviceApiController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping("/v1/banking/device/all")
    public ResponseDevices<Device> findDevices() {
        return deviceService.findDevices();
    }

    @GetMapping("/v1/banking/device/{deviceId}")
    public Device findDevice(@NotNull @PathVariable(name = "deviceId") Long deviceId) {
        if (CommonUtil.isNotDeviceIdFormat(deviceId)) {
            throw new InvalidValueException("NOT DEVICE ID FORMAT");
        }
        return deviceService.findDevice(deviceId);
    }

    @DeleteMapping("/v1/banking/delete/device/{deviceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDeviceById(@NotNull @PathVariable("deviceId") Long deviceId) {
        if (CommonUtil.isNotDeviceIdFormat(deviceId)) {
            throw new InvalidValueException("NOT DEVICE ID FORMAT");
        }
        deviceService.deleteDevice(deviceId);
    }

    /*
    @PostMapping("/v1/banking/register/device")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveDevice(@NotNull String deviceName) {
        //To-Do
    }

    @PutMapping("/v1/banking/modify/device/{deviceId}")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public void modifyDevice(@NotNull @PathVariable("deviceId") String deviceId,
                             @NotNull String deviceName) {
        //To-Do
    }
    */
}
