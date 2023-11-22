package ru.niu.itmo.feedback.service.exceptions;

public class UserAlreadyExist extends RuntimeException {
    public UserAlreadyExist(String message) {
        super(message);
    }
}
