package com.devwinter.postservice.application.port.input;

import com.devwinter.postservice.application.port.output.LoadMemberInfoPort;
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
            LocalDateTime createdAt,
            String nickName,
            String profile
    ) {

        public static DetailPostDto of(Post post, LoadMemberInfoPort.MemberInfoDto memberInfoDto) {
            return DetailPostDto.builder()
                                .postId(post.getId().value())
                                .memberId(post.getMemberId().value())
                                .title(post.getTitle())
                                .contents(post.getContents())
                                .category(post.getCategory())
                                .createdAt(post.getCreatedAt())
                                .nickName(memberInfoDto.nickName())
                                .profile(memberInfoDto.profile())
                                .build();
        }
    }

}
