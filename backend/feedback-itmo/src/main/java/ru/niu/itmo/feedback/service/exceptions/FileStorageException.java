package ru.niu.itmo.feedback.service.exceptions;

import java.io.IOException;

/**
 * @author amifideles
 */
public class FileStorageException extends RuntimeException {
    public FileStorageException(String failedToCreateUploadDirectory, IOException e) {
    }
}
