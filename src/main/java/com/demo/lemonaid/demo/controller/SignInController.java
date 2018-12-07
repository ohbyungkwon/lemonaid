package com.demo.lemonaid.demo.controller;

import com.demo.lemonaid.demo.domain.enums.SignInBasicMessage;
import com.demo.lemonaid.demo.domain.enums.SignInSpecMessage;
import com.demo.lemonaid.demo.dto.SiginInDto;
import com.demo.lemonaid.demo.dto.SimpleDto;
import com.demo.lemonaid.demo.service.SignInService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller
public class SignInController {
    private SignInService signInService;

    SignInController(SignInService signInService){
        this.signInService = signInService;
    }

    @GetMapping("/SignInBasic")
    public String signin1(HttpServletResponse response){
        response.setHeader("Location", "signIn");
        Cookie cookie = new Cookie("state","signIn");
        response.addCookie(cookie);

        return "SignInBasic";
    }

    @GetMapping("/SignInSpec")
    public String signin2(){ return "SignInSpec"; }

    @PostMapping("/api/checkEmail")
    @ResponseBody
    public ResponseEntity<SimpleDto> checkEmail(@RequestBody SiginInDto user){
        if(signInService.isDuplicate(user.getEmail()) != null || !signInService.checkEmailReg(user.getEmail()))
            return ResponseEntity.badRequest().build();

        SimpleDto simpleDto = SimpleDto.builder()
                .status("success")
                .build();
        return new ResponseEntity<>(simpleDto, HttpStatus.OK);
    }

    @PostMapping("/api/checkPwd")
    @ResponseBody
    public ResponseEntity<SignInBasicMessage> checkPwd(@RequestBody SiginInDto user){
         SignInBasicMessage flag = signInService.checkPasswordReg(user.getPassword());
         if(flag != SignInBasicMessage.NEXT)
            return new ResponseEntity<>(flag, HttpStatus.BAD_REQUEST);

         return new ResponseEntity<>(flag, HttpStatus.OK);
    }

    @PostMapping("/api/checkDuplicate")
    @ResponseBody
    public ResponseEntity<SimpleDto> checkDuplicate(@RequestBody SiginInDto user){
        if(!signInService.isSamePassword(user))
           ResponseEntity.badRequest().build();

        SimpleDto simpleDto = SimpleDto.builder()
                .status("success")
                .build();
        return new ResponseEntity<>(simpleDto, HttpStatus.OK);
    }

    @PostMapping("/api/redirectNext")
    @ResponseBody
    public ResponseEntity<SignInBasicMessage> redirectNext(@RequestBody SiginInDto TempUser, HttpSession session){
        SignInBasicMessage flag = signInService.redirectNext(TempUser, session);

        if(flag != SignInBasicMessage.NEXT) return new ResponseEntity<>(flag, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(flag, HttpStatus.OK);
    }

    @PostMapping("/api/done")
    @ResponseBody
    public ResponseEntity<SignInSpecMessage> done(@RequestBody SiginInDto TempUser, HttpServletRequest request){
        SignInSpecMessage flag = signInService.doneAndValidate(TempUser, request);//validate

        if(flag != SignInSpecMessage.SUCCESS) return new ResponseEntity<>(flag, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(flag, HttpStatus.OK);
    }

    @GetMapping("/api/authRandom")
    @ResponseBody
    public Map<String, Object> random(){
        Map<String, Object> map = new HashMap<>();
        Random random = new Random();
        int num = random.nextInt(99999);

        map.put("num", num);

        return map;
    }
}
