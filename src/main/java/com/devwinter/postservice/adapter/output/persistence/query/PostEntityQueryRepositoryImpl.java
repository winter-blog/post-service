package com.devwinter.postservice.adapter.output.persistence.query;

import com.devwinter.postservice.adapter.output.persistence.entity.PostEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.devwinter.postservice.adapter.output.persistence.entity.QPostEntity.postEntity;

@Repository
@RequiredArgsConstructor
class PostEntityQueryRepositoryImpl implements PostEntityQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<PostEntity> findByPostId(Long postId) {

        return Optional.ofNullable(queryFactory
                .select(postEntity)
                .from(postEntity)
                .where(postEntity.id.eq(postId),
                        postEntity.deleted.isFalse())
                .fetchOne());
    }
}
