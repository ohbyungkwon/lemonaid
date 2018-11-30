package com.demo.lemonaid.demo.Domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "review")
@Data
public class Review {
    @Id
    @Column
    private int id;

    @Column
    private String comment;

    @Column(name = "reg_time")
    private String regTime;
}
