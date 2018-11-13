package com.demo.lemonaid.demo.Domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "result_single")
@Data
public class ResultSingle {
    @Id
    @Column(name = "choice_single_question_id")
    private int question_id;

    @Column(name = "choice_single_id")
    private int choice_single_id;

    @Column(name = "choice")
    private int choice;

    @Column(name = "extra_info")
    private String extra_info;

    @Column(name = "user_id")
    private String user_id;
}
