package com.demo.lemonaid.demo.Adapter;

import lombok.Data;

@Data
public class ResultSingleAdapter {
    private int questionId;

    private int choiceSingleId;

    private int choice;

    private String extraInfo;

    private String userId;
}
