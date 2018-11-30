package com.demo.lemonaid.demo.Domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

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

//    @OneToMany(cascade= CascadeType.ALL, targetEntity = Question.class)
//    @JoinColumn(name = "disease_service_id", referencedColumnName = "id")
//    private Collection<Question> question;
}
