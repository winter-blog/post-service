package com.devwinter.postservice.adapter.input.api.dto;


import com.devwinter.postservice.application.port.input.RegisterPostUseCase;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class CreatePost {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private String title;
        private String contents;
        private String category;
        private List<String> images;

        public RegisterPostUseCase.RegisterPostCommand toCommand() {
            return RegisterPostUseCase.RegisterPostCommand.builder()
                                                          .title(title)
                                                          .contents(contents)
                                                          .category(category)
                                                          .images(images)
                                                          .build();
        }
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
