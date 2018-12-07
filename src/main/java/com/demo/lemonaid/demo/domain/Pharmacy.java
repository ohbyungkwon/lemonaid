package com.demo.lemonaid.demo.domain;

import lombok.Data;

import javax.persistence.*;

@Table(name = "pharmacy")
@Entity
@Data
public class Pharmacy {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private double lat;

    @Column
    private double lon;

    @Column(name = "device_id")
    private String deviceId;
}
