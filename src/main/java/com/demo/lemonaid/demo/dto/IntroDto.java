package com.demo.lemonaid.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IntroDto {
    private String name;

    private String medicineName;

    private String intro;

    private String guideExam;

    private String guidePay;

    private String guidePresc;

    private String guidePick;

    private String define;

    private String treatAndRisk;

    private String comment;

    private String []exceptService;

    private String guideStart;
}
