package com.demo.lemonaid.demo.Adapter;

import lombok.Data;

import java.util.List;

@Data
public class ResultMultiAdapter {
    private int question_id;

    private int choice_multi_id;

    private List<String> choice;

    private String extra_info;

    private String user_id;
}

