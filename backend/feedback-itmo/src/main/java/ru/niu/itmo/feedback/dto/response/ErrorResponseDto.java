package ru.niu.itmo.feedback.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author amifideles
 */
@Data
@AllArgsConstructor
public class ErrorResponseDto {
    private String message;
}
