package ru.niu.itmo.feedback.dto.response;

import lombok.Value;

/**
 * @author amifideles
 */
@Value
public class ColorResponseDto {
    Integer id;
    String color;
    String light;
}
