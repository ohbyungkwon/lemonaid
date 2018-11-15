package com.demo.lemonaid.demo.Adapter;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
public class ResultMultiAdapter {
    private int question_id;

    private int choice_multi_id;

    private String []choice;

    private String extra_info;

    private String user_id;
}

