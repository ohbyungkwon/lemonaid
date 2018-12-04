package com.demo.lemonaid.demo.Adapter;

import lombok.Data;

import java.util.List;

@Data
public class ResultMultiAdapter {
    private int questionId;

    private int choiceMultiId;

    private List<String> choice;

    private String extraInfo;

    private String userId;
}

