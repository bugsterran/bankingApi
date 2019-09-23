package com.banking.api.repository;

import com.banking.api.domain.YearRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YearRateRepository extends JpaRepository<YearRate, String> {
}
