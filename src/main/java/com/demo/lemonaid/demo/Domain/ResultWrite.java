package com.demo.lemonaid.demo.Domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "result_write")
@Data
public class ResultWrite {
    @Id
    @Column(name = "write_id")
    @Getter
    @Setter
    private int choice_multi_id;

    @Column(name = "text")
    @Getter
    @Setter
    private String text;

    @Column(name = "user_id")
    @Getter
    @Setter
    private String user_id;
}
