package com.demo.lemonaid.demo.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "intro")
@Entity
@Data
public class Intro {
    @Id
    @Column
    private int id;

    @Column
    private String disease;

    @Column
    private String medicineName;

    @Column
    private String intro;

    @Column
    private String guideExam;

    @Column
    private String guidePay;

    @Column
    private String guidePresc;

    @Column
    private String guidePick;

    @Column
    private String define;

    @Column
    private String treatAndRisk;

    @Column
    private String comment;

    @Column
    private String exceptService;

    @Column
    private String guideStart;
}
