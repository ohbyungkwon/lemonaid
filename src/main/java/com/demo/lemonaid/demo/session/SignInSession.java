package com.demo.lemonaid.demo.session;

import com.demo.lemonaid.demo.Domain.User;
import com.demo.lemonaid.demo.Domain.PasswordTemp;
import com.demo.lemonaid.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Component
@Scope(value ="session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SignInSession {
    private String userEmail;
    private String userPassword;

    private UserRepository userRepository;
    private SignInSession signInSession;

    @Autowired
    SignInSession(UserRepository userRepository,
                  SignInSession signInSession){
        this.userRepository = userRepository;
        this.signInSession = signInSession;
    }

    public String redirectNext(PasswordTemp TempUser){
        if(TempUser.getPassword().indexOf(" ") != -1) {
            return "공백은 불가합니다.";
        }else if(TempUser.getPassword().indexOf(" ") == -1 && TempUser.getPassword().length() < 6){
            return "6자 이상 입력해주세요.";
        }else if(!TempUser.isEmailCheck()){
            return "이메일 중복 확인해주세요.";
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

    public String done(PasswordTemp TempUser, HttpServletRequest request){
        User user = new User();

        Cookie []cookies = request.getCookies();
        String deviceId = null;
        for(int i = 0; i< cookies.length; i++){
            if(cookies[i].getName().equals("DeviceId"))
                deviceId = cookies[i].getValue();
        }

        user.setId(deviceId);
        user.setEmail(userEmail);
        user.setPassword(new BCrypt().hashpw(userPassword,BCrypt.gensalt()));
        user.setName(TempUser.getName());
        user.setGender(TempUser.getGender());
        user.setPersonal_id(TempUser.getPersonalId());
        user.setTel(TempUser.getTel());
        user.setAddr(TempUser.getAddr());
        user.setUserType("1");

        if(userRepository.save(user) != null){
            return "가입 완료";
        }else return "가입 실패";
    }
}
