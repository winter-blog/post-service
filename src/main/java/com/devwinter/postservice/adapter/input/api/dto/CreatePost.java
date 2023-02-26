package com.devwinter.postservice.adapter.input.api.dto;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

public class CreatePost {
    @Getter
    public static class Request {
        private String title;
        private String contents;
        private String category;
        private List<String> images;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Response {
        private Long postId;
        private static String DEFAULT_MESSAGE = "게시글을 작성하였습니다.";

        public static BaseResponse<CreatePost.Response> success(Long postId) {
            return BaseResponse.success(DEFAULT_MESSAGE, new Response(postId));
        }
    }
}
