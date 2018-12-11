package com.demo.lemonaid.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CantFindUserException extends RuntimeException{
    public CantFindUserException(String message) {
        super(message);
    }
}
