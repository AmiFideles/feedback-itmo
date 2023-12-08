package ru.niu.itmo.feedback.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.niu.itmo.feedback.entity.SiteReview;

/**
 * @author amifideles
 */
@Repository
public interface SiteReviewRepository extends JpaRepository<SiteReview, Long> {
//    Page<SiteReview> findAll(Pageable pageable);
}
