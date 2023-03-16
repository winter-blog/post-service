package com.devwinter.postservice.application.service;

import com.devwinter.postservice.application.port.input.DetailPostQuery;
import com.devwinter.postservice.application.port.output.LoadPostPort;
import com.devwinter.postservice.common.UseCase;
import com.devwinter.postservice.domain.Post;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class DetailPostQueryService implements DetailPostQuery {

    private final LoadPostPort loadPostPort;

    @Override
    public DetailPostDto find(Long postId) {
        Post post = loadPostPort.load(postId);
        return DetailPostDto.of(post);
    }
}
