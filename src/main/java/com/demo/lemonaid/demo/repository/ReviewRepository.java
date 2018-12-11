package com.demo.lemonaid.demo.repository;

import com.demo.lemonaid.demo.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    @Query("select r from Review r")
    List<Review> findAll();
}
