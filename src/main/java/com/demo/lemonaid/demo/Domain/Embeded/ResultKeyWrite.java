package com.demo.lemonaid.demo.Domain.Embeded;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class ResultKeyWrite implements Serializable {
    @Column(name = "write_question_id")
    private int question_id;

    @Column(name = "user_id")
    private String user_id;
}

