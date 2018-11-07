package com.demo.lemonaid.demo.Controller;

import com.demo.lemonaid.demo.Adapter.ResultMultiAdapter;
import com.demo.lemonaid.demo.Domain.*;
import com.demo.lemonaid.demo.Repository.*;
import com.demo.lemonaid.demo.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class QuestionController {
    private ResultSingleRepository resultSingleRepository;
    private ResultMultiRepository resultMultiRepository;
    private QuestionService questionService;
    private ResultWriteRepository resultWriteRepository;

    @Autowired
    public QuestionController(
            ResultSingleRepository resultSingleRepository,
            ResultMultiRepository resultMultiRepository,
            QuestionService questionService,
            ResultWriteRepository resultWriteRepository){
        this.resultSingleRepository = resultSingleRepository;
        this.resultMultiRepository = resultMultiRepository;
        this.resultWriteRepository = resultWriteRepository;
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

    @GetMapping("/temp")
    public ModelAndView temp() {
        String url = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getPrincipal().equals("anonymousUser")){
            url = "login";
        }else{ url = "order"; }

        return new ModelAndView(new RedirectView(url, true));
    }

    @PostMapping("/response/single/{id}")
    @ResponseBody
    public Map<String, Object> saveSingleDB(@PathVariable int id, @RequestBody ResultSingle resultSingle){
        Map<String, Object> map = new HashMap<>();

        resultSingle.setUser_id("obk");//임시 값

        if(resultSingleRepository.save(resultSingle) != null){
            map.put("question_id",questionService.getSingleQuestionId(resultSingle));
            map.put("choice_id",resultSingle.getChoice_single_id());
            map.put("choices",resultSingle.getChoice());
            map.put("extra_info",resultSingle.getExtra_info());
            map.put("state",HttpStatus.OK);
        }else{ map.put("state",HttpStatus.NOT_FOUND);}
        return map;
    }//single

    @PostMapping("/response/multi/{id}")
    @ResponseBody
    public Map<String, Object> saveMultiDB(@PathVariable int id, @RequestBody ResultMultiAdapter resultMultiTemp){
        Map<String, Object> map = new HashMap<>();
        ResultMulti resultMulti = questionService.MultiAdapter(new ResultMulti(), resultMultiTemp);

        if(resultMultiRepository.save(resultMulti) != null){
            map.put("question_id",questionService.getMultiQuestionId(resultMulti));
            map.put("choice_id",resultMulti.getChoice_multi_id());
            map.put("choices",resultMultiTemp.getChoice());
            map.put("extra_info",resultMulti.getExtra_info());
            map.put("state",HttpStatus.OK);
        }else{ map.put("state",HttpStatus.NOT_FOUND);}
        return map;
    }//multi

    @PostMapping("/response/write")
    @ResponseBody
    public Map<String, Object> saveWriteDB(@RequestBody ResultWrite write){
        Map<String, Object> map = new HashMap<>();
        write.setUser_id("obk");//임시 값
        if(resultWriteRepository.save(write) != null){
            map.put("write_id",write.getWrite_id());
            map.put("text",write.getText());
            map.put("state",HttpStatus.OK);
        }else{ map.put("state",HttpStatus.NOT_FOUND);}
        return map;
    }
}
