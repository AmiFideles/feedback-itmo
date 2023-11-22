package ru.niu.itmo.feedback.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.niu.itmo.feedback.entity.Feedback;
import ru.niu.itmo.feedback.entity.FeedbackStatus;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author amifideles
 */
public class FeedbackSpecifications {
    public static Specification<Feedback> hasStatus(FeedbackStatus status) {
        return (root, query, builder) -> builder.equal(root.get("status"), status);
    }

    public static Specification<Feedback> hasGraduationYear(Integer graduationYear) {
        return (root, query, builder) -> {
            if (graduationYear == null) {
                return builder.conjunction();
            } else {
                return builder.equal(root.get("graduationYear"), graduationYear);
            }
        };
    }

    public static Specification<Feedback> hasFaculty(String faculty) {
        return (root, query, builder) -> {
            if (faculty == null) {
                return builder.conjunction();
            } else {
                return builder.equal(root.get("faculty"), faculty);
            }
        };
    }


}
