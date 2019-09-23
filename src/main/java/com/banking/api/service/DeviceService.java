package com.banking.api.service;

import com.banking.api.domain.Device;
import com.banking.api.domain.response.ResponseDevices;
import com.banking.api.exception.DataNotFoundException;
import com.banking.api.exception.DeviceNotFoundException;
import com.banking.api.repository.DeviceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DeviceService {

    private DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository){
        this.deviceRepository = deviceRepository;
    }

    public ResponseDevices<Device> findDevices() {
        //return Mono.fromCallable(() -> {
        List<Device> list = deviceRepository.findAll();
        if(list.isEmpty()){
            throw new DataNotFoundException("DEVICE DATA IS NULL");
        }
        return new ResponseDevices.Builder<Device>(list).build();
        //}).subscribeOn(Schedulers.elastic()).log();
    }

    public Device findDevice(Long deviceId) {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new DeviceNotFoundException("DEVICE NOT FOUND"));
        return device;
    }

    public void deleteDevice(Long deviceId) {
        deviceRepository.deleteById(deviceId);
    }
}
