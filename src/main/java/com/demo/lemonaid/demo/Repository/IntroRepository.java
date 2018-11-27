package com.demo.lemonaid.demo.Repository;

import com.demo.lemonaid.demo.Domain.DiseaseService;
import com.demo.lemonaid.demo.Domain.Intro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IntroRepository extends JpaRepository<Intro, Integer> {
    @Query("select i from Intro i where i.disease=?1")
    Intro findByDiseaseName(String name);
}
