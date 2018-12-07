package com.demo.lemonaid.demo.domain;

import com.demo.lemonaid.demo.domain.embeded.ResultKeyWrite;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "result_write")
@Data
public class ResultWrite {
    @EmbeddedId
    private ResultKeyWrite id;

    @Column(name = "write_id")
    private int writeId;

    @Column(name = "text")
    private String text;
}
