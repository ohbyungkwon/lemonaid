package com.demo.lemonaid.demo.Controller;

import com.demo.lemonaid.demo.Dto.SiginInDto;
import com.demo.lemonaid.demo.Service.SignInService;
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
    public String signin1(HttpServletResponse response, HttpServletRequest request){
        response.setHeader("Location", "signIn");
        Cookie cookie = new Cookie("state","signIn");
        response.addCookie(cookie);

        return "SignInBasic";
    }

    @GetMapping("/SignInSpec")
    public String signin2(){ return "SignInSpec"; }

    @PostMapping("/api/checkEmail")
    @ResponseBody
    public ResponseEntity<?> checkEmail(@RequestBody SiginInDto user){
        if(signInService.isDuplicate(user.getEmail()) != null || !signInService.checkEmailReg(user.getEmail())){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/checkPwd")
    @ResponseBody
    public ResponseEntity<?> checkPwd(@RequestBody SiginInDto user){
        if(!signInService.checkPasswordReg(user.getPassword()))
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/checkDuplicate")
    @ResponseBody
    public ResponseEntity<?> checkDuplicate(@RequestBody SiginInDto user){
        if(!signInService.isSamePassword(user))
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/redirectNext")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> redirectNext(@RequestBody SiginInDto TempUser, HttpSession session){
        int flag = signInService.redirectNext(TempUser, session);
        Map<String, Object> map = new HashMap<>();
        map.put("flag", flag);

        if(flag != 0) return new ResponseEntity<Map<String,Object>>(map, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
    }

    @PostMapping("/done")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> done(@RequestBody SiginInDto TempUser, HttpServletRequest request){
        int flag = signInService.doneAndValidate(TempUser,request);//validate
        Map<String, Object> map = new HashMap<>();
        map.put("flag", flag);

        if(flag != 0) return new ResponseEntity<Map<String,Object>>(map, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
    }

    @GetMapping("/authRandom")
    @ResponseBody
    public Map<String, Object> random(){
        Map<String, Object> map = new HashMap<>();
        Random random = new Random();
        int num = random.nextInt(99999);

        map.put("num", num);

        return map;
    }
}
