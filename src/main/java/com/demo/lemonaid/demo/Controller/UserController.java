package com.demo.lemonaid.demo.Controller;

import com.demo.lemonaid.demo.Domain.User;
import com.demo.lemonaid.demo.Repository.UserRepository;
import com.demo.lemonaid.demo.Service.UserService;
import com.demo.lemonaid.demo.session.firstVisit;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    firstVisit firstVisit;

    @RequestMapping("/hello")
    public String hello() {
        return "Hello auth";
    }

    @RequestMapping("/api/hello")
    public String hello2() {
        return "Hello api auth";
    }

    @GetMapping("/login")
    public String login(){
        return "Login";
    }
}
