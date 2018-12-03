package com.demo.lemonaid.demo.Service;

import com.demo.lemonaid.demo.Domain.User;
import com.demo.lemonaid.demo.Dto.SiginInDto;
import com.demo.lemonaid.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional(readOnly = true)
public class SignInService {
    private UserRepository userRepository;
    private HttpSession session;

    @Autowired
    SignInService(UserRepository userRepository,
                  HttpSession session) {
        this.userRepository = userRepository;
        this.session = session;
    }

    public Boolean checkEmailReg(String userEmail) {
        String reg =  "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";

        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(userEmail);

       return m.matches();
    }

    public User isDuplicate(String userEmail){
        return userRepository.findUserByEmail(userEmail);
    }

    public String findPasswordReg(String userPassword){
        if(userPassword.indexOf(" ") != -1) {
            return "공백은 불가합니다.";
        }else if(userPassword.indexOf(" ") == -1 && userPassword.length() < 6){
            return "6자 이상 입력해주세요.";
        }
        else{ return ""; }
    }

    public String isSamePassword(SiginInDto temp){
        if(temp.getPassword().length() >= 6) {
            if (temp.getPassword().equals(temp.getCheckDuplicate())) {
                return "비밀번호가 같습니다.";
            }
            else {
                return "비밀번호가 다릅니다.";
            }
        }else{ return ""; }
    }


    public String redirectNext(SiginInDto TempUser, HttpSession session){
        if(TempUser.getPassword().indexOf(" ") != -1) {
            return "공백은 불가합니다.";
        }else if(TempUser.getPassword().indexOf(" ") == -1 && TempUser.getPassword().length() < 6){
            return "6자 이상 입력해주세요.";
        }else if(!TempUser.isEmailCheck()){
            return "이메일 중복 확인해주세요.";
        }else{
            if(TempUser.getCheckDuplicate().equals(TempUser.getPassword())){
                session.setAttribute("email", TempUser.getEmail());
                session.setAttribute("pwd", BCrypt.hashpw(TempUser.getPassword(),BCrypt.gensalt()));
                return "다음으로 이동합니다.";
            }
            else{
                return "비밀번호를 다시한번 확인해주세요.";
            }
        }
    }

    public String doneAndValidate(SiginInDto tempUser, HttpServletRequest request){
        String telReg = "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}";
        String nameReg = ".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*";
        String idReg = "\\d{6}[1-4]\\d{6}";

        Pattern patternTel = Pattern.compile(telReg);
        Pattern patternName = Pattern.compile(nameReg);
        Pattern patternId = Pattern.compile(idReg);

        Matcher mTel = patternTel.matcher(tempUser.getTel());
        Matcher mName = patternName.matcher(tempUser.getName());
        Matcher mId = patternId.matcher(tempUser.getPersonalId());

        if(tempUser.getName().equals("")) return "이름을 입력하세요";
        else if(tempUser.getPersonalId().equals("")) return "주민등록번호를 입력하세요";
        else if(tempUser.getGender() == null) return "성별을 선택하세요";
        else if(tempUser.getTel().equals("")) return "핸드폰번호를 입력하세요";
        else if(tempUser.getAddr().equals("")) return "주소를 입력하세요";
        else if(!tempUser.isCheckAgree()) return "동의 여부를 체크해주세요";
        else if(!tempUser.isAuth()) return "인증을 진행해주세요";
        else if(!mTel.matches()) return "휴대폰번호 형식이 잘못되었습니다";
        else if(!mName.matches()) return "이름 형식이 잘못되었습니다.";
        else if(!mId.matches()) return "주민등록번호 형식이 잘못되었습니다.";
        else {
            User user = new User();

            Cookie[]cookies = request.getCookies();
            String deviceId = null;
            for(int i = 0; i< cookies.length; i++){
                if(cookies[i].getName().equals("DeviceId"))
                    deviceId = cookies[i].getValue();
            }

            user.setId(deviceId);
            user.setEmail(session.getAttribute("email").toString());
            user.setPassword(session.getAttribute("pwd").toString());
            user.setName(tempUser.getName());
            user.setGender(tempUser.getGender());
            user.setPersonalId(tempUser.getPersonalId());
            user.setTel(tempUser.getTel());
            user.setAddr(tempUser.getAddr());
            user.setUserType("1");

            if(userRepository.save(user) != null){
                return "가입 완료";
            }else return "가입 실패";
        }
    }
}
