package com.demo.lemonaid.demo.Controller;

import com.demo.lemonaid.demo.Domain.DiseaseService;
import com.demo.lemonaid.demo.Domain.Intro;
import com.demo.lemonaid.demo.Dto.IntroDto;
import com.demo.lemonaid.demo.Domain.Review;
import com.demo.lemonaid.demo.Service.IntroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IntroController {
    private IntroService introService;

    @Autowired
    IntroController(IntroService introService){
        this.introService = introService;
    }

    @GetMapping("/api/disease")
    @ResponseBody
    public Map<String, Object> disease(){
        Map<String, Object> map = new HashMap<>();
        List<DiseaseService> disease= introService.findAllDiseaseList();
        map.put("disease",disease);
        return map;
    }

    @GetMapping("/api/intro")
    @ResponseBody
    public ResponseEntity<?> diseaseChoice(@RequestParam String disease_name){
        Intro intro = introService.findByDiseaseName(disease_name);
        String[] tokens = intro.getExceptService().split(";");

        IntroDto introDto = IntroDto.builder()
                .name(disease_name)
                .medicineName(intro.getMedicineName())
                .intro(intro.getIntro())
                .guideExam(intro.getGuideExam())
                .guidePay(intro.getGuidePay())
                .guidePresc(intro.getGuidePresc())
                .guidePick(intro.getGuidePick())
                .define(intro.getDefine())
                .treatAndRisk(intro.getTreatAndRisk())
                .comment(intro.getComment())
                .exceptService(tokens)
                .guideStart(intro.getGuideStart())
                .build();

        return new ResponseEntity<IntroDto>(introDto, HttpStatus.OK);
    }

    @GetMapping("/api/review")
    @ResponseBody
    public Map<String, Object> review(){
        Map<String, Object> map = new HashMap<>();
        List<Review> reviews = introService.findAllReviewList();
        map.put("review",reviews);
        return map;
    }
}