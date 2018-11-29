package com.demo.lemonaid.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class OrderController {
    @GetMapping("/order")
    public String OrderView(HttpServletResponse response){
        response.setHeader("isLogin","onLogin");
        response.setHeader("Location", "order");
        Cookie cookie = new Cookie("state","order");
        cookie.setMaxAge(60*60*24);
        response.addCookie(cookie);
        return "Order";
    }

    @GetMapping("/cash")
    public String CashView(HttpServletResponse response){
        response.setHeader("Location", "cash");
        Cookie cookie = new Cookie("state","cash");
        cookie.setMaxAge(60*60*24);
        response.addCookie(cookie);
        return "Cash";
    }

    @GetMapping("/end")
    @ResponseBody
    public String EndView(HttpServletResponse response){
        response.setHeader("Location", "end");
        Cookie cookie = new Cookie("state","end");
        cookie.setMaxAge(60*60*24);
        response.addCookie(cookie);
        return "";
    }
}
