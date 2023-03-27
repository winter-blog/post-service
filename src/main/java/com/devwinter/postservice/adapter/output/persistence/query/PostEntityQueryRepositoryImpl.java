package com.devwinter.postservice.adapter.output.persistence.query;

import com.devwinter.postservice.adapter.output.persistence.entity.PostEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.devwinter.postservice.adapter.output.persistence.entity.QPostEntity.postEntity;
import static com.devwinter.postservice.adapter.output.persistence.entity.QPostImageValueObject.postImageValueObject;

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
    @Override
    public List<PostEntity> findByPostsCursorBase(Long key, Integer size) {
        List<Long> postEntityIds = queryFactory
                .select(postEntity.id)
                .from(postEntity)
                .where(
                        isIdLessThan(key),
                        isMemberActive()
                )
                .orderBy(postEntity.id.desc())
                .limit(size)
                .fetch();

        return queryFactory
                .select(postEntity)
                .from(postEntity)
                .leftJoin(postEntity.postImageCollection.postImages, postImageValueObject)
                .where(
                        postEntity.id.in(postEntityIds),
                        postImageValueObject.orderNumber.eq(1)
                )
                .orderBy(postEntity.id.desc())
                .fetch();
    }

    private BooleanExpression isIdLessThan(Long key) {
        if (key != null) {
            return postEntity.id.lt(key);
        }
        return null;
    }

    private BooleanExpression isMemberActive() {
        return postEntity.memberActive.eq(true);
    }
}
