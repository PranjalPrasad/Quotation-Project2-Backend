package com.quo.quotation2.exception;

public class QuotationException extends RuntimeException {
    public QuotationException(String message) {
        super(message);
    }

    public QuotationException(String message, Throwable cause) {
        super(message, cause);
    }
}
