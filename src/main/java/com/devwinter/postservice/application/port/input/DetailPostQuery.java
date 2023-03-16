package com.devwinter.postservice.application.port.input;

import com.devwinter.postservice.domain.Category;
import com.devwinter.postservice.domain.Post;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalDateTime;

public interface DetailPostQuery {
    DetailPostDto find(Long postId);

    @Builder(access = AccessLevel.PRIVATE)
    record DetailPostDto(
            Long postId,
            Long memberId,
            String title,
            String contents,
            Category category,
            boolean deleted,
            LocalDateTime createdAt
    ) {

        public static DetailPostDto of(Post post) {
            return DetailPostDto.builder()
                                .postId(post.getId().value())
                                .memberId(post.getMemberId().value())
                                .title(post.getTitle())
                                .contents(post.getContents())
                                .category(post.getCategory())
                                .deleted(post.isDeleted())
                                .createdAt(post.getCreatedAt())
                                .build();
        }
    }

}
