package com.demo.lemonaid.demo.Domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "Pharmacy")
@Entity
@Data
public class Pharmacy {
    @Id
    @Column(nullable = false)
    private String id;

    @Column
    private String name;

    @Column
    private String lat;

    @Column
    private String lon;

    @Column
    private String email;
}
