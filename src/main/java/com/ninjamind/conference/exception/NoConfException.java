package com.ninjamind.conference.exception;

/**
 * Created by ehret_g on 17/04/14.
 */
public class NoConfException extends Exception {
    public NoConfException() {
    }

    public NoConfException(String message) {
        super(message);
    }

    public NoConfException(String message, Throwable cause) {
        super(message, cause);
    }
}
