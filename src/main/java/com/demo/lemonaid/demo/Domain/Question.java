package com.demo.lemonaid.demo.Domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name="question")
@Data
public class Question {
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "disease_service_id", nullable = false)
    private int disease_service_id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "type")
    private String type;

    @Column(name = "priority", nullable = false)
    private int priority;

    @OneToMany(cascade= CascadeType.ALL, targetEntity = ChoiceSingle.class)
    @JoinColumn(name = "question_id")
    private Collection<ChoiceSingle> choiceSingle;

    @OneToMany(cascade= CascadeType.ALL, targetEntity = ChoiceMulti.class)
    @JoinColumn(name = "question_id")
    private Collection<ChoiceMulti> choiceMulti;

    @OneToMany(cascade= CascadeType.ALL, targetEntity = Write.class)
    @JoinColumn(name = "question_id")
    private Collection<Write> write;
}
