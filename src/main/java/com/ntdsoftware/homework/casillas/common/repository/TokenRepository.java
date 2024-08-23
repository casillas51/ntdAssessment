package com.ntdsoftware.homework.casillas.common.repository;

import com.ntdsoftware.homework.casillas.common.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query("Select t.valid FROM Token t WHERE t.token = :token AND t.user.username = :username")
    Optional<Boolean> isValidByTokenAndUsername(String token, String username);

    @Query("FROM Token t WHERE t.token = :token AND t.user.username = :username")
    Optional<Token> findByTokenAndUsername(String token, String username);

}
