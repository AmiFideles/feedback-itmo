package ru.niu.itmo.feedback.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author amifideles
 */
@Data
public class UpdateFeedbackDto {

    private Long id;
    @NotNull(message = "firstName id required")
    private String firstName;
    private String lastName;
    private String faculty;
    @NotNull(message = "Mentor name cannot be null")
    private String mentorName;
    @NotNull(message = "Message text cannot be null")
    private String messageText;
    private String mentorEmail;
    private String status;
}
