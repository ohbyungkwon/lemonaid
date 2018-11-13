package com.demo.lemonaid.demo.Adapter;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "result_multi")
@Data
public class ResultMultiAdapter {
    @Id
    @Column(name = "choice_multi_question_id")
    private int question_id;

    @Column(name = "choice_multi_id")
    private int choice_multi_id;

    @Column(name = "choice")
    private String []choice;

    @Column(name = "extra_info")
    private String extra_info;

    @Column(name = "user_id")
    private String user_id;
}

