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
    @Getter @Setter
    private int id;

    @Column(name = "disease_service_id", nullable = false)
    @Getter @Setter
    private int disease_service_id;

    @Column(name = "content", nullable = false)
    @Getter @Setter
    private String content;

    @Column(name = "type")
    @Getter @Setter
    private String type;

    @Column(name = "priority", nullable = false)
    @Getter @Setter
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
