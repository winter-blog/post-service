package com.devwinter.postservice.application.service;

import com.devwinter.postservice.application.port.input.DetailPostQuery;
import com.devwinter.postservice.application.port.output.LoadMemberInfoPort;
import com.devwinter.postservice.application.port.output.LoadPostDetailPort;
import com.devwinter.postservice.common.UseCase;
import com.devwinter.postservice.domain.Post;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class DetailPostQueryService implements DetailPostQuery {

    private final LoadPostDetailPort loadPostDetailPort;
    private final LoadMemberInfoPort loadMemberInfoPort;

    @Override
    public DetailPostDto find(Long postId) {
        Post post = loadPostDetailPort.load(postId);
        LoadMemberInfoPort.MemberInfoDto memberInfoDto = loadMemberInfoPort.load(post.getMemberId().value());

        return DetailPostDto.of(post, memberInfoDto);
    }
}
