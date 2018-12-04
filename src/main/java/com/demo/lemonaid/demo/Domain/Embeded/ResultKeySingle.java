package com.demo.lemonaid.demo.Domain.Embeded;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class ResultKeySingle implements Serializable {
    @Column(name = "choice_single_question_id")
    private int questionId;

    @Column(name = "user_id")
    private String userId;
}
