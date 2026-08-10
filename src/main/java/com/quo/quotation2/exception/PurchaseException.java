package com.quo.quotation2.exception;


public class PurchaseException extends RuntimeException {

    public PurchaseException(String message) {
        super(message);
    }

    public PurchaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
