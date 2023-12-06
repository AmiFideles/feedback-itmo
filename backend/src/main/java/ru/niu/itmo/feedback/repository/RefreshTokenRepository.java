package ru.niu.itmo.feedback.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.niu.itmo.feedback.entity.security.RefreshToken;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
    void deleteByUserId(Long id);
}
