package com.demo.lemonaid.demo.Error;

import com.demo.lemonaid.demo.Domain.Embeded.ResultKeySingle;
import lombok.Data;
import org.springframework.http.HttpStatus;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;

@Data
public class ApiDtoSingle {
    private int question_id;
    private int choice_id;
    private int choices;
    private String extra_info;

    public ApiDtoSingle(){}

    public ApiDtoSingle(int question_id, int choice_id, int choices, String extra_info){
        this.choice_id=choice_id;
        this.choices=choices;
        this.question_id = question_id;
        this.extra_info = extra_info;
    }

}
