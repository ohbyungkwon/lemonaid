package com.demo.lemonaid.demo.Service;

import com.demo.lemonaid.demo.Domain.*;
import com.demo.lemonaid.demo.Adapter.ResultMultiAdapter;
import com.demo.lemonaid.demo.Repository.*;
import com.demo.lemonaid.demo.session.UserIdSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    private DiseaseRepository diseaseRepository;
    private QuestionRepository questionRepository;
    private ChoiceSingleRepository choiceSingleRepository;
    private ChoiceMultiRepository choiceMultiRepository;
    private WriteRepository writeRepository;
    private UserRepository userRepository;
    private UserIdSession userIdSession;

    @Autowired
    public QuestionService(
            DiseaseRepository diseaseRepository,
            QuestionRepository questionRepository,
            ChoiceSingleRepository choiceSingleRepository,
            ChoiceMultiRepository choiceMultiRepository,
            WriteRepository writeRepository,
            UserRepository userRepository,
            UserIdSession userIdSession){
        this.diseaseRepository = diseaseRepository;
        this.questionRepository = questionRepository;
        this.choiceSingleRepository = choiceSingleRepository;
        this.choiceMultiRepository = choiceMultiRepository;
        this.writeRepository = writeRepository;
        this.userRepository = userRepository;
        this.userIdSession = userIdSession;
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

    public void setInfoSingle(ResultSingle resultSingle){
        resultSingle.setUser_id("obk");//현재 세션에 저장된 id로 변경해야함.
        resultSingle.setQuestion_id(getSingleQuestionId(resultSingle));
    }
    public void setInfoWrite(ResultWrite resultWrite){
        resultWrite.setUser_id("obk");//임시 값
        resultWrite.setQuestion_id(7);
    }

    public ResultMulti MultiAdapter(ResultMulti resultMulti, ResultMultiAdapter resultMultiAdapter){
        String []resultTemp = resultMultiAdapter.getChoice();
        String str="";

        for(int i = 0; i < resultTemp.length; i++){
            str += resultTemp[i]+';';
        }

        resultMulti.setUser_id("obk");
        resultMulti.setChoice(str);
        resultMulti.setExtra_info(resultMultiAdapter.getExtra_info());
        resultMulti.setChoice_multi_id(resultMultiAdapter.getChoice_multi_id());
        resultMulti.setQuestion_id(getMultiQuestionId(resultMulti));

        return  resultMulti;
    }

    public int getSingleQuestionId(ResultSingle resultSingle){
        return choiceSingleRepository.selectChoicesById(resultSingle.getChoice_single_id()).getQuestion_id();
    }//응답 결과가 어떤 질문에 대한 결과인지 알기 위해 question을 search.

    public int getMultiQuestionId(ResultMulti resultMulti){
        return choiceMultiRepository.selectChoiceMulti(resultMulti.getChoice_multi_id()).getQuestion_id();
    }//응답 결과가 어떤 질문에 대한 결과인지 알기 위해 question을 search.

    public String TempUserValid(User user){
        if(user.getPersonal_id() == "") return "생년월일을 입력해주세요";
        else if(user.getGender().indexOf("-1") != -1) return "성별을 골라주세요";
        else if(user.getGender().indexOf("0") != -1) return "남성만 참여가능합니다";
        else{
            return "설문을 시작합니다";
        }
    }
}
