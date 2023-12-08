package ru.niu.itmo.feedback.dto;

import javax.validation.constraints.Size;

/**
 * @author amifideles
 */
public class SiteReviewDto {
    private String comment;
    @Size(max = 255)
    private String contact;
}
