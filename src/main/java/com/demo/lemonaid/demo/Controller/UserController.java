package com.demo.lemonaid.demo.Controller;

import com.demo.lemonaid.demo.Domain.User;
import com.demo.lemonaid.demo.Repository.UserRepository;
import com.demo.lemonaid.demo.Service.SignInService;
import com.demo.lemonaid.demo.Service.UserService;
import com.demo.lemonaid.demo.session.SignInSession;
import com.demo.lemonaid.demo.session.UserIdSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {
    @Autowired private UserRepository userRepository;
    @Autowired private UserService userService;
    @Autowired private UserIdSession userIdSession;

    @Autowired private HttpServletRequest request;

    @PostMapping("/receiveId")
    @ResponseBody
    public Map<String, Object> saveId(@RequestBody String id){
        Map<String, Object> map = new HashMap<>();

        String ParsedID = userService.JsonParseUserId(id);

        User user = new User();
        user.setId(ParsedID);
        user.setPersonal_id("0");
        user.setGender("-1");

        if(userRepository.save(user) != null){
            map.put("state","success");
            map.put("TempUserId",ParsedID);
        }else{ map.put("state", "fail"); }

        return map;
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
}
