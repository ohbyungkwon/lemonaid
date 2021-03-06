package com.demo.lemonaid.demo.Repository;

import com.demo.lemonaid.demo.Domain.DiseaseService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DiseaseRepository extends JpaRepository<DiseaseService, Integer> {
    @Query("select d from DiseaseService d where d.disease_name=?1")
    DiseaseService selectFindById(String name);
}
