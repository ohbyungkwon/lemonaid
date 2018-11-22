package com.demo.lemonaid.demo.Controller;

import com.demo.lemonaid.demo.Domain.passwordTemp;
import com.demo.lemonaid.demo.Repository.UserRepository;
import com.demo.lemonaid.demo.Service.SignInService;
import com.demo.lemonaid.demo.Service.UserService;
import com.demo.lemonaid.demo.session.SignInSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller
public class SignInController {
    private SignInService signInService;
    private SignInSession signInSession;

    SignInController(SignInService signInService,
                     SignInSession signInSession){
        this.signInService = signInService;
        this.signInSession = signInSession;
    }

    @GetMapping("/SignInBasic")
    public String signin1(HttpServletResponse response){
        response.setHeader("Location", "signIn");
        return "SignInBasic";
    }

    @GetMapping("/SignInSpec")
    public String signin2(){ return "SignInSpec"; }

    @PostMapping("/checkEmail")
    @ResponseBody
    public Map<String, Object> checkEmail(@RequestBody passwordTemp user){
        Map<String,Object> map = new HashMap<>();
        String comment = signInService.findDuplicate(user.getEmail());
        map.put("comment",comment);
        return map;
    }

    @PostMapping("/checkPwd")
    @ResponseBody
    public Map<String, Object> checkPwd(@RequestBody passwordTemp user){
        Map<String,Object> map = new HashMap<>();

        String comment = signInService.findPasswordReg(user.getPassword());
        map.put("comment",comment);

        return map;
    }

    @PostMapping("/checkDuplicate")
    @ResponseBody
    public Map<String, Object> checkDuplicate(@RequestBody passwordTemp user){
        Map<String,Object> map = new HashMap<>();
        String comment = signInService.isSamePassword(user);
        map.put("comment",comment);

        return map;
    }

    @PostMapping("/redirectNext")
    @ResponseBody
    public Map<String, Object> redirectNext(@RequestBody passwordTemp TempUser){
        Map<String,Object> map = new HashMap<>();
        String comment = signInSession.redirectNext(TempUser);
        map.put("comment",comment);

        return map;
    }

    @PostMapping("/done")
    @ResponseBody
    public Map<String, Object> done(@RequestBody passwordTemp TempUser, HttpSession session){
        Map<String, Object> map = new HashMap<>();
        String comment = signInService.done(TempUser);//validate

        if(comment.equals("가입 완료"))
            comment = signInSession.done(TempUser, session);//save

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
