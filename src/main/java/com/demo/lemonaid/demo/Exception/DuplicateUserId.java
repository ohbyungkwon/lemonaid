package com.demo.lemonaid.demo.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicateUserId extends RuntimeException{
    public DuplicateUserId(String message){
        super(message);
    }
}
