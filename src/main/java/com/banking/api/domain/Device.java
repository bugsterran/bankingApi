package com.banking.api.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name="DEVICE_TB")
@NoArgsConstructor
@SequenceGenerator(name="SEQ_ID", sequenceName="SEQ_ID", initialValue = 1000000000, allocationSize = 1)
public class Device {

    @Id
    @Column(name = "DEVICE_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_ID")
    private Long deviceId;

    @Column(name = "DEVICE_NAME")
    private String deviceName;

    public Device(String deviceName){ this.deviceName = deviceName; }

}
