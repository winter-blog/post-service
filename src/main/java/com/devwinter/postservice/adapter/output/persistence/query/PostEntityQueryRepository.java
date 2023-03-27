package com.devwinter.postservice.adapter.output.persistence.query;

import com.devwinter.postservice.adapter.output.persistence.entity.PostEntity;

import java.util.List;
import java.util.Optional;

interface PostEntityQueryRepository {

    Optional<PostEntity> findByPostId(Long postId);
    List<PostEntity> findByPostsCursorBase(Long key, Integer size);
}
