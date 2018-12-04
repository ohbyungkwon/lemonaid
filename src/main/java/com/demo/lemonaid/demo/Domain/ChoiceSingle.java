package com.demo.lemonaid.demo.Domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "choice_single")
@Data
public class ChoiceSingle {
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "question_id", nullable = false)
    private int questionId;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "extra_Info", nullable = false)
    private String extraInfo;

    @Column(name = "is_need_extra", nullable = false)
    private boolean isNeedExtra;

    @Column(name = "priority", nullable = false)
    private int priority;
}
