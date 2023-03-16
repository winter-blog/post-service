package com.devwinter.postservice.application.service;

import com.devwinter.postservice.application.port.input.RegisterPostUseCase;
import com.devwinter.postservice.application.port.output.SavePostPort;
import com.devwinter.postservice.domain.factory.PostFactory;
import com.devwinter.postservice.common.UseCase;
import com.devwinter.postservice.domain.*;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class RegisterPostService implements RegisterPostUseCase {

    private final SavePostPort savePostPort;

    @Override
    public Long register(Long memberId, RegisterPostCommand command) {
        // 게시글 도메인 생성
        Post post = PostFactory.create(memberId, command.title(), command.contents(), command.category(), command.images());
        return savePostPort.save(post).getId().value();
    }
}
