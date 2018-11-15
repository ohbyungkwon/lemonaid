package com.demo.lemonaid.demo.Adapter;

import lombok.Data;

@Data
public class ResultWriteAdapter {
    private int question_id;

    private int write_id;

    private String text;

    private String user_id;
}
