package com.demo.lemonaid.demo.controller;

import com.demo.lemonaid.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class OrderController {
    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping("/order")
    public String OrderView(HttpServletResponse response){
        return "Order";
    }

    @GetMapping("/cash")
    public String CashView(HttpServletResponse response){
        response.setHeader("Location", "cash");
        Cookie cookie = new Cookie("state","cash");
        response.addCookie(cookie);
        return "Cash";
    }

    @GetMapping("/end")
    @ResponseBody
    public String EndView(HttpServletResponse response){
        response.setHeader("Location", "end");
        Cookie cookie = new Cookie("state","end");
        response.addCookie(cookie);

        orderService.saveRefundUser(true);

        return "";
    }
}
