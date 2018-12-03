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

//    @PostMapping("/checkEmail")
//    @ResponseBody
//    public ResponseEntity<?> checkEmail(@RequestBody SiginInDto user){
//        Map<String,Object> map = new HashMap<>();
//        if(signInService.isDuplicate(user.getEmail()) != null){
//            return new ResponseEntity<>()
//        }
//        if(signInService.checkEmailReg(user.getEmail())){
//            return new ResponseEntity<String>("success", HttpStatus.OK);
//        }
//        return map;
//    }

    @PostMapping("/checkPwd")
    @ResponseBody
    public Map<String, Object> checkPwd(@RequestBody SiginInDto user){
        Map<String,Object> map = new HashMap<>();

        String comment = signInService.findPasswordReg(user.getPassword());
        map.put("comment",comment);

        return map;
    }

    @PostMapping("/checkDuplicate")
    @ResponseBody
    public Map<String, Object> checkDuplicate(@RequestBody SiginInDto user){
        Map<String,Object> map = new HashMap<>();
        String comment = signInService.isSamePassword(user);
        map.put("comment",comment);

        return map;
    }

    @PostMapping("/redirectNext")
    @ResponseBody
    public Map<String, Object> redirectNext(@RequestBody SiginInDto TempUser, HttpSession session){
        Map<String,Object> map = new HashMap<>();

        String comment = signInService.redirectNext(TempUser, session);

        map.put("comment",comment);
        return map;
    }

    @PostMapping("/done")
    @ResponseBody
    public Map<String, Object> done(@RequestBody SiginInDto TempUser, HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        String comment = signInService.doneAndValidate(TempUser,request);//validate

        map.put("comment", comment);
        return map;
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
