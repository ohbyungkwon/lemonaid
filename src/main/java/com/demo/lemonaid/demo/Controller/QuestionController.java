package com.demo.lemonaid.demo.Controller;

import com.demo.lemonaid.demo.Adapter.ResultMultiAdapter;
import com.demo.lemonaid.demo.Adapter.ResultSingleAdapter;
import com.demo.lemonaid.demo.Adapter.ResultWriteAdapter;
import com.demo.lemonaid.demo.Domain.*;
import com.demo.lemonaid.demo.Dto.ApiDtoMulti;
import com.demo.lemonaid.demo.Dto.ApiDtoSingle;
import com.demo.lemonaid.demo.Dto.ApiDtoWrite;
import com.demo.lemonaid.demo.Service.QuestionService;
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
import java.util.HashMap;
import java.util.Map;

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

    @PostMapping("/TempUserSet")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> GiveUserID(@RequestBody User user, @RequestParam(value = "disease_name") String disease){
        Map<String, Object> map = new HashMap<>();
        int flag = questionService.TempUserValid(user, disease);
        map.put("flag", flag);

        if(flag != 0){ return new ResponseEntity<Map<String, Object>>(map,HttpStatus.BAD_REQUEST); }
        return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
    }//비로그인에만 출력, 적격판정의 결과 알람

    @GetMapping("/question")
    public String question(Model model,
                           @RequestParam(value = "disease_name") String disease,
                           @RequestParam(value = "priority") int priority,
                           @RequestParam(value = "isLogin", defaultValue = "0", required = false) int login,
                           HttpServletResponse response){
        response.setHeader("Location","survey");//For IOS

        Cookie cookie = new Cookie("state","survey");
        response.addCookie(cookie);//For Android

        if(userIdSession.getAuthor().equals("ROLE_ANONYMOUS") && login == 0){
            return "NonLoginUser";
        } else if(!userIdSession.getAuthor().equals("ROLE_ANONYMOUS") && priority >= 1){
            boolean state = questionService.eligibility(userIdSession.getName(), disease);
            if(!state) return "WrongUser";
        }

        DiseaseService dTemp = questionService.searchDisease(disease);//질병 선택
        Question qTemp = questionService.searchQuestion(dTemp, priority);//해당 질병의 문항 번호를 읽어옴

        model.addAttribute("total_question", questionService.totalQuestion(dTemp));//해당 질병의 마지막 id = 전체 문제 수
        model.addAttribute("disease_name", dTemp.getDiseaseName());
        model.addAttribute("question", qTemp);

        if (qTemp.getType().equals("single")) {
            model.addAttribute("choices", qTemp.getChoiceSingle());//1대 n관계
            model.addAttribute("isState", 0);//단일선택 객관식
        } else if (qTemp.getType().equals("multi")) {
            model.addAttribute("choices", qTemp.getChoiceMulti());
            model.addAttribute("isState", 1);//다중선택 객관식
        } else if (qTemp.getType().equals("write")) {
            model.addAttribute("choices", qTemp.getWrite());
            model.addAttribute("isState", 2);//주관식
        } else {
            model.addAttribute("isState", 3);//사진 문진
        }

        return "Question";
    }

    @GetMapping("/temp")
    public ModelAndView temp() {
        String url = "";
        if(userIdSession.getAuthor().equals("ROLE_ANONYMOUS")){
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
                .question_id(resultSingle.getId().getQuestion_id())
                .choice_id(resultSingle.getChoice_single_id())
                .choices(resultSingle.getChoice())
                .extra_info(resultSingle.getExtra_info())
                .build();
        return new ResponseEntity<ApiDtoSingle>(api, HttpStatus.OK);
    }//single question's result save

    @PostMapping("/response/multi/{id}")
    @ResponseBody
    public ResponseEntity<ApiDtoMulti> saveMultiDB(@PathVariable int id, @RequestBody ResultMultiAdapter resultMultiAdapter, HttpServletRequest request){
        ResultMulti resultMulti = questionService.setInfoMulti(new ResultMulti(), resultMultiAdapter, request.getCookies());
        if(resultMulti == null){
            return ResponseEntity.badRequest().build();
        }

        ApiDtoMulti api = ApiDtoMulti.builder()
                .question_id(resultMulti.getId().getQuestion_id())
                .choice_id(resultMulti.getChoice_multi_id())
                .choices(resultMulti.getChoice())
                .extra_info(resultMulti.getExtra_info())
                .build();
        return new ResponseEntity<ApiDtoMulti>(api, HttpStatus.OK);
    }//multi

    @PostMapping("/response/write")
    @ResponseBody
    public ResponseEntity<ApiDtoWrite> saveWriteDB(@RequestBody ResultWriteAdapter resultWriteAdapter, HttpServletRequest request){
        ResultWrite resultWrite = questionService.setInfoWrite(new ResultWrite(), resultWriteAdapter, request.getCookies());
        if(resultWrite == null){
            return ResponseEntity.badRequest().build();
        }
        ApiDtoWrite api = ApiDtoWrite.builder()
                .question_id(resultWrite.getId().getQuestion_id())
                .write_id(resultWrite.getWrite_id())
                .text(resultWrite.getText())
                .build();
        return new ResponseEntity<ApiDtoWrite>(api, HttpStatus.OK);
    }//write
}