package com.demo.lemonaid.demo.Service;

import com.demo.lemonaid.demo.Dto.SiginInDto;
import com.demo.lemonaid.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional(readOnly = true)
public class SignInService {
    private UserRepository userRepository;

    @Autowired
    SignInService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public String findDuplicate(String userEmail){
        String reg =  "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";

        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(userEmail);
        if(userRepository.findUserByEmail(userEmail) == null && m.matches()){
            return "사용 가능한 아이디입니다.";
        }
        else {
            return "사용 불가한 아이디입니다.";
        }
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

    public String done(SiginInDto temp){
        String telReg = "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}";
        String nameReg = ".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*";
        String idReg = "\\d{6}[1-4]\\d{6}";

        Pattern patternTel = Pattern.compile(telReg);
        Pattern patternName = Pattern.compile(nameReg);
        Pattern patternId = Pattern.compile(idReg);

        Matcher mTel = patternTel.matcher(temp.getTel());
        Matcher mName = patternName.matcher(temp.getName());
        Matcher mId = patternId.matcher(temp.getPersonalId());

        if(temp.getName().equals("")) return "이름을 입력하세요";
        else if(temp.getPersonalId().equals("")) return "주민등록번호를 입력하세요";
        else if(temp.getGender() == null) return "성별을 선택하세요";
        else if(temp.getTel().equals("")) return "핸드폰번호를 입력하세요";
        else if(temp.getAddr().equals("")) return "주소를 입력하세요";
        else if(!temp.isCheckAgree()) return "동의 여부를 체크해주세요";
        else if(!temp.isAuth()) return "인증을 진행해주세요";
        else if(!mTel.matches()) return "휴대폰번호 형식이 잘못되었습니다";
        else if(!mName.matches()) return "이름 형식이 잘못되었습니다.";
        else if(!mId.matches()) return "주민등록번호 형식이 잘못되었습니다.";
        else {
            return "가입 완료";
        }
    }
}
