package com.demo.lemonaid.demo.Controller;

import com.demo.lemonaid.demo.Domain.User;
import com.demo.lemonaid.demo.Repository.UserRepository;
import com.demo.lemonaid.demo.Service.UserService;
import com.demo.lemonaid.demo.session.firstVisit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller
public class UserController {
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
