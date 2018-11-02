package com.demo.lemonaid.demo.Service;

import com.demo.lemonaid.demo.Domain.DiseaseService;
import com.demo.lemonaid.demo.Domain.Question;
import com.demo.lemonaid.demo.Domain.ResultMulti;
import com.demo.lemonaid.demo.Domain.ResultMultiAdapter;
import com.demo.lemonaid.demo.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    private DiseaseRepository diseaseRepository;
    private QuestionRepository questionRepository;

    @Autowired
    public QuestionService(
            DiseaseRepository diseaseRepository,
            QuestionRepository questionRepository){
        this.diseaseRepository = diseaseRepository;
        this.questionRepository = questionRepository;
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
    public ResultMulti MultiAdpater(ResultMulti resultMulti, ResultMultiAdapter resultMultiAdapter){
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
}
