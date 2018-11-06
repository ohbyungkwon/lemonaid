package com.demo.lemonaid.demo.Repository;

import com.demo.lemonaid.demo.Domain.ResultSingle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ResultSingleRepository extends JpaRepository<ResultSingle, Integer> {
    @Query("select r from ResultSingle r where r.choice_single_id = ?1 and r.id = ?2")
    ResultSingle findResultSingle(int choice_id, int id);
}
