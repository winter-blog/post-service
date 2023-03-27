package com.devwinter.postservice.adapter.input.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class RemoveImage {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private String url;
    }

    public static class Response {
        public static BaseResponse<RemoveImage.Response> success() {
            return BaseResponse.success();
        }
    }
}
