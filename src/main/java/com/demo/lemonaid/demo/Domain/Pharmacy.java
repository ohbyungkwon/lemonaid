package com.demo.lemonaid.demo.Domain;

import lombok.Data;

import javax.persistence.*;

@Table(name = "Pharmacy")
@Entity
@Data
public class Pharmacy {
    @Id
    @Column(nullable = false)
    @GeneratedValue
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
