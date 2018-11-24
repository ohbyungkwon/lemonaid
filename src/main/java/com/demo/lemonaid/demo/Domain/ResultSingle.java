package com.demo.lemonaid.demo.Domain;

import com.demo.lemonaid.demo.Domain.Embeded.ResultKeySingle;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "result_single")
@Data
public class ResultSingle {
    @EmbeddedId
    private ResultKeySingle id;

    @Column(name = "choice_single_id")
    private int choice_single_id;

    @Column(name = "choice")
    private int choice;

    @Column(name = "extra_info")
    private String extra_info;
}
