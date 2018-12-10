package com.demo.lemonaid.demo.service;

import com.demo.lemonaid.demo.domain.User;
import com.demo.lemonaid.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service
public class OrderService {
    private UserRepository userRepository;
    private HttpServletRequest request;

    @Autowired
    public OrderService(UserRepository userRepository, HttpServletRequest request){
        this.userRepository = userRepository;
        this.request = request;
    }

    public void saveRefundUser(boolean is_user_cashed){
        String deviceId = null;
        Cookie[] cookie = request.getCookies();
        for(int i = 0; i<cookie.length; i++){
            if(cookie[i].getName().equals("deviceId")){
                deviceId = cookie[i].getValue();
            }
        }
        User user = userRepository.findUserById(deviceId);
        user.setNeedRefund(is_user_cashed);
        userRepository.save(user);
    }
}
