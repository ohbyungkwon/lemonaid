package com.demo.lemonaid.demo.Service;

import com.demo.lemonaid.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SignInService {
    private UserRepository userRepository;

    @Autowired
    SignInService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public String findDuplicate(String userEmail){
        String reg =  "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern pattern = null;

        boolean isMatch = pattern.matches(reg, userEmail);

        if(userRepository.findUserByEmail(userEmail) == null && isMatch == true)
            return "사용 가능한 아이디입니다.";
        else return "사용 불가한 아이디입니다.";
    }


}
