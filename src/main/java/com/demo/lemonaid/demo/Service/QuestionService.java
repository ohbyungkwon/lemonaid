package com.demo.lemonaid.demo.Service;

import com.demo.lemonaid.demo.Adapter.ResultSingleAdapter;
import com.demo.lemonaid.demo.Adapter.ResultWriteAdapter;
import com.demo.lemonaid.demo.Domain.*;
import com.demo.lemonaid.demo.Adapter.ResultMultiAdapter;
import com.demo.lemonaid.demo.Domain.Embeded.ResultKeyMulti;
import com.demo.lemonaid.demo.Domain.Embeded.ResultKeySingle;
import com.demo.lemonaid.demo.Domain.Embeded.ResultKeyWrite;
import com.demo.lemonaid.demo.Domain.Enums.SurveyMessage;
import com.demo.lemonaid.demo.Repository.*;
import com.demo.lemonaid.demo.UserDetail.UserDetail;
import com.demo.lemonaid.demo.session.UserIdSession;
import com.sun.deploy.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class QuestionService {
    private DiseaseRepository diseaseRepository;
    private QuestionRepository questionRepository;

    private ChoiceSingleRepository choiceSingleRepository;
    private ChoiceMultiRepository choiceMultiRepository;

    private ResultSingleRepository resultSingleRepository;
    private ResultMultiRepository resultMultiRepository;
    private ResultWriteRepository resultWriteRepository;

    private UserRepository userRepository;
    private UserIdSession userIdSession;

    @Autowired
    public QuestionService(
            DiseaseRepository diseaseRepository,
            QuestionRepository questionRepository,
            ChoiceSingleRepository choiceSingleRepository,
            ChoiceMultiRepository choiceMultiRepository,
            ResultSingleRepository resultSingleRepository,
            ResultMultiRepository resultMultiRepository,
            ResultWriteRepository resultWriteRepository,
            UserRepository userRepository,
            UserIdSession userIdSession){
        this.diseaseRepository = diseaseRepository;
        this.questionRepository = questionRepository;
        this.choiceSingleRepository = choiceSingleRepository;
        this.choiceMultiRepository = choiceMultiRepository;
        this.resultSingleRepository = resultSingleRepository;
        this.resultMultiRepository = resultMultiRepository;
        this.resultWriteRepository = resultWriteRepository;
        this.userRepository=userRepository;
        this.userIdSession=userIdSession;
    }


    @Transactional(readOnly = true)
    public Question searchQuestion(DiseaseService dTemp, int priority){
        return questionRepository.selectQuestion(dTemp.getId(), priority);//질병에 달린 질문
    }

    @Transactional(readOnly = true)
    public DiseaseService searchDisease(String disease){
        return diseaseRepository.selectFindByName(disease);//질병 선택
    }

    @Transactional(readOnly = true)
    public int totalQuestion(DiseaseService dTemp){
        return questionRepository.getCount(dTemp.getId());
    }

    @Transactional(readOnly = true)
    public String getPrincipalCustom(Authentication authentication){
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        User user = userRepository.findUserByEmail(userDetail.getUsername());
        return user.getId();
    }


    private String findDeviceId(Cookie[] cookies){
        String deviceId = null;
        for(int i = 0; i <cookies.length; i++){
            if(cookies[i].getName().equals("deviceId")){
                deviceId = cookies[i].getValue();
            }
        }
        return deviceId;
    }

    //setting
    @Transactional
    public ResultSingle setInfoSingle(ResultSingle resultSingle, ResultSingleAdapter resultSingleAdapter, Cookie []cookies){
        ResultKeySingle resultKeySingle = new ResultKeySingle();

        if(userIdSession.isAnonymouse()) {
            String deviceId = findDeviceId(cookies);
            resultKeySingle.setUser_id(deviceId);
        }
        else {//프린스펄에서 찾아서 유저의 아이디를 넣은다.
            resultKeySingle.setUser_id(getPrincipalCustom(userIdSession.getAuthentication()));
        }
        resultKeySingle.setQuestion_id(getSingleQuestionId(resultSingleAdapter));

        resultSingle.setId(resultKeySingle);
        resultSingle.setChoice_single_id(resultSingleAdapter.getChoice_single_id());
        resultSingle.setExtra_info(resultSingleAdapter.getExtra_info());
        resultSingle.setChoice(resultSingleAdapter.getChoice());

        return resultSingleRepository.save(resultSingle);
    }

    @Transactional
    public ResultMulti setInfoMulti(ResultMulti resultMulti, ResultMultiAdapter resultMultiAdapter, Cookie[] cookies){
        ResultKeyMulti resultKeyMulti = new ResultKeyMulti();

        List<String> resultTemp = resultMultiAdapter.getChoice();
        String str = StringUtils.join(resultTemp,";");

        if(userIdSession.isAnonymouse()) {
            String deviceId = findDeviceId(cookies);
            resultKeyMulti.setUser_id(deviceId);//현재 세션에 저장된 id로 변경해야함.
        } else {
            resultKeyMulti.setUser_id(getPrincipalCustom(userIdSession.getAuthentication()));//현재 세션에 저장된 id로 변경해야함.
        }

        resultMulti.setChoice(str);
        resultMulti.setExtra_info(resultMultiAdapter.getExtra_info());// TODO: aaa
        resultMulti.setChoice_multi_id(resultMultiAdapter.getChoice_multi_id());
        resultKeyMulti.setQuestion_id(getMultiQuestionId(resultMulti));
        resultMulti.setId(resultKeyMulti);

        return resultMultiRepository.save(resultMulti);
    }

    @Transactional
    public ResultWrite setInfoWrite(ResultWrite resultWrite, ResultWriteAdapter resultWriteAdapter, Cookie []cookies){
        ResultKeyWrite resultKeyWrite = new ResultKeyWrite();

        if(userIdSession.isAnonymouse()) {
            String deviceId = findDeviceId(cookies);
            resultKeyWrite.setUser_id(deviceId);//현재 세션에 저장된 id로 변경해야함.
        }else {
            resultKeyWrite.setUser_id(getPrincipalCustom(userIdSession.getAuthentication()));//현재 세션에 저장된 id로 변경해야함.
        }

        resultKeyWrite.setQuestion_id(7);

        resultWrite.setId(resultKeyWrite);
        resultWrite.setText(resultWriteAdapter.getText());
        resultWrite.setWrite_id(resultWriteAdapter.getWrite_id());

        return resultWriteRepository.save(resultWrite);
    }

    //find a question' id
    @Transactional(readOnly = true)
    public int getSingleQuestionId(ResultSingleAdapter resultSingle){
        return choiceSingleRepository.selectChoicesById(resultSingle.getChoice_single_id()).getQuestion_id();
    }//응답 결과가 어떤 질문에 대한 결과인지 알기 위해 question을 search.

    @Transactional(readOnly = true)
    public int getMultiQuestionId(ResultMulti resultMulti){
        return choiceMultiRepository.selectChoiceMulti(resultMulti.getChoice_multi_id()).getQuestion_id();
    }//응답 결과가 어떤 질문에 대한 결과인지 알기 위해 question을 search.

    public SurveyMessage TempUserValid(User user, String disease){
        DiseaseService diseaseService = diseaseRepository.selectFindByName(disease);

        int age = 0;
        if(!user.getPersonalId().equals("")) age = getAge(user);

        if(age == 0) return SurveyMessage.ERR_BIRTH;//"생년월일을 입력해주세요";
        else if(user.getGender() == null) return SurveyMessage.ERR_GENDER;//"성별을 골라주세요";
        else if(user.getGender().equals(diseaseService.getExceptGender())) {
            return SurveyMessage.ERR_EXCEPT_GENDER;//"참여 대상자가 아닙니다";
        }else if(age > diseaseService.getMaxAge() || age < diseaseService.getMinAge()){
            return SurveyMessage.ERR_EXCEPT_AGE;//"참여 대상자가 아닙니다.";
        }else{
            return SurveyMessage.CONTINUE;//"설문을 시작합니다";
        }
    }//비로그인시 적격판정

    @Transactional(readOnly = true)
    public boolean eligibility(String username, String disease){
        User user = userRepository.findUserByEmail(username);
        DiseaseService diseaseService = diseaseRepository.selectFindByName(disease);

        int age = getAge(user);

        boolean state = false;
        if((!user.getGender().equals(diseaseService.getExceptGender()) || user.getGender() == null)
                && (diseaseService.getMaxAge() > age || diseaseService.getMinAge() < age)){
            state = true;
        }

        return state;
    }//로그인시 적격판정

    private int getAge(User user){
        int birthYear = Integer.parseInt(user.getPersonalId().substring(0,2));
        int birthMonthDate = Integer.parseInt(user.getPersonalId().substring(2,6));

        SimpleDateFormat date = new SimpleDateFormat("YYMMdd");
        int currentYear = Integer.parseInt(date.format(new Date()).substring(0,2));
        int currentMonthDate = Integer.parseInt(date.format(new Date()).substring(2,6));

        int age = (100 - birthYear + currentYear) + 1;
        if(currentMonthDate > birthMonthDate){
            age -= 1;
        }else age -= 2;

        return age;
    }
}