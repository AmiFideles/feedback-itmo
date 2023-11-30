package ru.niu.itmo.feedback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.niu.itmo.feedback.entity.Color;

/**
 * @author amifideles
 */
@Repository
public interface ColorRepository extends JpaRepository<Color, Integer> {
}
