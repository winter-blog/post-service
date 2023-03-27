package com.devwinter.postservice.application.service;

import com.devwinter.postservice.application.port.input.DeletePostUseCase;
import com.devwinter.postservice.application.port.output.DeletePostPort;
import com.devwinter.postservice.application.port.output.LoadPostDetailPort;
import com.devwinter.postservice.common.UseCase;
import com.devwinter.postservice.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class DeletePostService implements DeletePostUseCase {
    private final LoadPostDetailPort loadPostDetailPort;
    private final DeletePostPort deletePostPort;

    @Override
    @Transactional
    public void delete(DeletePostCommand command) {
        Post post = loadPostDetailPort.load(command.postId());
        post.checkAuthority(command.memberId());
        post.delete();

        deletePostPort.delete(post.getId().value());
    }
}
