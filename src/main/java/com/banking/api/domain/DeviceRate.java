package com.banking.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@Entity
@Data
@Table(name="DEVICE_RATE_TB")
@SequenceGenerator(name="SEQ_NO", sequenceName="SEQ_NO", initialValue = 1, allocationSize = 1)
public class DeviceRate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_NO")
    private Long seqNo;

    @Column(name = "DEVICE_ID")
    private Long deviceId;

    @Column(name="year", length = 4)
    private String year;

    @Digits(integer = 5, fraction = 1)
    private BigDecimal rate;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "DEVICE_ID", updatable = false, insertable = false)
    private Device device;

}
