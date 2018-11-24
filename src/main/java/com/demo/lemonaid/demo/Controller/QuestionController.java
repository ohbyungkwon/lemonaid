package com.demo.lemonaid.demo.Controller;

import com.demo.lemonaid.demo.Adapter.ResultMultiAdapter;
import com.demo.lemonaid.demo.Adapter.ResultSingleAdapter;
import com.demo.lemonaid.demo.Adapter.ResultWriteAdapter;
import com.demo.lemonaid.demo.Domain.*;
import com.demo.lemonaid.demo.Error.ApiDtoSingle;
import com.demo.lemonaid.demo.Service.QuestionService;
import com.demo.lemonaid.demo.UserDetail.UserDetail;
import com.demo.lemonaid.demo.session.UserIdSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

    @GetMapping("/")
    @ResponseBody
    public String root(){
        return "hello";
    }//TEST

    @GetMapping("/register/NonUser")
    public String nonUser(Model model){
//        NonUserUseID += 1;
//        model.addAttribute("userID",NonUserUseID);
        return "NonLoginUser";
    }//비로그인에만 출력

    @PostMapping("/TempUserSet")
    @ResponseBody
    public Map<String, Object> GiveUserID(@RequestBody User user){
        userIdSession.setTempUser(user);

        Map<String, Object> map = new HashMap<>();
        map.put("comment", questionService.TempUserValid(user));

        return map;
    }//비로그인에만 출력, 적격판정의 결과 알람

    @GetMapping("/question")
    public String question(Model model,
                           @RequestParam(value = "disease_name") String disease,
                           @RequestParam(value = "priority") int priority,
                           @RequestParam(value = "isLogin", defaultValue = "0", required = false) int login,
                           HttpServletResponse response){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication.getPrincipal().equals("anonymousUser") && login == 0){
            return "NonLoginUser";
        }
        else if(!authentication.getPrincipal().equals("anonymousUser") && priority == 1){
            UserDetail userDetail = (UserDetail)authentication.getPrincipal();
            boolean state = questionService.eligibility(userDetail.getUsername());
            if(!state) return "WrongUser";
        }
//        System.out.println(authentication.getPrincipal());
        DiseaseService dTemp = questionService.SearchDisease(disease);//질병 선택
        Question qTemp = questionService.SearchQuestion(dTemp, priority);//해당 질병의 id문항을 읽어옴

        model.addAttribute("total_question", questionService.totalQuestion(dTemp));//해당 질병의 마지막 id = 전체 문제 수
        model.addAttribute("disease_name", dTemp.getDisease_name());
        model.addAttribute("question", qTemp);

        if (qTemp.getType().equals("single")) {
            model.addAttribute("choices", qTemp.getChoiceSingle());//1대 n관계
            model.addAttribute("isState", 0);//flag
        } else if (qTemp.getType().equals("multi")) {
            model.addAttribute("choices", qTemp.getChoiceMulti());
            model.addAttribute("isState", 1);
        } else if (qTemp.getType().equals("write")) {
            model.addAttribute("choices", qTemp.getWrite());
            model.addAttribute("isState", 2);
        } else {
            model.addAttribute("isState", 3);
        }

        response.setHeader("Location","survey");

        return "Question";
    }

    @GetMapping("/temp")
    public ModelAndView temp() {
        String url = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.isAuthenticated()){
            url = "login";
        }else{ url = "order"; }

        return new ModelAndView(new RedirectView(url, true));
    }//마지막 문진이 끝날 때 로그인 여부에 따른 리다이렉트

    @PostMapping("/response/single/{id}")
    @ResponseBody
    public Map<String, Object> saveSingleDB(@PathVariable int id, @RequestBody ResultSingleAdapter resultSingleAdapter, HttpServletRequest request){
        return questionService.setInfoSingle(new ResultSingle(), resultSingleAdapter, request.getCookies());
    }//single question's result save

    @PostMapping("/response/multi/{id}")
    @ResponseBody
    public Map<String, Object> saveMultiDB(@PathVariable int id, @RequestBody ResultMultiAdapter resultMultiAdapter,  HttpServletRequest request){
        return questionService.setInfoMulti(new ResultMulti(), resultMultiAdapter, request.getCookies());
    }//multi

    @PostMapping("/response/write")
    @ResponseBody
    public Map<String, Object> saveWriteDB(@RequestBody ResultWriteAdapter resultWriteAdapter,  HttpServletRequest request){
        return questionService.setInfoWrite(new ResultWrite(), resultWriteAdapter, request.getCookies());
    }//write
}