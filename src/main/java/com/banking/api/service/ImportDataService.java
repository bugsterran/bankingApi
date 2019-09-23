package com.banking.api.service;

import com.banking.api.domain.Device;
import com.banking.api.domain.DeviceRate;
import com.banking.api.domain.YearRate;
import com.banking.api.module.CommonUtil;
import com.banking.api.repository.DeviceRateRepository;
import com.banking.api.repository.DeviceRepository;
import com.banking.api.repository.YearRateRepository;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Reader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ImportDataService {

    private final char SEPARATOR = ',';
    private final int YEAR_COL = 0;
    private final int RATE_COL = 1;
    private final int START_COL = 2;
    private final String FILE_PATH = "csv/data.csv";

    private DeviceRepository deviceRepository;
    private DeviceRateRepository deviceRateRepository;
    private YearRateRepository yearRateRepository;

    public ImportDataService(DeviceRepository deviceRepository,
                             DeviceRateRepository deviceRateRepository,
                             YearRateRepository yearRateRepository) {
        this.deviceRateRepository = deviceRateRepository;
        this.deviceRepository = deviceRepository;
        this.yearRateRepository = yearRateRepository;
    }

    /**
     * 초기 스프링 로딩 시 테이블에 데이터를 넣는다.
     * 로컬 설정 상 초기에 DB는 초기화 하도록 설정됨.
     */
    public void importData() throws Exception {

        List<String[]> rows = csvFileReadProcess(FILE_PATH);

        Map<String, Map<String, BigDecimal>> initData = this.convertCsvToMap(rows, yearRateRepository);

        initData.forEach((name, map) -> {
            Device device = new Device(name);
            deviceRepository.save(device); // 저장
            map.forEach((year, rate) -> {
                DeviceRate deviceRate = new DeviceRate();
                deviceRate.setDeviceId(device.getDeviceId());
                deviceRate.setYear(year);
                deviceRate.setRate(rate);
                deviceRateRepository.save(deviceRate); //저장
            });
        });
    }

    /**
     * csv파일 을 넘겨주면 List<String[]> 형태로 반환 해준다.
     *
     * @param filePath
     * @return
     * @throws Exception
     */
    public List<String[]> csvFileReadProcess(String filePath) throws Exception {

        Path path = Paths.get(ClassLoader.getSystemResource(filePath).toURI());
        CSVParser parser = new CSVParserBuilder().withSeparator(SEPARATOR).build();

        Reader br = Files.newBufferedReader(path, StandardCharsets.UTF_8);
        CSVReader reader = new CSVReaderBuilder(br).withCSVParser(parser).build();

        return reader.readAll();
    }

    /**
     * CSV 데이터를 MAP 으로 변환
     * @param rows CSV DATA
     * @return
     */
    public Map<String, Map<String, BigDecimal>> convertCsvToMap (
            List<String[]> rows
            , YearRateRepository yearRateRepository) {

        Map<String, Map<String, BigDecimal>> resultMap = new HashMap<>();

        for (int i = 0; i < rows.size(); i++) {
            String[] row = rows.get(i);
            String year = "";
            for (int j = 0; j < row.length; j++) {
                if (i == 0) {
                    if (j >= START_COL) {
                        Map<String, BigDecimal> map = new HashMap<>();
                        resultMap.put(row[j], map); // KEY = DEVICE_NAME
                    }
                } else {
                    if (j == YEAR_COL) {
                        year = row[j];
                    } else if (j == RATE_COL) {
                        YearRate yearRate = new YearRate();
                        yearRate.setYear(year);
                        yearRate.setRate(CommonUtil.stringToBigDecimal(row[j]));
                        yearRateRepository.save(yearRate);
                    } else if (j >= START_COL) {
                        Map<String, BigDecimal> map = resultMap.get(rows.get(0)[j]);
                        map.put(year, CommonUtil.stringToBigDecimal(row[j])); // KEY = YEAR
                    }
                }
            }
        }
        return resultMap;
    }
}
