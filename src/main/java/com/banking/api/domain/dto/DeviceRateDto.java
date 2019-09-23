package com.banking.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DeviceRateDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long deviceId;
    private String deviceName;
    private String year;
    private BigDecimal rate;

    public DeviceRateDto(){

    }

    public DeviceRateDto(Builder builder) {
        this.deviceId = builder.deviceId;
        this.deviceName = builder.deviceName;
        this.year = builder.year;
        this.rate = builder.rate;
    }

    public static class Builder {

        private Long deviceId;
        private String deviceName;
        private String year;
        private BigDecimal rate;

        public Builder(){

        }

        public Builder setDeviceId(Long deviceId) {
            this.deviceId = deviceId;
            return this;
        }

        public Builder setDeviceName(String deviceName) {
            this.deviceName = deviceName;
            return this;
        }

        public Builder setYear(String year) {
            this.year = year;
            return this;
        }

        public Builder setRate(BigDecimal rate) {
            this.rate = rate;
            return this;
        }

        public DeviceRateDto build(){
            return new DeviceRateDto(this);
        }
    }

}
