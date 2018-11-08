//package com.demo.lemonaid.demo.validate;
//
//import org.springframework.validation.Errors;
//import org.springframework.validation.Validator;
//
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class CheckEmailValidate implements Validator {
//    private Pattern pattern;
//    private Matcher matcher;
//
//    String reg =  "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
//
//    @Override
//    public boolean supports(Class<?> clazz) {
//        return false;
//    }
//
//    @Override
//    public void validate(Object target, Errors errors) {
//
//    }
//}
