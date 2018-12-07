package com.demo.lemonaid.demo.domain;

import com.demo.lemonaid.demo.domain.embeded.ResultKeySingle;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "result_single")
@Data
public class ResultSingle {
    @EmbeddedId
    private ResultKeySingle id;

    @Column(name = "choice_single_id")
    private int choiceSingleId;

    @Column(name = "choice")
    private int choice;

    @Column(name = "extra_info")
    private String extraInfo;
}
