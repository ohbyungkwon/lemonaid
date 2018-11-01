package com.demo.lemonaid.demo.Repository;

import com.demo.lemonaid.demo.Domain.DiseaseService;
import com.demo.lemonaid.demo.Domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    @Query("select q from Question  q where q.disease_service_id=?1 and q.priority = ?2")
    Question selectQuestion(int disease_id, int priority);

    @Query("select max(q.priority) from Question q where q.disease_service_id=?1")
    Integer getCount(int disease_id);

    @Query("select q from Question q where q.id=?1")
    Question getQuestionById(int id);

    Page<Question> findAllById(int id, Pageable pageable);

}
