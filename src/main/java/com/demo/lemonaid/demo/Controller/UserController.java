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

    @PostMapping("/firstVisit")
    @ResponseBody
    public Map<String, Object> UserId(){
        Map<String, Object> map = new HashMap<>();

        String Id = firstVisit.createId();

        return map;
    }

    @PostMapping("/createToken")
    @ResponseBody
    public Map<String, Object> Token(){
        try {
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .claim("email","obk")
                    .claim("pwd","1234").build();
            Payload payload = new Payload(claimsSet.toJSONObject());
            JWEHeader header = new JWEHeader(JWEAlgorithm.DIR, EncryptionMethod.A128CBC_HS256);

            String secret = "841D8A6C80CBA4FCAD32D5367C18C53B";
            byte[] secretKey = secret.getBytes();
            DirectEncrypter encrypter = new DirectEncrypter(secretKey);

            JWEObject jweObject = new JWEObject(header, payload);
            jweObject.encrypt(encrypter);
            String token = jweObject.serialize();
            System.out.println(token);
        }catch (Exception e){ e.printStackTrace(); }

        Map<String, Object> map = new HashMap<>();

        return map;
    }

    @GetMapping("/login")
    public String login(){
        return "Login";
    }
}
