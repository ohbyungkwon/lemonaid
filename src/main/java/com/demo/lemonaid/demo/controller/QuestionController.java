package com.demo.lemonaid.demo.controller;

import com.demo.lemonaid.demo.adapter.ResultMultiAdapter;
import com.demo.lemonaid.demo.adapter.ResultSingleAdapter;
import com.demo.lemonaid.demo.adapter.ResultWriteAdapter;
import com.demo.lemonaid.demo.domain.*;
import com.demo.lemonaid.demo.domain.enums.SurveyMessage;
import com.demo.lemonaid.demo.dto.ApiDtoMulti;
import com.demo.lemonaid.demo.dto.ApiDtoSingle;
import com.demo.lemonaid.demo.dto.ApiDtoWrite;
import com.demo.lemonaid.demo.service.QuestionService;
import com.demo.lemonaid.demo.session.UserIdSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class QuestionController {
    private QuestionService questionService;
    private UserIdSession userIdSession;

    @Autowired
    public QuestionController(
            QuestionService questionService,
            UserIdSession userIdSession){
        this.questionService = questionService;
        this.userIdSession = userIdSession;
    }

    @GetMapping("/WrongUser")
    public String WrongUser(){
        return "WrongUser";
    }

    @PostMapping("/api/TempUserSet")
    @ResponseBody
    public ResponseEntity<SurveyMessage> GiveUserID(@RequestBody User user, @RequestParam(value = "disease_name") String disease){
        SurveyMessage flag = questionService.TempUserValid(user, disease);

        if(flag != SurveyMessage.CONTINUE){ return new ResponseEntity<>(flag, HttpStatus.BAD_REQUEST); }
        return new ResponseEntity<>(flag, HttpStatus.OK);
    }//비로그인에만 출력, 적격판정의 결과 알람

    @GetMapping("/question")
    public String question(Model model,
                           @RequestParam(value = "disease_name") String disease,
                           @RequestParam(value = "priority") int priority,
                           @RequestParam(value = "is_login", defaultValue = "0", required = false) int login,
                           HttpServletResponse response){
        response.setHeader("Location","survey");//For IOS

        Cookie cookie = new Cookie("state","survey");
        response.addCookie(cookie);//For Android

        if(userIdSession.isAnonymouse() && login == 0){
            return "NonLoginUser";
        } else if(!userIdSession.isAnonymouse() && priority >= 1){
            boolean state = questionService.eligibility(userIdSession.getName(), disease);
            if(!state) return "WrongUser";
        }

        DiseaseService dTemp = questionService.searchDisease(disease);//질병 선택
        Question qTemp = questionService.searchQuestion(dTemp, priority);//해당 질병의 문항 번호를 읽어옴

        model.addAttribute("totalQuestion", questionService.totalQuestion(dTemp));//해당 질병의 마지막 id = 전체 문제 수
        model.addAttribute("diseaseName", dTemp.getDiseaseName());
        model.addAttribute("question", qTemp);

        if (qTemp.getType().equals("single")) {
            model.addAttribute("choices", qTemp.getChoiceSingle());//1대 n관계
            model.addAttribute("isState", "single");//단일선택 객관식
        } else if (qTemp.getType().equals("multi")) {
            model.addAttribute("choices", qTemp.getChoiceMulti());
            model.addAttribute("isState", "multi");//다중선택 객관식
        } else if (qTemp.getType().equals("write")) {
            model.addAttribute("choices", qTemp.getWrite());
            model.addAttribute("isState", "write");//주관식
        } else {
            model.addAttribute("isState", "picture");//사진 문진
        }

        return "Question";
    }

    @GetMapping("/temp")
    public ModelAndView temp() {
        String url;
        if(userIdSession.isAnonymouse()){
            url = "login";
        }else{ url = "order"; }

        return new ModelAndView(new RedirectView(url, true));
    }//마지막 문진이 끝날 때 로그인 여부에 따른 리다이렉트

    @PostMapping("/response/single/{id}")
    @ResponseBody
    public ResponseEntity<ApiDtoSingle> saveSingleDB(@PathVariable int id, @RequestBody ResultSingleAdapter resultSingleAdapter, HttpServletRequest request){
        ResultSingle resultSingle = questionService.setInfoSingle(new ResultSingle(), resultSingleAdapter, request.getCookies());
        if(resultSingle == null){
            return ResponseEntity.badRequest().build();
        }
        ApiDtoSingle api = ApiDtoSingle.builder()
                .question_id(resultSingle.getId().getQuestionId())
                .choice_id(resultSingle.getChoiceSingleId())
                .choices(resultSingle.getChoice())
                .extra_info(resultSingle.getExtraInfo())
                .build();
        return new ResponseEntity<>(api, HttpStatus.OK);
    }//single question's result save

    @PostMapping("/response/multi/{id}")
    @ResponseBody
    public ResponseEntity<ApiDtoMulti> saveMultiDB(@PathVariable int id, @RequestBody ResultMultiAdapter resultMultiAdapter, HttpServletRequest request){
        ResultMulti resultMulti = questionService.setInfoMulti(new ResultMulti(), resultMultiAdapter, request.getCookies());
        if(resultMulti == null){
            return ResponseEntity.badRequest().build();
        }

        ApiDtoMulti api = ApiDtoMulti.builder()
                .question_id(resultMulti.getId().getQuestionId())
                .choice_id(resultMulti.getChoiceMultiId())
                .choices(resultMulti.getChoice())
                .extra_info(resultMulti.getExtraInfo())
                .build();
        return new ResponseEntity<>(api, HttpStatus.OK);
    }//multi

    @PostMapping("/response/write")
    @ResponseBody
    public ResponseEntity<ApiDtoWrite> saveWriteDB(@RequestBody ResultWriteAdapter resultWriteAdapter, HttpServletRequest request){
        ResultWrite resultWrite = questionService.setInfoWrite(new ResultWrite(), resultWriteAdapter, request.getCookies());
        if(resultWrite == null){
            return ResponseEntity.badRequest().build();
        }
        ApiDtoWrite api = ApiDtoWrite.builder()
                .question_id(resultWrite.getId().getQuestionId())
                .write_id(resultWrite.getWriteId())
                .text(resultWrite.getText())
                .build();
        return new ResponseEntity<>(api, HttpStatus.OK);
    }//write
}