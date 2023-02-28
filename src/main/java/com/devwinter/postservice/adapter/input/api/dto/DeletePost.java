package com.devwinter.postservice.adapter.input.api.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class DeletePost {
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Response {
        private Long postId;
        private static String DEFAULT_MESSAGE = "게시글을 삭제하였습니다.";

        public static BaseResponse<DeletePost.Response> success(Long postId) {
            return BaseResponse.success(DEFAULT_MESSAGE, new DeletePost.Response(postId));
        }
    }
}
