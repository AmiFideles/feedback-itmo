package ru.niu.itmo.feedback.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query("SELECT f FROM Feedback f " +
            "WHERE (f.graduationYear = :graduationYear) " +
            "AND (f.status = 'APPROVED') " +
            "ORDER BY FUNCTION('MOD', f.id, :seed)")
    Page<Feedback> findWithGraduationYear(
            @Param("graduationYear") Integer graduationYear,
            @Param("seed") int seed,
            Pageable pageable);

    @Query("SELECT f FROM Feedback f " +
            "WHERE (f.faculty = :faculty) " +
            "AND (f.status = 'APPROVED') " +
            "ORDER BY FUNCTION('MOD', f.id, :seed)")
    Page<Feedback> findWithFaculty(
            @Param("faculty") String faculty,
            @Param("seed") int seed,
            Pageable pageable);

    @Query("SELECT f FROM Feedback f " +
            "WHERE (f.graduationYear = :graduationYear) " +
            "AND (f.faculty = :faculty) " +
            "AND (f.status = 'APPROVED') " +
            "ORDER BY FUNCTION('MOD', f.id, :seed)")
    Page<Feedback> findWithBothConditions(
            @Param("graduationYear") Integer graduationYear,
            @Param("faculty") String faculty,
            @Param("seed") int seed,
            Pageable pageable);

    @Query("SELECT f FROM Feedback f " +
            "WHERE (f.status = 'APPROVED') " +
            "ORDER BY FUNCTION('MOD', f.id, :seed)")
    Page<Feedback> findWithoutBothConditions(
            @Param("seed") int seed,
            Pageable pageable);

    Page<Feedback> findAll(Specification<Feedback> specification, Pageable pageable);
}
