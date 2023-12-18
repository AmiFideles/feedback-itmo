package ru.niu.itmo.feedback.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author amifideles
 */
@Data
public class FeedbackRequestDto {
    @NotNull(message = "firstName id required")
    private String firstName;
    private String lastName;
    private Integer graduationYear;
    private String faculty;
    @NotNull(message = "Mentor name is required")
    private String mentorName;
    @NotNull(message = "Message text is required")
    private String messageText;
//    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$", message = "Invalid email format")
    private String mentorEmail;
    @NotNull(message = "colorId is required")
    private Integer color;
}
