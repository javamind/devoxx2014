package com.ninjamind.conference.service;

/**
 * Created by ehret_g on 16/04/14.
 */
public class NoConfException extends Exception {
    public NoConfException() {
        super();
    }

    public NoConfException(String message) {
        super(message);
    }

    public NoConfException(String message, Throwable cause) {
        super(message, cause);
    }
}
