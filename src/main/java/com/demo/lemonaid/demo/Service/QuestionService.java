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
    private ChoiceMultiRepository choiceMultiRepository;
    private WriteRepository writeRepository;

    @Autowired
    public QuestionService(
            DiseaseRepository diseaseRepository,
            QuestionRepository questionRepository,
            ChoiceSingleRepository choiceSingleRepository,
            ChoiceMultiRepository choiceMultiRepository,
            WriteRepository writeRepository){
        this.diseaseRepository = diseaseRepository;
        this.questionRepository = questionRepository;
        this.choiceSingleRepository = choiceSingleRepository;
        this.choiceMultiRepository = choiceMultiRepository;
        this.writeRepository = writeRepository;
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

    public int getSingleQuestionId(ResultSingle resultSingle){
        return choiceSingleRepository.selectChoicesById(resultSingle.getChoice_single_id()).getQuestion_id();
    }//응답 결과가 어떤 질문에 대한 결과인지 알기 위해 question을 search.

    public int getMultiQuestionId(ResultMulti resultMulti){
        return choiceMultiRepository.findChoiceMulti(resultMulti.getChoice_multi_id()).getQuestion_id();
    }//응답 결과가 어떤 질문에 대한 결과인지 알기 위해 question을 search.

    public int getWriteId(ResultWrite resultWrite){
        return choiceMultiRepository.findChoiceMulti(resultWrite.getWrite_id()).getQuestion_id();
    }//응답 결과가 어떤 질문에 대한 결과인지 알기 위해 question을 search.
}
