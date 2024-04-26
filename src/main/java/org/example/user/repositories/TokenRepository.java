package org.example.user.repositories;

import jakarta.transaction.Transactional;
import org.example.user.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,Long> {
    public Optional<Token> findTokenByValue(String token);
    @Transactional
    public void deleteTokenById(Long id);
}
