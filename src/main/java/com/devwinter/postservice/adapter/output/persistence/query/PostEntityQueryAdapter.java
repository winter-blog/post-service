package com.devwinter.postservice.adapter.output.persistence.query;

import com.devwinter.postservice.adapter.output.persistence.PostMapper;
import com.devwinter.postservice.adapter.output.persistence.entity.PostEntity;
import com.devwinter.postservice.application.port.output.LoadPostPort;
import com.devwinter.postservice.common.PersistenceAdapter;
import com.devwinter.postservice.common.exception.PostException;
import com.devwinter.postservice.domain.MemberId;
import com.devwinter.postservice.domain.Post;
import com.devwinter.postservice.domain.PostId;
import lombok.RequiredArgsConstructor;

import static com.devwinter.postservice.common.exception.PostErrorCode.POST_NOT_FOUND;

@PersistenceAdapter
@RequiredArgsConstructor
public class PostEntityQueryAdapter implements LoadPostPort {

    private final PostEntityQueryRepository postEntityQueryRepository;

    @Override
    public Post load(Long postId) {
        PostEntity postEntity = postEntityQueryRepository.findByPostId(postId)
                                                         .orElseThrow(() -> new PostException(POST_NOT_FOUND));
        return entityToDomain(postEntity);
    }

    private Post entityToDomain(PostEntity postEntity) {
        return Post.builder()
                   .id(new PostId(postEntity.getId()))
                   .memberId(new MemberId(postEntity.getMemberId()))
                   .title(postEntity.getTitle())
                   .contents(postEntity.getContents())
                   .category(postEntity.getCategory())
                   .deleted(postEntity.isDeleted())
                   .createdAt(postEntity.getCreatedAt())
                   .modifiedAt(postEntity.getModifiedAt())
                   .deletedAt(postEntity.getDeletedAt())
                   .build();
    }
}
