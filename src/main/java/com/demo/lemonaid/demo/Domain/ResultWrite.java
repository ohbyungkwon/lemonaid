package com.demo.lemonaid.demo.Domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "result_write")
@Data
public class ResultWrite {
    @Id
    @Column(name = "write_question_id")
    private int question_id;

    @Column(name = "write_id")
    private int write_id;

    @Column(name = "text")
    private String text;

    @Column(name = "user_id")
    private String user_id;
}
