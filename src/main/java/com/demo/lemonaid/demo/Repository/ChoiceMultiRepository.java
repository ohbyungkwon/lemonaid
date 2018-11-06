package com.demo.lemonaid.demo.Repository;

import com.demo.lemonaid.demo.Domain.ChoiceMulti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChoiceMultiRepository extends JpaRepository<ChoiceMulti, Integer> {
    @Query("select c from ChoiceMulti c where c.id=?1")
    ChoiceMulti findChoiceMulti(int id);
}
