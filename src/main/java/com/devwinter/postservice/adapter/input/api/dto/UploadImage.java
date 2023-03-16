package com.devwinter.postservice.adapter.input.api.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class UploadImage {
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Response {
        private String path;
        private static String DEFAULT_MESSAGE = "이미지를 업로드하였습니다.";

        public static BaseResponse<UploadImage.Response> success(String path) {
            return BaseResponse.success(DEFAULT_MESSAGE, new Response(path));
        }
    }
}
