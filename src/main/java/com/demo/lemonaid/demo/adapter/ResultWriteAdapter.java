package com.demo.lemonaid.demo.adapter;

import lombok.Data;

@Data
public class ResultWriteAdapter {
    private int questionId;

    private int writeId;

    private String text;

    private String userId;
}