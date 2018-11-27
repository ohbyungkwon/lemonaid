package com.demo.lemonaid.demo.Repository;

import com.demo.lemonaid.demo.Domain.Question;
import com.demo.lemonaid.demo.Domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    @Query("select r from Review r")
    List<Review> findAll();
}
