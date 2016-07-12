package com.mrsnottypants.nihpubmed;

/**
 * For wrapping implementation-specific exceptions
 *
 * Created by Eric on 7/11/2016.
 */
public class PubMedResultException extends RuntimeException {

    /**
     * Construct an exception
     * @param message human-readable error message
     * @param cause exception that led to this exception
     */
    public PubMedResultException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Construct an exception
     * @param message human-readable error message
     */
    public PubMedResultException(String message) {
        super(message);
    }
}