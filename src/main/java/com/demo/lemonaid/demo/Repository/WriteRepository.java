package com.demo.lemonaid.demo.Repository;

import com.demo.lemonaid.demo.Domain.Write;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.CriteriaBuilder;

public interface WriteRepository extends JpaRepository<Write, Integer> {
}
