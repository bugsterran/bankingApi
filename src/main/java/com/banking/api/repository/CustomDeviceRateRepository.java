package com.banking.api.repository;

import com.banking.api.domain.dto.DeviceRateDto;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomDeviceRateRepository {

    /**
     * 각 년도별 인터넷 뱅킹을 가장 많이 이용하는 접속기기 조회
     */
    List<DeviceRateDto> findTopRateDeviceEachYear();

    /**
     * 특정 년도를 입력받아 그 해에 인터넷뱅킹에 가장 많이 접속기기 조회
     */
    List<DeviceRateDto> findTopRateDeviceByYear(String year);

    /**
     * 디바이스 아이디를 입력받아 인터넷뱅킹에 접속 비율이 가장 많은 해 조회
     */
    List<DeviceRateDto> findTopRateYearByDeviceId(Long deviceId);

    /**
     * 디바이스 아이디를 입력받아 내년을 예측하기 위한 데이터 조회
     */
    List<DeviceRateDto> findRateByDeviceIdPredictNextYearRate(Long deviceId);

}
