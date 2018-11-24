package com.demo.lemonaid.demo.Domain;

import com.demo.lemonaid.demo.Domain.Embeded.ResultKeyWrite;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "result_write")
@Data
public class ResultWrite {
    @EmbeddedId
    private ResultKeyWrite id;

    @Column(name = "write_id")
    private int write_id;

    @Column(name = "text")
    private String text;
}
