package ru.niu.itmo.feedback.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author amifideles
 */
@Getter
@Setter
@AllArgsConstructor
public class TokenDto {
    private String accessToken;
    private String refreshToken;
}
