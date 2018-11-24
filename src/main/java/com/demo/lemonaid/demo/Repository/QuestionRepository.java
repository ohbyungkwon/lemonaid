package com.demo.lemonaid.demo.Repository;

import com.demo.lemonaid.demo.Domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    @Query("select q from Question  q where q.disease_service_id=?1 and q.priority = ?2")
    Question selectQuestion(int disease_id, int priority);

    @Query("select max(q.priority) from Question q where q.disease_service_id=?1")
    Integer getCount(int disease_id);
}
