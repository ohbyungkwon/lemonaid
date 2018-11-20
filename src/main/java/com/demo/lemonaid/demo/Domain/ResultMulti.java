package com.demo.lemonaid.demo.Domain;

import com.demo.lemonaid.demo.Domain.Embeded.ResultKeyMulti;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "result_multi")
@Data
public class ResultMulti {
    @EmbeddedId
    private ResultKeyMulti id;

    @Column(name = "choice_multi_id")
    private int choice_multi_id;

    @Column(name = "choice")
    private String choice;

    @Column(name = "extra_info")
    private String extra_info;
}
