package ru.niu.itmo.feedback.mapper;

import org.springframework.data.jpa.domain.Specification;
import ru.niu.itmo.feedback.entity.Feedback;
import ru.niu.itmo.feedback.entity.FeedbackStatus;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @author amifideles
 */
public class FeedbackSpecifications {
    public static Specification<Feedback> buildSpecificationWithSeed(String faculty, Integer graduationYear, int seed) {
        return (Root<Feedback> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            // Добавляем условия фильтрации только в том случае, если параметры не null
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("status"), FeedbackStatus.APPROVED));

            if (faculty != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("faculty"), "%" + faculty + "%"));
            }

            if (graduationYear != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("graduationYear"), graduationYear));
            }


            // Добавляем сортировку по id % seed
            query.orderBy(criteriaBuilder.asc(criteriaBuilder.mod(root.get("id"), seed)));

            return predicate;
        };
    }

    public static Specification<Feedback> buildSpecification(String faculty, Integer graduationYear) {
        return (Root<Feedback> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            // Добавляем условия фильтрации только в том случае, если параметры не null
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("status"), FeedbackStatus.APPROVED));

            if (faculty != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("faculty"), "%" + faculty + "%"));
            }

            if (graduationYear != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("graduationYear"), graduationYear));
            }

            return predicate;
        };
    }
}
