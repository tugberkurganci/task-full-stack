package com.tobeto_case.tobeto_case.repositories;

import com.tobeto_case.tobeto_case.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token,String> {
    Token findByUserId(int i);

    void deleteByUserId(int id);
}
