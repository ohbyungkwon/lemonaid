package com.demo.lemonaid.demo.Controller;

import com.demo.lemonaid.demo.Domain.User;
import com.demo.lemonaid.demo.Domain.passwordTemp;
import com.demo.lemonaid.demo.Repository.UserRepository;
import com.demo.lemonaid.demo.Service.SignInService;
import com.demo.lemonaid.demo.Service.UserService;
import com.demo.lemonaid.demo.session.SignInSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller
public class UserController {
    private SignInService signInService;
    private SignInSession signInSession;
    private UserService userService;
    private UserRepository userRepository;

    UserController(SignInService signInService, SignInSession signInSession, UserService userService, UserRepository userRepository){
        this.signInService = signInService;
        this.signInSession = signInSession;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/receiveId")
    @ResponseBody
    public Map<String, Object> saveId(@RequestBody String id){
        Map<String, Object> map = new HashMap<>();

        String idTemp = userService.JsonParseUserId(id);

        User user = new User();
        user.setId(idTemp);
        user.setPersonal_id("0");
        user.setGender("-1");

        if(userRepository.save(user) != null){
            map.put("state","success");
            map.put("TempUserId",idTemp);
        }else{ map.put("state", "fail"); }

        return map;
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request){
        String ref = "http://localhost:8080/order";
        request.getSession().setAttribute("prev",ref);
        System.out.println(ref);
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
    public Map<String, Object> done(@RequestBody passwordTemp TempUser){
        Map<String, Object> map = new HashMap<>();
        String comment = signInService.done(TempUser);//validate

        if(comment.equals("가입 완료"))
            comment = signInSession.done(TempUser);//save

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
