package ru.niu.itmo.feedback.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author amifideles
 */
@Data
public class FeedbackRequestDto {
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
}
