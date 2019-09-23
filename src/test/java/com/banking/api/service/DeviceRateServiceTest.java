package com.banking.api.service;

import com.banking.api.domain.dto.DeviceRateDto;
import com.banking.api.domain.response.ResponseDevices;
import com.banking.api.domain.response.ResponseResult;
import com.banking.api.exception.DataNotFoundException;
import com.banking.api.exception.DeviceNotFoundException;
import com.banking.api.exception.YearNotFoundException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeviceRateServiceTest {

    @Autowired
    private DeviceRateService deviceRateService;

    @Autowired
    private ImportDataService importDataService;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        //초기 데이터 셋팅
        //importDataService.importData();
    }

    @Test
    @Ignore
    public void findTopDeviceEachYear() {
        //실행
        ResponseDevices<DeviceRateDto> result = deviceRateService.findTopDeviceEachYear();
        //확인
        //데이터 존재 여부
        assertTrue(!result.getDevices().isEmpty());
        //중복 조회 체크
        assertTrue(result.getDevices().stream().distinct().count() != result.getDevices().size());
        //데이터 정상적으로 조회 확인(년도별 데이터) 임의 년도 입력
        assertTrue(result.getDevices()
                .stream()
                .anyMatch(deviceRateDto -> deviceRateDto.getYear().equals("2018")

                ));
        assertTrue(result.getDevices()
                .stream()
                .anyMatch(deviceRateDto -> deviceRateDto.getRate().toString().equals("90.5")
                ));
    }

    @Test
    @Ignore
    public void findTopDeviceEachYearExceptionTest() {
        //예상예외
        exception.expect(DataNotFoundException.class);
        //실행
        deviceRateService.findTopDeviceEachYear();
    }

    @Test
    @Ignore
    public void findTopRateYearByDeviceIdTest() {
        //조건
        Long id = 1000000000L;
        //실행
        ResponseResult<DeviceRateDto> result = deviceRateService.findTopRateYearByDeviceId(id);
        //확인
        //조회된 데이터들이 조건과 일치하는지
        assertTrue(result.getResult().stream()
                    .allMatch(deviceRateDto -> deviceRateDto.getDeviceName().equals("스마트폰")));
        assertTrue(result.getResult().stream()
                .allMatch(deviceRateDto -> deviceRateDto.getYear().equals("2017")));
        assertTrue(result.getResult().stream()
                .allMatch(deviceRateDto -> deviceRateDto.getRate().toString().equals("90.6")));
    }
    @Test
    @Ignore
    public void findTopRateYearByDeviceIdExceptionTest() {
        //예상 예외
        exception.expect(DeviceNotFoundException.class);
        //조건 (없는 ID)
        Long id = 1000000010L;
        //실행
        ResponseResult<DeviceRateDto> result = deviceRateService.findTopRateYearByDeviceId(id);
    }

    @Test
    public void findTopRateDeviceByYearTest() {
        //조건
        String year= "2012";
        //실행
        ResponseResult<DeviceRateDto> result = deviceRateService.findTopRateDeviceByYear(year);
        //확인
        //조회된 데이터들이 조건과 일치하는지
        assertTrue(result.getResult().stream().allMatch(deviceRateDto -> deviceRateDto.getYear().equals(year)));
        assertTrue(result.getResult().stream().allMatch(deviceRateDto ->
                                                        deviceRateDto.getRate().toString().equals("93.9")));
    }

    @Test
    public void findTopRateDeviceByYearExceptionTest() {
        //예상 예외
        exception.expect(YearNotFoundException.class);
        //조건 (없는 년도)
        String year= "2010";
        //실행
        ResponseResult<DeviceRateDto> result = deviceRateService.findTopRateDeviceByYear(year);
    }

    @Test
    public void getPredictNextYearRateByDeviceIdTest() {
        //조건
        Long id = 1000000000L;

        //데이터 조회
        DeviceRateDto dto= deviceRateService.getPredictNextYearRateByDeviceId(id);

        //예측 데이터 확인
        assertTrue( dto.getDeviceName().equals("스마트폰"));
        assertTrue( dto.getYear().equals("2019"));
        assertTrue( dto.getRate().intValue() > 90 );
    }
}