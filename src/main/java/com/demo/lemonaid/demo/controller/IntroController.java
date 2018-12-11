package com.demo.lemonaid.demo.controller;

import com.demo.lemonaid.demo.domain.DiseaseService;
import com.demo.lemonaid.demo.domain.Intro;
import com.demo.lemonaid.demo.dto.IntroDto;
import com.demo.lemonaid.demo.domain.Review;
import com.demo.lemonaid.demo.dto.SimpleDto;
import com.demo.lemonaid.demo.service.IntroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class IntroController {
    private IntroService introService;

    @Autowired
    IntroController(IntroService introService){
        this.introService = introService;
    }

    @GetMapping("/api/disease")
    @ResponseBody
    public ResponseEntity<SimpleDto.DiseaseMap> disease(){
        List<DiseaseService> disease= introService.findAllDiseaseList();

        if(disease.size() == 0)
            return ResponseEntity.badRequest().build();

        SimpleDto.DiseaseMap diseaseMap = SimpleDto.DiseaseMap.builder()
                                    .disease(disease)
                                    .build();

        return new ResponseEntity<>(diseaseMap, HttpStatus.OK);
    }

    @GetMapping("/api/intro")
    @ResponseBody
    public ResponseEntity<IntroDto> diseaseChoice(@RequestParam String disease_name){
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

        return new ResponseEntity<>(introDto, HttpStatus.OK);
    }

    @GetMapping("/api/review")
    @ResponseBody
    public ResponseEntity<SimpleDto.ReviewMap> review(){
        List<Review> reviews = introService.findAllReviewList();
        SimpleDto.ReviewMap reviewMap = SimpleDto.ReviewMap.builder()
                .review(reviews)
                .build();
        return new ResponseEntity<>(reviewMap, HttpStatus.OK);
    }
}