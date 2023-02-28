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
    POST_CATEGORY_REQUIRED(HttpStatus.BAD_REQUEST, "카테고리는 필수 입력 값입니다."),
    POST_IMAGE_UPLOAD_EXTENSION_NOT_VALID(HttpStatus.BAD_REQUEST, "이미지 확장자가 잘못되었습니다."),
    POST_IMAGE_UPLOAD_NOT_SUPPORT(HttpStatus.BAD_REQUEST, "지원하지 않는 확장자입니다."),
    POST_IMAGE_NAME_EMPTY(HttpStatus.BAD_REQUEST, "업로드할 이미지의 이름이 업습니다."),
    MEMBER_ID_REQUIRED(HttpStatus.BAD_REQUEST, "회원 id는 필수 입니다."),
    POST_ID_REQUIRED(HttpStatus.BAD_REQUEST, "게시글 id는 필수 입니다."),
    POST_NOT_AUTHORITY(HttpStatus.UNAUTHORIZED, "권한이 없습니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글이 존재하지 않습니다.")
    ;
    private final HttpStatus httpStatus;
    private final String message;
}
