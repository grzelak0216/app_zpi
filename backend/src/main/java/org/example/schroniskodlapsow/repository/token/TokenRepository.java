package org.example.schroniskodlapsow.repository.token;

import org.example.schroniskodlapsow.entity.token.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    Optional<Token> findByToken(String token);
}
