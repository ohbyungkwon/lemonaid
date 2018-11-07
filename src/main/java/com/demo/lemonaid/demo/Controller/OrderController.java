package com.demo.lemonaid.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class OrderController {
    @GetMapping("/order")
    public String OderView(){
        return "Order";
    }
}
