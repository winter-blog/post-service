package com.devwinter.postservice.application.port.input;

import java.time.LocalDateTime;
import java.util.List;
public interface ListPostQuery {

    ListPostQueryResultDto query(CursorBaseCommand command);

    record CursorBaseCommand(Long key, Integer size) {

    }

    record ListPostQueryResultDto(Long key, List<PostItemDto> items) {

    }

    record PostItemDto(Long postId, Long memberId, String title, String contents, LocalDateTime createdAt, String nickName, String memberProfile, String thumbNail) {

    }
}
