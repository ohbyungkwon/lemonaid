package com.demo.lemonaid.demo.Adapter;

import lombok.Data;

@Data
public class ResultSingleAdapter {
    private int question_id;

    private int choice_single_id;

    private int choice;

    private String extra_info;

    private String user_id;
}
