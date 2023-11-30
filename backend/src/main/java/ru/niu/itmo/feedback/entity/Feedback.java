package ru.niu.itmo.feedback.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author amifideles
 */
@Entity
@Data
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "feedback_generator")
    @SequenceGenerator(name = "feedback_generator", initialValue = 100, allocationSize = 1)
    private Long id;

    private String firstName;

    private String lastName;

    @NotNull(message = "Graduation year is required")
    private Integer graduationYear;

    @NotNull(message = "Faculty must be null")
    private String faculty;

    @NotNull(message = "Mentor name must be null")
    private String mentorName;

    @NotNull(message = "Message text must be null")
    private String messageText;

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$", message = "Invalid email format")
    private String mentorEmail;

    private String photoUrl;

    @Enumerated(EnumType.STRING)
    private FeedbackStatus status;

    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;

    //    @Null(message = "Gratitude type is required")
//    @Enumerated(EnumType.STRING)
//    private GratitudeType gratitudeType;
}