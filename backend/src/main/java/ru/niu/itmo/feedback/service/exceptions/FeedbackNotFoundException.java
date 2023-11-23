package ru.niu.itmo.feedback.service.exceptions;

/**
 * @author amifideles
 */
public class FeedbackNotFoundException extends RuntimeException{
    public FeedbackNotFoundException(String message) {
        super(message);
    }
}
