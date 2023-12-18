package ru.niu.itmo.feedback.dto.response;


import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

/**
 * @author amifideles
 */
@Data
public class FeedbackResponseDto {

    private Long id;
    @NotNull(message = "firstName id required")
    private String firstName;
    private String lastName;
    private Integer graduationYear;
    private String faculty;
    @NotNull(message = "Mentor name cannot be null")
    private String mentorName;
    @NotNull(message = "Message text cannot be null")
    private String messageText;
    private String mentorEmail;
    private String photoUrl;
    private String status;
    private LocalDateTime dateTime;
    @NotNull(message = "colorId is required")
    private Integer color;
}
