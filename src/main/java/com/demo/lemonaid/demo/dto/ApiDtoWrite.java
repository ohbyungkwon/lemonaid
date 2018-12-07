package com.demo.lemonaid.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiDtoWrite {
    private int question_id;
    private int write_id;
    private String text;
}
