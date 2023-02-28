package com.devwinter.postservice.adapter.output.persistence.query;

import com.devwinter.postservice.adapter.output.persistence.PostMapper;
import com.devwinter.postservice.adapter.output.persistence.entity.PostEntity;
import com.devwinter.postservice.application.port.output.LoadPostPort;
import com.devwinter.postservice.common.PersistenceAdapter;
import com.devwinter.postservice.common.exception.PostException;
import com.devwinter.postservice.domain.Post;
import lombok.RequiredArgsConstructor;

import static com.devwinter.postservice.common.exception.PostErrorCode.POST_NOT_FOUND;

@PersistenceAdapter
@RequiredArgsConstructor
public class PostEntityQueryAdapter implements LoadPostPort {

    private final PostEntityQueryRepository postEntityQueryRepository;
    private final PostMapper postMapper;

    @Override
    public Post load(Long postId) {
        PostEntity postEntity = postEntityQueryRepository.findByPostId(postId)
                                                         .orElseThrow(() -> new PostException(POST_NOT_FOUND));
        return postMapper.entityToDomain(postEntity);
    }
}
