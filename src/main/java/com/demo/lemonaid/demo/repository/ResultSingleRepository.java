package com.demo.lemonaid.demo.repository;

import com.demo.lemonaid.demo.domain.ResultSingle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ResultSingleRepository extends JpaRepository<ResultSingle, Integer> {
    @Query("select r from ResultSingle r where r.choiceSingleId = ?1 and r.id = ?2")
    ResultSingle findResultSingle(int choiceId, int id);
}
