package com.ninjamind.conference.exception;

/**
 * Created by ehret_g on 16/04/14.
 */
public class FunctionnalException extends RuntimeException {
    public FunctionnalException(String message) {
        super(message);
    }

    public FunctionnalException(String message, Throwable cause) {
        super(message, cause);
    }
}
