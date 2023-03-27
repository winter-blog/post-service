package com.devwinter.postservice.adapter.input.api.dto;

import com.devwinter.postservice.application.port.input.ListPostQuery;
import com.devwinter.postservice.common.StringConverter;
import lombok.*;

import java.util.List;

public class ListPost {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private Long key;
        private Integer size;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Response {
        private CursorResponse nextCursor;
        private List<ListPostItem> items;

        public static BaseResponse<ListPost.Response> success(CursorResponse cursorResponse, List<ListPostItem> items) {
            return BaseResponse.success(new ListPost.Response(cursorResponse, items));
        }
    }

    @Getter
    @AllArgsConstructor
    public static class CursorResponse {
        private Long key;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ListPostItem {
        private Long postId;
        private Long memberId;
        private String thumbNail;
        private String title;
        private String contents;
        private String createdAt;
        private String nickName;
        private String memberProfile;

        public static ListPostItem success(ListPostQuery.PostItemDto postItemDto, String title, String contents) {
            return new ListPostItem(
                    postItemDto.postId(),
                    postItemDto.memberId(),
                    (postItemDto.thumbNail() != null) ? postItemDto.thumbNail() : "",
                    title,
                    contents,
                    StringConverter.localDateTimeToLocalDateString(postItemDto.createdAt()),
                    postItemDto.nickName(),
                    postItemDto.memberProfile()
            );
        }
    }
}