package com.demo.lemonaid.demo.Repository;

import com.demo.lemonaid.demo.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
