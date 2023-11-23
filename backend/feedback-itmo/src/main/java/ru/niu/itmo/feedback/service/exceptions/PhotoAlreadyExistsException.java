package ru.niu.itmo.feedback.service.exceptions;

/**
 * @author amifideles
 */
public class PhotoAlreadyExistsException extends RuntimeException{
    public PhotoAlreadyExistsException() {
    }

    public PhotoAlreadyExistsException(String message) {
        super(message);
    }
}
