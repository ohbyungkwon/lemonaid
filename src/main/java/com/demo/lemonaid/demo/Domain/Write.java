package com.demo.lemonaid.demo.Domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "write")
@Data
public class Write {
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "question_id", nullable = false)
    private int questionId;

    @Column(name = "conetent", nullable = false)
    private int content;

    @Column(name = "text", nullable = false)
    private int text;
}
