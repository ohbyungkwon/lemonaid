package com.demo.lemonaid.demo.Controller;

import com.demo.lemonaid.demo.Domain.User;
import com.demo.lemonaid.demo.Domain.passwordTemp;
import com.demo.lemonaid.demo.Repository.UserRepository;
import com.demo.lemonaid.demo.Service.SignInService;
import com.demo.lemonaid.demo.Service.UserService;
import com.demo.lemonaid.demo.session.firstVisit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller
public class UserController {
    private SignInService signInService;

    UserController(SignInService signInService){
        this.signInService = signInService;
    }

    @GetMapping("/login")
    public String login(){
        return "Login";
    }//access to survey

    @GetMapping("/firstLogin")
    public String firstLogin(HttpServletRequest request){
        String ref = request.getHeader("Referer");
        request.getSession().setAttribute("prev",ref);
        System.out.println(ref);

        return "Login";
    }//access to main

    @GetMapping("/SignInBasic")
    public String signin1(){
        return "SignInBasic";
    }

    @GetMapping("/SignInSpec")
    public String signin2(){
        return "SignInSpec";
    }

    @PostMapping("/checkEmail")
    @ResponseBody
    public Map<String, Object> checkEmail(@RequestBody User user){
        Map<String,Object> map = new HashMap<>();
        boolean isExist = signInService.findDuplicate(user.getEmail());
        map.put("isExist",isExist);
        return map;
    }

    @PostMapping("/checkPwd")
    @ResponseBody
    public Map<String, Object> checkPwd(@RequestBody User user){
        Map<String,Object> map = new HashMap<>();
        System.out.println(user.getPassword());
        return map;
    }

    @PostMapping("/checkDuplicate")
    @ResponseBody
    public Map<String, Object> checkDuplicate(@RequestBody passwordTemp param){
        Map<String,Object> map = new HashMap<>();
        System.out.println(param.getCheckDuplicate());
        return map;
    }
}
