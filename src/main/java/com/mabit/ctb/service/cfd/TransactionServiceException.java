package com.mabit.ctb.service.cfd;

public class TransactionServiceException extends Exception {
    public TransactionServiceException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}