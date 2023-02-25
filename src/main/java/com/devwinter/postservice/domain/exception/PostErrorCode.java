package com.devwinter.postservice.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PostErrorCode {

    POST_ALREADY_DELETED(HttpStatus.BAD_REQUEST, "게시글이 이미 삭제 되었습니다.")
    ;
    private final HttpStatus httpStatus;
    private final String message;
}
