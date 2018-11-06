package com.demo.lemonaid.demo.Repository;

import com.demo.lemonaid.demo.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select user from User user where user.email=?1")
    User findUserByEmail(String email);
}
