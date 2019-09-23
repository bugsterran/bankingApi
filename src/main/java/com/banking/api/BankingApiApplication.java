package com.banking.api;

import com.banking.api.service.ImportDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootApplication
public class BankingApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankingApiApplication.class, args);
    }

    @Bean
    @Transactional
    CommandLineRunner DataInitialize(ImportDataService importDataService) {
        return args -> {
            importDataService.importData();
        };

    }

}
