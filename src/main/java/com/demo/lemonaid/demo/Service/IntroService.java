package com.demo.lemonaid.demo.Service;

import com.demo.lemonaid.demo.Domain.DiseaseService;
import com.demo.lemonaid.demo.Domain.Intro;
import com.demo.lemonaid.demo.Domain.Review;
import com.demo.lemonaid.demo.Repository.DiseaseRepository;
import com.demo.lemonaid.demo.Repository.IntroRepository;
import com.demo.lemonaid.demo.Repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class IntroService {
    private DiseaseRepository diseaseRepository;
    private IntroRepository introRepository;
    private ReviewRepository reviewRepository;

    @Autowired
    IntroService(DiseaseRepository diseaseRepository,
                    IntroRepository introRepository,
                    ReviewRepository reviewRepository){
        this.diseaseRepository = diseaseRepository;
        this.introRepository = introRepository;
        this.reviewRepository = reviewRepository;
    }

    public List<DiseaseService> findAllDiseaseList(){
        return diseaseRepository.findAll();
    }

    public Intro findByDiseaseName(String diseaseName){
        return introRepository.findByDiseaseName(diseaseName);
    }

    public List<Review> findAllReviewList(){
        return reviewRepository.findAll();
    }
}
