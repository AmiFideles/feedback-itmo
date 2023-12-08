package ru.niu.itmo.feedback.dto;

import lombok.Data;

import javax.validation.constraints.Size;

/**
 * @author amifideles
 */
@Data
public class SiteReviewDto {
    private String comment;
    @Size(max = 255)
    private String contact;
}
