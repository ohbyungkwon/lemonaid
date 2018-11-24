package com.demo.lemonaid.demo.Adapter;

import lombok.Data;

@Data
public class ResultMultiAdapter {
    private int question_id;

    private int choice_multi_id;

    private String []choice;

    private String extra_info;

    private String user_id;
}

