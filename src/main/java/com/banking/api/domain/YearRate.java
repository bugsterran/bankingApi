package com.banking.api.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@Data
@Entity
@Table(name="YEAR_RATE_TB")
public class YearRate {
    @Id
    @Column(name="year", length = 4)
    private String year;
    @Digits(integer = 5, fraction = 1)
    private BigDecimal rate;
}
