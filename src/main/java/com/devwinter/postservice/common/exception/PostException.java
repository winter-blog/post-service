package com.devwinter.postservice.common.exception;

import lombok.Getter;

@Getter
public class PostException extends RuntimeException {
    private final PostErrorCode errorCode;
    public PostException(PostErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
