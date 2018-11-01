package com.demo.lemonaid.demo.Repository;

import com.demo.lemonaid.demo.Domain.ChoiceSingle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChoiceSingleRepository extends JpaRepository<ChoiceSingle, Integer> {
    @Query("select c from ChoiceSingle c where c.question_id=id")
    List<ChoiceSingle> selectChoices(int id);

    @Query("select c from ChoiceSingle c where c.id=id")
    ChoiceSingle selectChoicesById(int id);
}
