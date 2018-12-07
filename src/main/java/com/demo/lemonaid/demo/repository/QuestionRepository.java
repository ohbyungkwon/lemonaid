package com.demo.lemonaid.demo.repository;

import com.demo.lemonaid.demo.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    @Query("select q from Question  q where q.diseaseServiceId=?1 and q.priority = ?2")
    Question selectQuestion(int diseaseId, int priority);

    @Query("select max(q.priority) from Question q where q.diseaseServiceId=?1")
    Integer getCount(int diseaseId);
}
