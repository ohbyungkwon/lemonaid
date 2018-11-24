package com.demo.lemonaid.demo.Repository;

import com.demo.lemonaid.demo.Domain.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.CriteriaBuilder;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Integer> {
}
