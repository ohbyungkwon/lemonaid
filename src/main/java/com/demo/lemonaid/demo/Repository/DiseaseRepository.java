package com.demo.lemonaid.demo.Repository;

import com.demo.lemonaid.demo.Domain.DiseaseService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DiseaseRepository extends JpaRepository<DiseaseService, Integer> {
    @Query("select d from DiseaseService d where d.diseaseName=?1")
    DiseaseService selectFindByName(String name);

    @Query("select d from DiseaseService d")
    List<DiseaseService> findAll();
}
