package com.demo.lemonaid.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiDtoMulti {
    private int question_id;
    private int choice_id;
    private String choices;
    private String extra_info;
}
