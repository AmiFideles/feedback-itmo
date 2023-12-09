package ru.niu.itmo.feedback.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author amifideles
 */
@Getter
@Setter
public class AuthDto {
    @NotBlank
    @Size(min = 6, max = 30)
    private String username;
    @NotBlank
    @Size(min = 6, max = 60)
    private String password;
}
