package com.demo.lemonaid.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
public class OrderController {
    @GetMapping("/order")
    public String OrderView(HttpServletResponse response){
        response.setHeader("isLogin","onLogin");
        response.setHeader("Location", "order");
        return "Order";
    }

    @GetMapping("/cash")
    public String CashView(HttpServletResponse response){
        response.setHeader("Location", "cash");
        return "Cash";
    }

    @GetMapping("/end")
    @ResponseBody
    public String EndView(HttpServletResponse response){
        response.setHeader("Location", "end");
        return "<h2>End Page</h2>";
    }

}
