package com.banking.api.service;

import com.banking.api.domain.dto.DeviceRateDto;
import com.banking.api.domain.response.ResponseDevices;
import com.banking.api.domain.response.ResponseResult;
import com.banking.api.exception.DataNotFoundException;
import com.banking.api.exception.DeviceNotFoundException;
import com.banking.api.exception.YearNotFoundException;
import com.banking.api.repository.DeviceRateRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class DeviceRateService {

    private final DeviceRateRepository deviceRateRepository;

    DeviceRateService(DeviceRateRepository deviceRateRepository){
        this.deviceRateRepository = deviceRateRepository;
    }

    public ResponseDevices<DeviceRateDto> findTopDeviceEachYear() {
        List<DeviceRateDto> list = deviceRateRepository.findTopRateDeviceEachYear();
        if(list.isEmpty()){
            throw new DataNotFoundException("REQUEST DATA IS NULL");
        }
        return new ResponseDevices.Builder<DeviceRateDto>(list).build();
    }

    public ResponseResult<DeviceRateDto> findTopRateDeviceByYear(String year){
        List<DeviceRateDto> list = deviceRateRepository.findTopRateDeviceByYear(year);
        if(list.isEmpty()){
            throw new YearNotFoundException("DATA NOT FOUND FIND BY YEAR");
        }
        return new ResponseResult.Builder<DeviceRateDto>(list).build();
    }

    public ResponseResult<DeviceRateDto> findTopRateYearByDeviceId(Long deviceId){
        List<DeviceRateDto> list = deviceRateRepository.findTopRateYearByDeviceId(deviceId);
        if(list.isEmpty()){
            throw new DeviceNotFoundException("DATA NOT FOUND FIND BY DEVICE");
        }
        return new ResponseResult.Builder<DeviceRateDto>(list).build();
    }

    public DeviceRateDto getPredictNextYearRateByDeviceId(Long deviceId){
        List<DeviceRateDto> list = deviceRateRepository.findRateByDeviceIdPredictNextYearRate(deviceId);
        if(list.isEmpty()){
            throw new DeviceNotFoundException("DATA NOT FOUND FIND BY DEVICE");
        }
        return calcPredictRate(list);
    }

    /**
     * 예측 데이터 생성 한다.
     * @param list
     * @return
     */
    private DeviceRateDto calcPredictRate(List<DeviceRateDto> list){

        //단순 회귀 분석 라이브러리
        SimpleRegression reg = new SimpleRegression();
        //데이터를 담는다.
        list.stream().forEach(dto -> {
            reg.addData(Double.parseDouble(dto.getYear()), Double.parseDouble(dto.getRate().toString()));
        });

        //최근 년도 데이터 추출
        DeviceRateDto resultDto = list.stream()
                .sorted((o1, o2) -> o2.getYear().compareToIgnoreCase(o1.getYear()))
                .collect(Collectors.toList()).get(0);

        int year = Integer.parseInt(resultDto.getYear()) + 1;
        double rate = Double.parseDouble(resultDto.getRate().toString());
        //평균 기울기 계산
        double slope = reg.getSlope();
        //평균 기울기에서 100 또는 0에 가까워 질수록 기울기는 완만해지기 때문에 가공해준다.
        double expect;
        if(0 > slope + rate){
            expect = rate + (slope * rate/100);
        }else{
            expect = rate + slope * (1 - rate/100);
        }

        BigDecimal bd = new BigDecimal(expect);
        resultDto.setRate(bd.setScale(1, BigDecimal.ROUND_DOWN));
        resultDto.setYear(String.valueOf(year));

        return resultDto;
    }

}
