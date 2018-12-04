package com.demo.lemonaid.demo.Domain;

import com.demo.lemonaid.demo.Domain.Embeded.ResultKeyMulti;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "result_multi")
@Data
public class ResultMulti {
    @EmbeddedId
    private ResultKeyMulti id;

    @Column(name = "choice_multi_id")
    private int choiceMultiId;

    @Column(name = "choice")
    private String choice;

    @Column(name = "extra_Info")
    private String extraInfo;
}
