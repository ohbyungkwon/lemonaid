package com.demo.lemonaid.demo.Controller;

import com.demo.lemonaid.demo.Domain.*;
import com.demo.lemonaid.demo.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

//1. 멀티 질문 fk 설정 및 출력 확인
//2. 추가정보 js 처리
//        Page<Question> qTemp  = questionRepository.findAll(PageRequest.of(priority-1, 1, Sort.by(Sort.Direction.DESC, "id")));
//        List<Question> temp = qTemp.getContent();  -> another method

@Controller
public class QuestionController {
    private DiseaseRepository diseaseRepository;
    private QuestionRepository questionRepository;
    private ChoiceSingleRepository choiceSingleRepository;
    private ResultSingleRepository resultSingleRepository;
    private ResultMultiRepository resultMultiRepository;

    @Autowired
    public QuestionController(
            DiseaseRepository diseaseRepository,
            QuestionRepository questionRepository,
            ChoiceSingleRepository choiceSingleRepository,
            ResultSingleRepository resultSingleRepository,
            ResultMultiRepository resultMultiRepository){
        this.diseaseRepository = diseaseRepository;
        this.questionRepository = questionRepository;
        this.choiceSingleRepository = choiceSingleRepository;
        this.resultSingleRepository = resultSingleRepository;
        this.resultMultiRepository = resultMultiRepository;
    }

    @GetMapping("/")

    @ResponseBody
    public String root(){
        return "hello";
    } //TEST

    @GetMapping("/register/NonUser")
    public String nonUser(Model model){
        return "NonLoginUser";
    }

    @GetMapping("/question/{disease}/{priority}")
    public String question(Model model, @PathVariable String disease, @PathVariable int priority){
        DiseaseService dTemp = diseaseRepository.selectFindById(disease);//질병 선택
        Question qTemp  = questionRepository.selectQuestion(dTemp.getId(), priority);//질병에 달린 질문

        model.addAttribute("total_question", questionRepository.getCount(dTemp.getId()));//해당 질병의 마지막 id = 전체 문제 수
        model.addAttribute("disease_name",dTemp.getDisease_name());
        model.addAttribute("question", qTemp);

        if(qTemp.getType().equals("single")){
            model.addAttribute("choices", qTemp.getChoiceSingle());
            model.addAttribute("isState",0);
        }else if(qTemp.getType().equals("multi")){
            model.addAttribute("choices",qTemp.getChoiceMulti());
            model.addAttribute("isState",1);
        }else if(qTemp.getType().equals("write")){
            model.addAttribute("choices",qTemp.getWrite());
            model.addAttribute("isState",2);
        }else{
            model.addAttribute("isState",3);
        }

        return "Question";
    }

    @PostMapping("/response/single/{id}")
    @ResponseBody
    public Map<String, Object> saveSingleDB(@PathVariable int id, @RequestBody ResultSingle resultSingle){
        Map<String, Object> map = new HashMap<>();
        resultSingle.setUser_id("obk");//임시 값
        if(resultSingleRepository.save(resultSingle) != null){
            map.put("data","결과 저장 완료");
        }
        return map;
    }//single

    @PostMapping("/response/multi/{id}")
    @ResponseBody
    public Map<String, Object> saveMultiDB(@PathVariable int id, @RequestBody ResultMultiAdapter resultMultiTemp){
        Map<String, Object> map = new HashMap<>();

        String []resultTemp = resultMultiTemp.getChoice();
        String str="";

        for(int i = 0; i < resultTemp.length; i++){
            str += resultTemp[i]+';';
        }

        ResultMulti resultMulti = new ResultMulti();
        resultMulti.setChoice_multi_id(resultMultiTemp.getChoice_multi_id());
        resultMulti.setExtra_info(resultMultiTemp.getExtra_info());
        resultMulti.setUser_id("obk");//임시 값
        resultMulti.setChoice(str);

        if(resultMultiRepository.save(resultMulti) != null){
            map.put("data","결과 저장 완료");
        }
        return map;
    }//multi
}
