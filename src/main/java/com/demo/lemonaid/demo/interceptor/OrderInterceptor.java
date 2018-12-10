package com.demo.lemonaid.demo.interceptor;

import com.demo.lemonaid.demo.domain.User;
import com.demo.lemonaid.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class OrderInterceptor implements HandlerInterceptor {
    private UserRepository userRepository;

    @Autowired
    public OrderInterceptor(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String deviceId = null;
        Cookie[] cookies = request.getCookies();
        for(int i = 0; i<cookies.length; i++){
            if(cookies[i].getName().equals("deviceId")){
                deviceId = cookies[i].getValue();
            }
        }
        User user = userRepository.findUserById(deviceId);


        response.setHeader("isLogin","onLogin");
        response.setHeader("Location", "order");
        Cookie cookie = new Cookie("state","order");
        response.addCookie(cookie);

        if(user.isNeedRefund()) {
            user.setNeedRefund(false);
            userRepository.save(user);

            response.setContentType("text/html; charset=UTF-8");

            PrintWriter out = response.getWriter();
            out.println("<script>alert('처방 도중 앱이 예기치 못하게 종료되어 자동 환불처리되었습니다.')</script>");
            out.flush();
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
