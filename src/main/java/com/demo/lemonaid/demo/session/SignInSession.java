package com.demo.lemonaid.demo.session;

import com.demo.lemonaid.demo.Domain.User;
import com.demo.lemonaid.demo.Domain.passwordTemp;
import com.demo.lemonaid.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
@Scope(value ="session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SignInSession {
    private String userEmail;
    private String userPassword;

    private UserRepository userRepository;

    @Autowired
    SignInSession(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public String redirectNext(passwordTemp TempUser){
        if(TempUser.getPassword().indexOf(" ") != -1) {
            return "공백은 불가합니다.";
        }else if(TempUser.getPassword().indexOf(" ") == -1 && TempUser.getPassword().length() < 6){
            return "6자 이상 입력해주세요.";
        }else{
            if(TempUser.getCheckDuplicate().equals(TempUser.getPassword())){
                this.userEmail = TempUser.getEmail();
                this.userPassword = TempUser.getPassword();
                return "다음으로 이동합니다.";
            }
            else{
                return "비밀번호를 다시한번 확인해주세요.";
            }
        }
    }

    public String done(passwordTemp TempUser, HttpSession session){
        User user = new User();
        user.setId(session.getAttribute("DeviceId").toString());
        user.setEmail(userEmail);
        user.setPassword(new BCrypt().hashpw(userPassword,BCrypt.gensalt()));
        user.setName(TempUser.getName());
        user.setGender(TempUser.getGender());
        user.setPersonal_id(TempUser.getPersonalId());
        user.setTel(TempUser.getTel());
        user.setAddr(TempUser.getAddr());

        if(userRepository.save(user) != null){
            return "가입 완료";
        }else return "가입 실패";
    }
}
