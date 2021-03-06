package com.demo.lemonaid.demo.Domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name="disease_service")
@Data
public class DiseaseService {
    @Id
    @Column(name = "id", nullable = false)
    @Setter @Getter
    private int id;

    @Column(name = "disease_name", nullable=false)
    @Setter @Getter
    private String disease_name;

    @OneToMany(cascade= CascadeType.ALL, targetEntity = Question.class)
    @JoinColumn(name = "disease_service_id")
    private Collection<Question> question;
}
