package com.demo.lemonaid.demo.repository;

import com.demo.lemonaid.demo.domain.ChoiceSingle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChoiceSingleRepository extends JpaRepository<ChoiceSingle, Integer> {
    @Query("select c from ChoiceSingle c where c.id=?1")
    ChoiceSingle selectChoicesById(int id);
}
