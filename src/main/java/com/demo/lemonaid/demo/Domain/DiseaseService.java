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
    private String disease_name;

//    @OneToMany(cascade= CascadeType.ALL, targetEntity = Question.class)
//    @JoinColumn(name = "disease_service_id", referencedColumnName = "id")
//    private Collection<Question> question;
}
