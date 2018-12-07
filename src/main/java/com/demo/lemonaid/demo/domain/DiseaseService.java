package com.demo.lemonaid.demo.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="disease_service")
@Data
public class DiseaseService {
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "disease_name", nullable=false)
    private String diseaseName;

    @Column(name = "max_age", nullable=false)
    private int maxAge;

    @Column(name = "min_age", nullable=false)
    private int minAge;

    @Column(name = "except_gender", nullable=false)
    private String exceptGender;
}
