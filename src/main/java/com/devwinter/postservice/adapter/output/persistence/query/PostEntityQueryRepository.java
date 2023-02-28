package com.devwinter.postservice.adapter.output.persistence.query;

import com.devwinter.postservice.adapter.output.persistence.entity.PostEntity;

import java.util.Optional;

interface PostEntityQueryRepository {

    Optional<PostEntity> findByPostId(Long postId);
}
