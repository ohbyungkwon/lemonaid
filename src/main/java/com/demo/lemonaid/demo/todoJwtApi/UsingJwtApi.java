package com.demo.lemonaid.demo.todoJwtApi;

import com.demo.lemonaid.demo.domain.DiseaseService;
import com.demo.lemonaid.demo.domain.Intro;
import com.demo.lemonaid.demo.domain.Pharmacy;
import com.demo.lemonaid.demo.domain.Review;
import com.demo.lemonaid.demo.dto.ApiSavePharmacy;
import com.demo.lemonaid.demo.dto.IntroDto;
import com.demo.lemonaid.demo.dto.SimpleDto;
import com.demo.lemonaid.demo.service.IntroService;
import com.demo.lemonaid.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsingJwtApi {
    private UserService userService;
    private IntroService introService;

    @Autowired
    UsingJwtApi(UserService userService, IntroService introService) {
        this.userService = userService;
        this.introService = introService;
    }

    // TODO : 로그인한 유저만 접근 할 수 있는 API
    @PostMapping("/jwt/saveMapLocation")
    public ResponseEntity<ApiSavePharmacy> saveMapLocation(@RequestBody Pharmacy pharmacy){
        String userEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        pharmacy.setDeviceId(userEmail);

        if(userService.savePharmacy(userEmail, pharmacy) != null){
            ApiSavePharmacy api =  ApiSavePharmacy.builder()
                    .name(pharmacy.getName())
                    .lat(pharmacy.getLat())
                    .lon(pharmacy.getLon())
                    .deviceId(userEmail)
                    .build();
            return new ResponseEntity<>(api, HttpStatus.OK);
        }else {
            return ResponseEntity.badRequest().build();
        }
    }

    // TODO : 모든 사용자가 접근할 수 있는 API
    @GetMapping("/jwt/disease")
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

    @GetMapping("/jwt/intro")
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

    @GetMapping("/jwt/review")
    @ResponseBody
    public ResponseEntity<SimpleDto.ReviewMap> review(){
        List<Review> reviews = introService.findAllReviewList();
        SimpleDto.ReviewMap reviewMap = SimpleDto.ReviewMap.builder()
                .review(reviews)
                .build();
        return new ResponseEntity<>(reviewMap, HttpStatus.OK);
    }
}

