package com.demo.lemonaid.demo.Service;

import com.demo.lemonaid.demo.Adapter.ResultSingleAdapter;
import com.demo.lemonaid.demo.Adapter.ResultWriteAdapter;
import com.demo.lemonaid.demo.Controller.UserController;
import com.demo.lemonaid.demo.Domain.*;
import com.demo.lemonaid.demo.Adapter.ResultMultiAdapter;
import com.demo.lemonaid.demo.Repository.*;
import com.demo.lemonaid.demo.session.UserIdSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired private UserService userService;
    @Autowired private UserController userController;

    @Autowired
    public QuestionService(
            DiseaseRepository diseaseRepository,
            QuestionRepository questionRepository,
            ChoiceSingleRepository choiceSingleRepository,
            ChoiceMultiRepository choiceMultiRepository,
            ResultSingleRepository resultSingleRepository,
            ResultMultiRepository resultMultiRepository,
            ResultWriteRepository resultWriteRepository){
        this.diseaseRepository = diseaseRepository;
        this.questionRepository = questionRepository;
        this.choiceSingleRepository = choiceSingleRepository;
        this.choiceMultiRepository = choiceMultiRepository;
        this.resultSingleRepository = resultSingleRepository;
        this.resultMultiRepository = resultMultiRepository;
        this.resultWriteRepository = resultWriteRepository;
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


    //setting
    public ResultSingle setInfoSingle(ResultSingle resultSingle, ResultSingleAdapter resultSingleAdapter){
        resultSingle.setQuestion_id(getSingleQuestionId(resultSingleAdapter));
        resultSingle.setChoice_single_id(resultSingleAdapter.getChoice_single_id());
        resultSingle.setUser_id("obk");//현재 세션에 저장된 id로 변경해야함.
        System.out.println();
        resultSingle.setExtra_info(resultSingleAdapter.getExtra_info());
        resultSingle.setChoice(resultSingleAdapter.getChoice());
        return resultSingle;
    }

    public ResultMulti setInfoMulti(ResultMulti resultMulti, ResultMultiAdapter resultMultiAdapter){
        String []resultTemp = resultMultiAdapter.getChoice();
        String str="";

        for(int i = 0; i < resultTemp.length; i++) {
            str += resultTemp[i] + ';';
        }
        resultMulti.setUser_id("obk");
        resultMulti.setChoice(str);
        resultMulti.setExtra_info(resultMultiAdapter.getExtra_info());
        resultMulti.setChoice_multi_id(resultMultiAdapter.getChoice_multi_id());
        resultMulti.setQuestion_id(getMultiQuestionId(resultMulti));

        return  resultMulti;
    }

    public ResultWrite setInfoWrite(ResultWrite resultWrite, ResultWriteAdapter resultWriteAdapter){
        resultWrite.setUser_id("obk");
        resultWrite.setQuestion_id(7);
        resultWrite.setText(resultWriteAdapter.getText());
        resultWrite.setWrite_id(resultWriteAdapter.getWrite_id());

        return resultWrite;
    }


    //find a question' id
    public int getSingleQuestionId(ResultSingleAdapter resultSingle){
        return choiceSingleRepository.selectChoicesById(resultSingle.getChoice_single_id()).getQuestion_id();
    }//응답 결과가 어떤 질문에 대한 결과인지 알기 위해 question을 search.

    public int getMultiQuestionId(ResultMulti resultMulti){
        return choiceMultiRepository.selectChoiceMulti(resultMulti.getChoice_multi_id()).getQuestion_id();
    }//응답 결과가 어떤 질문에 대한 결과인지 알기 위해 question을 search.


    //api
    public Map<String, Object> returnApiSingle(ResultSingle resultSingle){
        Map<String, Object> map = new HashMap<>();

        if(resultSingleRepository.save(resultSingle) != null){
            map.put("question_id",resultSingle.getQuestion_id());
            map.put("choice_id",resultSingle.getChoice_single_id());
            map.put("choices",resultSingle.getChoice());
            map.put("extra_info",resultSingle.getExtra_info());
            map.put("state", HttpStatus.OK);
        }else{ map.put("state",HttpStatus.NOT_FOUND);}

        return map;
    }

    public Map<String, Object> returnApiMulti(ResultMulti resultMulti){
        Map<String, Object> map = new HashMap<>();

        if(resultMultiRepository.save(resultMulti) != null){
            map.put("question_id",resultMulti.getQuestion_id());
            map.put("choice_id",resultMulti.getChoice_multi_id());
            map.put("choices",resultMulti.getChoice());
            map.put("extra_info",resultMulti.getExtra_info());
            map.put("state",HttpStatus.OK);
        }else{ map.put("state",HttpStatus.NOT_FOUND);}

        return map;
    }

    public Map<String, Object> returnApiWrite(ResultWrite resultWrite){
        Map<String, Object> map = new HashMap<>();

        if(resultWriteRepository.save(resultWrite) != null){
            map.put("write_id",resultWrite.getWrite_id());
            map.put("text",resultWrite.getText());
            map.put("state",HttpStatus.OK);
        }else{ map.put("state",HttpStatus.NOT_FOUND);}

        return map;
    }

    public String TempUserValid(User user){
        if(user.getPersonal_id() == "") return "생년월일을 입력해주세요";
        else if(user.getGender().indexOf("-1") != -1) return "성별을 골라주세요";
        else if(user.getGender().indexOf("0") != -1) return "남성만 참여가능합니다";
        else{
            return "설문을 시작합니다";
        }
    }
}
