package com.devwinter.postservice.adapter.output.persistence;

import com.devwinter.postservice.adapter.output.persistence.entity.PostEntity;
import com.devwinter.postservice.common.Mapper;
import com.devwinter.postservice.domain.MemberId;
import com.devwinter.postservice.domain.Post;
import com.devwinter.postservice.domain.PostId;
import lombok.RequiredArgsConstructor;

@Mapper
@RequiredArgsConstructor
class PostMapper {

    private final PostImageMapper postImageMapper;

    PostEntity domainToEntity(Post post) {
        return PostEntity.builder()
                         .id((post.getId() != null) ? post.getId().value() : null)
                         .memberId(post.getMemberId().value())
                         .title(post.getTitle())
                         .contents(post.getContents())
                         .category(post.getCategory())
                         .postImageCollection(postImageMapper.domainToEntity(post.getPostImageCollection()))
                         .deleted(post.isDeleted())
                         .build();
    }

    Post entityToDomain(PostEntity postEntity) {
        return Post.builder()
                   .id(new PostId(postEntity.getId()))
                   .memberId(new MemberId(postEntity.getMemberId()))
                   .title(postEntity.getTitle())
                   .contents(postEntity.getContents())
                   .postImageCollection(postImageMapper.entityToDomain(postEntity.getPostImageCollection()))
                   .category(postEntity.getCategory())
                   .deleted(postEntity.isDeleted())
                   .createdAt(postEntity.getCreatedAt())
                   .modifiedAt(postEntity.getModifiedAt())
                   .deletedAt(postEntity.getDeletedAt())
                   .build();
    }
}
