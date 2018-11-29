package com.demo.lemonaid.demo.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiDtoSingle {
    private int question_id;
    private int choice_id;
    private int choices;
    private String extra_info;
}
