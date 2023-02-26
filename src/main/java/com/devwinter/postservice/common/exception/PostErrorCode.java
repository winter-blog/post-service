package com.devwinter.postservice.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PostErrorCode {

    POST_ALREADY_DELETED(HttpStatus.BAD_REQUEST, "게시글이 이미 삭제 되었습니다."),
    MEMBER_NOT_VALID(HttpStatus.BAD_REQUEST, "잘못된 회원 정보입니다."),
    POST_TITLE_REQUIRED(HttpStatus.BAD_REQUEST, "제목은 필수 입력 값입니다."),
    POST_CONTENTS_REQUIRED(HttpStatus.BAD_REQUEST, "본문은 필수 입력 값입니다."),
    POST_CATEGORY_REQUIRED(HttpStatus.BAD_REQUEST, "카테고리는 필수 입력 값입니다.")
    ;
    private final HttpStatus httpStatus;
    private final String message;
}
