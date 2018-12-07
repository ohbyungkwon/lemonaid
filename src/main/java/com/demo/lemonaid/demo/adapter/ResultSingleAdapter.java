package com.demo.lemonaid.demo.adapter;

import lombok.Data;

@Data
public class ResultSingleAdapter {
    private int questionId;

    private int choiceSingleId;

    private int choice;

    private String extraInfo;

    private String userId;
}
