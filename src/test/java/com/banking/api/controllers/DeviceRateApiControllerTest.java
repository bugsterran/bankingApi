package com.banking.api.controllers;

import com.banking.api.exception.DataNotFoundException;
import com.banking.api.exception.DeviceNotFoundException;
import com.banking.api.exception.InvalidValueException;
import com.banking.api.exception.YearNotFoundException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DeviceRateApiControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Rule
    public ExpectedException exception = ExpectedException.none();
    @Before
    public void setUp() throws Exception{
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @Ignore
    public void findTopDeviceEachYearTest() throws Exception {
        //정상 케이스 200
        mockMvc.perform(get("/v1/banking/top/device/eachyear"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(handler().methodName("findTopDeviceEachYear"))
                .andDo(print());
    }

    @Test
    @Ignore
    public void findTopDeviceEachYearExceptionTest() throws Exception {
        //데이터가 없을 경우 404
        mockMvc.perform(get("/v1/banking/top/device/eachyear"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(handler().methodName("findTopDeviceEachYear"))
                .andDo(print());
    }

    @Test
    @Ignore
    public void findTopRateDeviceByYearTest() throws Exception {
        //정상 케이스 200
        mockMvc.perform(get("/v1/banking/2018/top/rate/device"))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("findTopRateDeviceByYear"))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("result[0].year",is("2018")))
                .andDo(print());

        //잘못된 형태 요청 (숫자 외 + 4 이상) 400
        mockMvc.perform(get("/v1/banking/3r32443/top/rate/device"))
                .andExpect(status().isBadRequest())
                .andExpect(handler().methodName("findTopRateDeviceByYear"))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andDo(print());

    }

    @Test
    //@Ignore
    public void findTopRateDeviceByYearExceptionTest() throws Exception {
        //데이터가 없을 경우 404
        mockMvc.perform(get("/v1/banking/2010/top/rate/device"))
                .andExpect(status().isNotFound())
                .andExpect(handler().methodName("findTopRateDeviceByYear"))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andDo(print());
    }

    @Test
    @Ignore
    public void findTopRateYearByDeviceIdTest() throws Exception {
        //정상 케이스 200
        mockMvc.perform(get("/v1/banking/1000000000/top/rate/year"))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("findTopRateYearByDeviceId"))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("result[0].deviceId",is(1000000000)))
                .andDo(print());

        //잘못된 형태 요청 (자릿수 부족) 400
        mockMvc.perform(get("/v1/banking/441234/top/rate/year"))
                .andExpect(status().isBadRequest())
                .andExpect(handler().methodName("findTopRateYearByDeviceId"))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andDo(print());

    }
    @Test
    //@Ignore
    public void findTopRateYearByDeviceIdExceptionTest() throws Exception {
        //데이터가 없을 경우 404
        mockMvc.perform(get("/v1/banking/1000000010/top/rate/year"))
                .andExpect(status().isNotFound())
                .andExpect(handler().methodName("findTopRateYearByDeviceId"))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andDo(print());

    }
}