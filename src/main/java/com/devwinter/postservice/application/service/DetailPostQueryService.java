package com.devwinter.postservice.application.service;

import com.devwinter.postservice.application.port.input.DetailPostQuery;
import com.devwinter.postservice.application.port.output.LoadPostDetailPort;
import com.devwinter.postservice.common.UseCase;
import com.devwinter.postservice.domain.Post;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class DetailPostQueryService implements DetailPostQuery {

    private final LoadPostDetailPort loadPostDetailPort;

    @Override
    public DetailPostDto find(Long postId) {
        Post post = loadPostDetailPort.load(postId);
        return DetailPostDto.of(post);
    }
}
