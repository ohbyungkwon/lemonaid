package com.demo.lemonaid.demo.Repository;

import com.demo.lemonaid.demo.Domain.Write;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WriteRepository extends JpaRepository<Write, Integer> {
    @Query("select w from Write w where w.id=?1")
    Write findWrite(int id);
}
