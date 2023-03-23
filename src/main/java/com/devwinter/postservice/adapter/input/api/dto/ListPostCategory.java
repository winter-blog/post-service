package com.devwinter.postservice.adapter.input.api.dto;

import com.devwinter.postservice.application.port.input.ListPostCategoryQuery.PostCategoryItemDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

public class ListPostCategory {
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Response {

        private List<PostCategoryItemResponse> categories;

        public static BaseResponse<ListPostCategory.Response> success(List<PostCategoryItemDto> items) {

            List<PostCategoryItemResponse> postCategoryItemResponses = items.stream()
                                                                            .map(PostCategoryItemResponse::new)
                                                                            .toList();

            return BaseResponse.success(new ListPostCategory.Response(postCategoryItemResponses));
        }
    }

    @Getter
    private static class PostCategoryItemResponse {
        private final String name;
        private final String description;

        public PostCategoryItemResponse(PostCategoryItemDto itemDto) {
            this.name = itemDto.name();
            this.description = itemDto.description();
        }
    }
}
