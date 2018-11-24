package com.demo.lemonaid.demo.Domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "choice_multi")
@Data
public class ChoiceMulti {
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "question_id", nullable = false)
    private int question_id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "extra_info", nullable = false)
    private String extra_info;

    @Column(name = "is_need_extra", nullable = false)
    private boolean is_need_extra;

    @Column(name = "priority", nullable = false)
    private int priority;
}
