package com.demo.lemonaid.demo.Controller;

import com.demo.lemonaid.demo.Domain.*;
import com.demo.lemonaid.demo.Repository.*;
import com.demo.lemonaid.demo.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;
import java.util.HashMap;
import java.util.Map;
//1. 멀티 질문 fk 설정 및 출력 확인
//2. 추가정보 js 처리
//        Page<Question> qTemp  = questionRepository.findAll(PageRequest.of(priority-1, 1, Sort.by(Sort.Direction.DESC, "id")));
//        List<Question> temp = qTemp.getContent();  -> another method

@Controller
public class QuestionController {
    private ResultSingleRepository resultSingleRepository;
    private ResultMultiRepository resultMultiRepository;
    private QuestionService questionService;

    @Autowired
    public QuestionController(
            ResultSingleRepository resultSingleRepository,
            ResultMultiRepository resultMultiRepository,
            QuestionService questionService){
        this.resultSingleRepository = resultSingleRepository;
        this.resultMultiRepository = resultMultiRepository;
        this.questionService = questionService;
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
        DiseaseService dTemp = questionService.SearchDisease(disease);
        Question qTemp  = questionService.SearchQuestion(dTemp, priority);

        model.addAttribute("total_question", questionService.totalQuestion(dTemp));//해당 질병의 마지막 id = 전체 문제 수
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

        ResultMulti resultMulti = questionService.MultiAdpater(new ResultMulti(), resultMultiTemp);

        if(resultMultiRepository.save(resultMulti) != null){
            map.put("chocie_id",resultMulti.getChoice_multi_id());
            map.put("choices",resultMultiTemp.getChoice());
            map.put("extra_info",resultMulti.getExtra_info());
            map.put("state",HttpStatus.OK);
        }else{ map.put("state",HttpStatus.NOT_FOUND);}
        return map;
    }//multi
}
