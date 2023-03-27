package com.devwinter.postservice.adapter.output.persistence.query;

import com.devwinter.postservice.adapter.output.persistence.entity.PostEntity;
import com.devwinter.postservice.application.port.output.LoadPostDetailPort;
import com.devwinter.postservice.application.port.output.LoadPostListPort;
import com.devwinter.postservice.common.PersistenceAdapter;
import com.devwinter.postservice.common.exception.PostException;
import com.devwinter.postservice.domain.*;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static com.devwinter.postservice.common.exception.PostErrorCode.POST_NOT_FOUND;

@PersistenceAdapter
@RequiredArgsConstructor
public class PostEntityQueryAdapter implements LoadPostDetailPort, LoadPostListPort {

    private final PostEntityQueryRepository postEntityQueryRepository;

    @Override
    public Post load(Long postId) {
        PostEntity postEntity = postEntityQueryRepository.findByPostId(postId)
                                                         .orElseThrow(() -> new PostException(POST_NOT_FOUND));
        return entityToDomain(postEntity);
    }

    @Override
    public List<Post> load(Long key, Integer size) {
        List<PostEntity> postEntities = postEntityQueryRepository.findByPostsCursorBase(key, size);

        return postEntities.stream()
                           .map(this::entityToDomain)
                           .collect(Collectors.toList());
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
                   .postImageCollection(PostImageCollection.builder()
                                                           .postImages(postEntity.getPostImageCollection()
                                                                                 .getPostImages()
                                                                                 .stream()
                                                                                 .map(
                                                                                         p -> PostImage.builder()
                                                                                                       .path(p.getPath())
                                                                                                       .orderNumber(p.getOrderNumber())
                                                                                                       .build()
                                                                                 )
                                                                                 .toList())
                                                           .build())

                   .build();
    }
}
