package ru.niu.itmo.feedback.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.niu.itmo.feedback.entity.Feedback;
import ru.niu.itmo.feedback.entity.FeedbackStatus;

/**
 * @author amifideles
 */
@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    Page<Feedback> findAllByOrderByDateTimeDesc(Pageable pageable);

    Page<Feedback> findByStatusOrderByDateTimeDesc(FeedbackStatus feedbackStatus, Pageable pageable);

    Page<Feedback> findAll(Specification<Feedback> specification, Pageable pageable);
}
