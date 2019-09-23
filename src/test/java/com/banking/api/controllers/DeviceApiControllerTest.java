package com.banking.api.controllers;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeviceApiControllerTest {

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
    public void findDevicesTest() throws Exception {
        //정상 케에스 200
        mockMvc.perform(get("/v1/banking/device/all"))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("findDevices"))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("devices[0].deviceId",is(1000000000)))
                .andDo(print());
    }

    @Test
    @Ignore
    public void findDevicesExceptionTest() throws Exception {
        //데이터가 존재하지 않을 경우 404
        mockMvc.perform(get("/v1/banking/device/all"))
                .andExpect(status().isNotFound())
                .andExpect(handler().methodName("findDevices"))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("devices[0].deviceId",is(1000000000)))
                .andDo(print());
    }

    @Test
    public void testFindDeviceTest() throws Exception {
        //정상 케이스 200
        mockMvc.perform(get("/v1/banking/device/1000000000"))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("findDevice"))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.deviceId",is(1000000000)))
                .andDo(print());
        //잘못된 형태 요청 (자릿수 부족) 400
        mockMvc.perform(get("/v1/banking/device/10000"))
                .andExpect(status().isBadRequest())
                .andExpect(handler().methodName("findDevice"))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andDo(print());
    }

    @Test
    public void testFindDeviceExceptionTest() throws Exception {
        //데이터가 존재하지 않을 경우(응답 코드 404)
        mockMvc.perform(get("/v1/banking/device/1000000012"))
                .andExpect(status().isNotFound())
                .andExpect(handler().methodName("findDevice"))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andDo(print());
    }


}