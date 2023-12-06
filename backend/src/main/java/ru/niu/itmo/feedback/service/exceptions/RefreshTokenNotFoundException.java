package ru.niu.itmo.feedback.service.exceptions;

/**
 * @author amifideles
 */
public class RefreshTokenNotFoundException extends RuntimeException {
    public RefreshTokenNotFoundException(String refreshTokenDoesNotExist) {
    }
}
