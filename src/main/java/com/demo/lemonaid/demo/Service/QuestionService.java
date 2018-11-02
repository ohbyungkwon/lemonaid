package com.demo.lemonaid.demo.Service;

import com.demo.lemonaid.demo.Domain.*;
import com.demo.lemonaid.demo.Adapter.ResultMultiAdapter;
import com.demo.lemonaid.demo.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    private DiseaseRepository diseaseRepository;
    private QuestionRepository questionRepository;
    private ChoiceSingleRepository choiceSingleRepository;

    @Autowired
    public QuestionService(
            DiseaseRepository diseaseRepository,
            QuestionRepository questionRepository,
            ChoiceSingleRepository choiceSingleRepository){
        this.diseaseRepository = diseaseRepository;
        this.questionRepository = questionRepository;
        this.choiceSingleRepository = choiceSingleRepository;
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
    public ResultMulti MultiAdapter(ResultMulti resultMulti, ResultMultiAdapter resultMultiAdapter){
        String []resultTemp = resultMultiAdapter.getChoice();
        String str="";

        for(int i = 0; i < resultTemp.length; i++){
            str += resultTemp[i]+';';
        }

        resultMulti.setChoice_multi_id(resultMultiAdapter.getChoice_multi_id());
        resultMulti.setExtra_info(resultMultiAdapter.getExtra_info());
        resultMulti.setUser_id("obk");//임시 값
        resultMulti.setChoice(str);

        return  resultMulti;
    }

    public Question getSingleQuestionId(ResultSingle resultSingle){
        ChoiceSingle choiceSingle =  choiceSingleRepository.selectChoicesById(resultSingle.getChoice_single_id());
        return questionRepository.getQuestionById(choiceSingle.getQuestion_id());
    }//응답 결과가 어떤 질문에 대한 결과인지 알기 위해 question을 search.
//다선지, 주관식도 따로 구현해야함.
}
