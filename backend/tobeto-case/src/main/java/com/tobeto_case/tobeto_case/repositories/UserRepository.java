package com.tobeto_case.tobeto_case.repositories;

import com.tobeto_case.tobeto_case.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    boolean existsByEmail(String mail);

    User findByEmail(String email);



}