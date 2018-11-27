package com.demo.lemonaid.demo.Domain;

import com.sun.javafx.beans.IDProperty;
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

    @Column
    private String reg_time;
}
