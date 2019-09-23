package com.banking.api.service;

import com.banking.api.domain.YearRate;
import com.banking.api.repository.YearRateRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ImportDataServiceTest {

    @Autowired
    ImportDataService importDataService;
    @Autowired
    YearRateRepository yearRateRepository;
    public void importDataTest() {

    }

    @Test
    @Ignore
    public void csvFileReadProcessTest() throws Exception {
        //테스트 csv 파일 변환
        List<String[]> list = importDataService.csvFileReadProcess("csv/test.csv");

        //정상정으로 값이 들어 갔는지 체크
        assertTrue(!list.isEmpty());
        //중복으로 생성 여부 체크
        assertTrue(list.stream().distinct().count() == list.size());
    }

    @Test
    public void convertCsvToMapTest() throws Exception {
        //테스트 csv 파일 변환
        List<String[]> list = importDataService.csvFileReadProcess("csv/test.csv");

        Map<String, Map<String, BigDecimal>> initData =  importDataService.convertCsvToMap(list, yearRateRepository);

        assertFalse(initData.isEmpty());
        assertTrue(initData.keySet().contains("스마트폰"));

        List<YearRate> years= yearRateRepository.findAll();

        assertTrue(years.stream().anyMatch(yearRate -> yearRate.getYear().equals("2018")));
        assertTrue(years.stream().anyMatch(yearRate -> yearRate.getRate().toString().equals("53.4")));

    }

}