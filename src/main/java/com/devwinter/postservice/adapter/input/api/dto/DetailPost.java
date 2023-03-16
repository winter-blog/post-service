package com.devwinter.postservice.adapter.input.api.dto;

import com.devwinter.postservice.application.port.input.DetailPostQuery.DetailPostDto;
import com.devwinter.postservice.common.StringConverter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

public class DetailPost {

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class AnonymousResponse {
        private Long postId;
        private Long memberId;
        private String title;
        private String contents;
        private String category;
        private String createdAt;

        public static BaseResponse<DetailPost.AnonymousResponse> success(DetailPostDto detailPostDto) {
            return BaseResponse.success(create(detailPostDto));
        }

        protected static DetailPost.AnonymousResponse create(DetailPostDto detailPostDto) {
            return new DetailPost.AnonymousResponse(
                    detailPostDto.postId(),
                    detailPostDto.memberId(),
                    detailPostDto.title(),
                    detailPostDto.contents(),
                    detailPostDto.category()
                                 .getDescription(),
                    StringConverter.localDateTimeToLocalDateTimeString(detailPostDto.createdAt()));
        }
    }

    @Getter
    public static class Response extends AnonymousResponse {
        private boolean editAble = false;
        private boolean deleteAble = false;

        private Response(Long memberId, AnonymousResponse anonymousResponse) {
            super(
                    anonymousResponse.postId,
                    anonymousResponse.memberId,
                    anonymousResponse.title,
                    anonymousResponse.contents,
                    anonymousResponse.category,
                    anonymousResponse.createdAt
            );

            if(Objects.equals(memberId, anonymousResponse.memberId)) {
                this.editAble = true;
                this.deleteAble = true;
            }
        }


        public static BaseResponse<DetailPost.Response> success(Long memberId, DetailPostDto detailPostDto) {
            return BaseResponse.success(new Response(memberId, create(detailPostDto)));
        }
    }
}
