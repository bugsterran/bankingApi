package com.banking.api.service;

import com.banking.api.domain.Device;
import com.banking.api.domain.DeviceRate;
import com.banking.api.domain.response.ResponseDevices;
import com.banking.api.exception.DataNotFoundException;
import com.banking.api.exception.DeviceNotFoundException;
import com.fasterxml.jackson.core.PrettyPrinter;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeviceServiceTest {

    @Autowired
    private DeviceService deviceService;

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
    public void findDevicesTest() {
        //실행
        ResponseDevices<Device> result =  deviceService.findDevices();
        //확인
        assertTrue(!result.getDevices().isEmpty());

        //중복 조회 체크
        assertTrue(result.getDevices().stream().distinct().count() != result.getDevices().size());
        //데이터 정상적으로 조회 확인
        assertTrue(result.getDevices()
                .stream()
                .anyMatch(device -> device.getDeviceId().equals(1000000000)));
    }

    /**
     *  데이터가 존재하지 않은 경우 Exception 발생
     *  TEST 시 초기 데이터 세팅하는 부분 주석 또는 제거 필요
     */
    @Test
    @Ignore
    public void findDevicesExceptionTest(){
        //예상 예외
        exception.expect(DataNotFoundException.class);
        //실행
        deviceService.findDevices();
    }

    @Test
    @Ignore
    public void findDeviceTest() {
        //조건
        Long id = 1000000000L;
        //실행
        Device result = deviceService.findDevice(id);
        //확인
        assertNotNull(result);
        assertEquals(id,result.getDeviceId());
        assertEquals("스마트폰",result.getDeviceName());
    }

    @Test
    public void findDeviceExceptionTest() {
        //예상 예외
        exception.expect(DeviceNotFoundException.class);
        //실행
        Long id = 1000000010L;
        Device result = deviceService.findDevice(id);
    }

}