package com.devwinter.postservice.domain.exception;

public class PostException extends RuntimeException {
    private final PostErrorCode errorCode;
    public PostException(PostErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
