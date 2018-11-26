package com.demo.lemonaid.demo.Service;

import com.demo.lemonaid.demo.Adapter.ResultSingleAdapter;
import com.demo.lemonaid.demo.Adapter.ResultWriteAdapter;
import com.demo.lemonaid.demo.Domain.*;
import com.demo.lemonaid.demo.Adapter.ResultMultiAdapter;
import com.demo.lemonaid.demo.Domain.Embeded.ResultKeyMulti;
import com.demo.lemonaid.demo.Domain.Embeded.ResultKeySingle;
import com.demo.lemonaid.demo.Domain.Embeded.ResultKeyWrite;
import com.demo.lemonaid.demo.Domain.Enums.Gender;
import com.demo.lemonaid.demo.Error.ApiDtoMulti;
import com.demo.lemonaid.demo.Error.ApiDtoSingle;
import com.demo.lemonaid.demo.Error.ApiDtoWrite;
import com.demo.lemonaid.demo.Repository.*;
import com.demo.lemonaid.demo.UserDetail.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Column;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class QuestionService {
    private DiseaseRepository diseaseRepository;
    private QuestionRepository questionRepository;

    private ChoiceSingleRepository choiceSingleRepository;
    private ChoiceMultiRepository choiceMultiRepository;

    private ResultSingleRepository resultSingleRepository;
    private ResultMultiRepository resultMultiRepository;
    private ResultWriteRepository resultWriteRepository;
    private UserRepository userRepository;

    @Autowired
    public QuestionService(
            DiseaseRepository diseaseRepository,
            QuestionRepository questionRepository,
            ChoiceSingleRepository choiceSingleRepository,
            ChoiceMultiRepository choiceMultiRepository,
            ResultSingleRepository resultSingleRepository,
            ResultMultiRepository resultMultiRepository,
            ResultWriteRepository resultWriteRepository,
            UserRepository userRepository){
        this.diseaseRepository = diseaseRepository;
        this.questionRepository = questionRepository;
        this.choiceSingleRepository = choiceSingleRepository;
        this.choiceMultiRepository = choiceMultiRepository;
        this.resultSingleRepository = resultSingleRepository;
        this.resultMultiRepository = resultMultiRepository;
        this.resultWriteRepository = resultWriteRepository;
        this.userRepository=userRepository;
    }

    public Question SearchQuestion(DiseaseService dTemp, int priority){
        Question qTemp  = questionRepository.selectQuestion(dTemp.getId(), priority);//질병에 달린 질문
        return  qTemp;
    }

    public DiseaseService SearchDisease(String disease){
        DiseaseService dTemp = diseaseRepository.selectFindById(disease);//질병 선택
        return  dTemp;
    }
    public int totalQuestion(DiseaseService dTemp){
        int total = questionRepository.getCount(dTemp.getId());
        return total;
    }

    public String getPrincipalCustom(Authentication authentication){
        //System.out.println(authentication.getPrincipal().getClass().getCanonicalName());
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        User user = userRepository.findUserByEmail(userDetail.getUsername());
        return user.getId();
    }

    public String findDeviceId(Cookie[] cookies){
        String deviceId = null;
        for(int i = 0; i <cookies.length; i++){
            if(cookies[i].getName().equals("DeviceId")){
                deviceId = cookies[i].getValue();
            }
        }
        return deviceId;
    }
    //setting
    public ResponseEntity<?> setInfoSingle(ResultSingle resultSingle, ResultSingleAdapter resultSingleAdapter, Cookie []cookies){
        ResultKeySingle resultKeySingle = new ResultKeySingle();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication.getPrincipal().equals("anonymousUser")) {
            String deviceId = findDeviceId(cookies);
            resultKeySingle.setUser_id(deviceId);
        }
        else {//프린스펄에서 찾아서 유저의 아이디를 넣은다.
            resultKeySingle.setUser_id(getPrincipalCustom(authentication));
        }
        resultKeySingle.setQuestion_id(getSingleQuestionId(resultSingleAdapter));

        resultSingle.setId(resultKeySingle);
        resultSingle.setChoice_single_id(resultSingleAdapter.getChoice_single_id());
        resultSingle.setExtra_info(resultSingleAdapter.getExtra_info());
        resultSingle.setChoice(resultSingleAdapter.getChoice());

        ApiDtoSingle api;

        if(resultSingleRepository.save(resultSingle) != null){
            api = ApiDtoSingle.builder()
                    .question_id(resultSingle.getId().getQuestion_id())
                    .choice_id(resultSingle.getChoice_single_id())
                    .choices(resultSingle.getChoice())
                    .extra_info(resultSingle.getExtra_info())
                    .build();
            return new ResponseEntity<ApiDtoSingle>(api, HttpStatus.OK);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<?> setInfoMulti(ResultMulti resultMulti, ResultMultiAdapter resultMultiAdapter, Cookie[] cookies){
        ResultKeyMulti resultKeyMulti = new ResultKeyMulti();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String []resultTemp = resultMultiAdapter.getChoice();
        String str="";

        for(int i = 0; i < resultTemp.length; i++) {
            str += resultTemp[i] + ';';
        }
        if(authentication.getPrincipal().equals("anonymousUser")) {
            String deviceId = findDeviceId(cookies);
            resultKeyMulti.setUser_id(deviceId);//현재 세션에 저장된 id로 변경해야함.
        } else {
            resultKeyMulti.setUser_id(getPrincipalCustom(authentication));//현재 세션에 저장된 id로 변경해야함.
        }

        resultMulti.setChoice(str);
        resultMulti.setExtra_info(resultMultiAdapter.getExtra_info());
        resultMulti.setChoice_multi_id(resultMultiAdapter.getChoice_multi_id());
        resultKeyMulti.setQuestion_id(getMultiQuestionId(resultMulti));
        resultMulti.setId(resultKeyMulti);

        Map<String, Object> map = new HashMap<>();

        ApiDtoMulti api;

        if(resultMultiRepository.save(resultMulti) != null){
            api = ApiDtoMulti.builder()
                    .question_id(resultMulti.getId().getQuestion_id())
                    .choice_id(resultMulti.getChoice_multi_id())
                    .choices(resultMulti.getChoice())
                    .extra_info(resultMulti.getExtra_info())
                    .build();
            return new ResponseEntity<ApiDtoMulti>(api, HttpStatus.OK);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }


//    public Map<String, Object> setInfoSingle(ResultSingle resultSingle, ResultSingleAdapter resultSingleAdapter, HttpSession session){
//        ResultKeySingle resultKeySingle = new ResultKeySingle();
//        if(session != null)
//            resultKeySingle.setUser_id(session.getAttribute("DeviceId").toString());//현재 세션에 저장된 id로 변경해야함.
//        else {
//            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            resultKeySingle.setUser_id(user.getId());//현재 세션에 저장된 id로 변경해야함.
//        }
//        resultKeySingle.setQuestion_id(getSingleQuestionId(resultSingleAdapter));
//
//        resultSingle.setId(resultKeySingle);
//        resultSingle.setChoice_single_id(resultSingleAdapter.getChoice_single_id());
//        resultSingle.setExtra_info(resultSingleAdapter.getExtra_info());
//        resultSingle.setChoice(resultSingleAdapter.getChoice());
//
//        Map<String, Object> map = new HashMap<>();
//
//        if(resultSingleRepository.save(resultSingle) != null){
//            map.put("question_id",resultSingle.getId().getQuestion_id());
//            map.put("choice_id",resultSingle.getChoice_single_id());
//            map.put("choices",resultSingle.getChoice());
//            map.put("extra_info",resultSingle.getExtra_info());
//            map.put("state", HttpStatus.OK);
//        }else{ map.put("state",HttpStatus.NOT_FOUND);}
//
//        return map;
//    }
//
//    public Map<String, Object> setInfoMulti(ResultMulti resultMulti, ResultMultiAdapter resultMultiAdapter, HttpSession session){
//        String []resultTemp = resultMultiAdapter.getChoice();
//        ResultKeyMulti resultKeyMulti = new ResultKeyMulti();
//        String str="";
//
//        for(int i = 0; i < resultTemp.length; i++) {
//            str += resultTemp[i] + ';';
//        }
//        if(session != null)
//            resultKeyMulti.setUser_id(session.getAttribute("DeviceId").toString());//현재 세션에 저장된 id로 변경해야함.
//        else {
//            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            resultKeyMulti.setUser_id(user.getId());//현재 세션에 저장된 id로 변경해야함.
//        }
//
//        resultMulti.setChoice(str);
//        resultMulti.setExtra_info(resultMultiAdapter.getExtra_info());
//        resultMulti.setChoice_multi_id(resultMultiAdapter.getChoice_multi_id());
//        resultKeyMulti.setQuestion_id(getMultiQuestionId(resultMulti));
//        resultMulti.setId(resultKeyMulti);
//
//        Map<String, Object> map = new HashMap<>();
//
//        if(resultMultiRepository.save(resultMulti) != null){
//            map.put("question_id",resultMulti.getId().getQuestion_id());
//            map.put("choice_id",resultMulti.getChoice_multi_id());
//            map.put("choices",resultMulti.getChoice());
//            map.put("extra_info",resultMulti.getExtra_info());
//            map.put("state",HttpStatus.OK);
//        }else{ map.put("state",HttpStatus.NOT_FOUND);}
//
//        return map;
//    }

    public ResponseEntity<?> setInfoWrite(ResultWrite resultWrite, ResultWriteAdapter resultWriteAdapter, Cookie []cookies){
        ResultKeyWrite resultKeyWrite = new ResultKeyWrite();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication.getPrincipal().equals("anonymousUser")) {
            String deviceId = findDeviceId(cookies);
            resultKeyWrite.setUser_id(deviceId);//현재 세션에 저장된 id로 변경해야함.
        }else {
            resultKeyWrite.setUser_id(getPrincipalCustom(authentication));//현재 세션에 저장된 id로 변경해야함.
        }

        resultKeyWrite.setQuestion_id(7);

        resultWrite.setId(resultKeyWrite);
        resultWrite.setText(resultWriteAdapter.getText());
        resultWrite.setWrite_id(resultWriteAdapter.getWrite_id());

        Map<String, Object> map = new HashMap<>();

        ApiDtoWrite api;
        if(resultWriteRepository.save(resultWrite) != null){
            api = ApiDtoWrite.builder()
                    .question_id(resultWrite.getId().getQuestion_id())
                    .write_id(resultWrite.getWrite_id())
                    .text(resultWrite.getText())
                    .build();
            return new ResponseEntity<ApiDtoWrite>(api, HttpStatus.OK);
        }else{ return ResponseEntity.badRequest().build(); }
    }

    //find a question' id
    public int getSingleQuestionId(ResultSingleAdapter resultSingle){
        return choiceSingleRepository.selectChoicesById(resultSingle.getChoice_single_id()).getQuestion_id();
    }//응답 결과가 어떤 질문에 대한 결과인지 알기 위해 question을 search.

    public int getMultiQuestionId(ResultMulti resultMulti){
        return choiceMultiRepository.selectChoiceMulti(resultMulti.getChoice_multi_id()).getQuestion_id();
    }//응답 결과가 어떤 질문에 대한 결과인지 알기 위해 question을 search.

    public String TempUserValid(User user){
        if(user.getPersonal_id() == "") return "생년월일을 입력해주세요";
        else if(user.getGender() == null) return "성별을 골라주세요";
        else if(user.getGender().equals(Gender.WOMAN.toString())) return "남성만 참여가능합니다";
        else{
            return "설문을 시작합니다";
        }
    }

    public boolean eligibility(String username){
        User user = userRepository.findUserByEmail(username);
        boolean state = false;
        if(user.getGender().equals(Gender.MAN.toString()) || user.getGender() == null){
            state = true;
        }
        return state;
    }
}