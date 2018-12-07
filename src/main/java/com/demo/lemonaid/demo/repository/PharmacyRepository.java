package com.demo.lemonaid.demo.repository;

import com.demo.lemonaid.demo.domain.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Integer> {
}
