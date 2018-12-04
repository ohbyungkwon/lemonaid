package com.demo.lemonaid.demo.Dto;

import com.demo.lemonaid.demo.Domain.DiseaseService;
import com.demo.lemonaid.demo.Domain.Review;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SimpleDto {
    private String status;
    @Data
    @Builder
    public static class ReciveMap{
        private int isSuccess;
        private String reciveId;
    }
    @Data
    @Builder
    public static class DiseaseMap{
        private List<DiseaseService> disease;
    }
    @Data
    @Builder
    public static class ReviewMap{
        private List<Review> review;
    }
}
