package com.devwinter.postservice.domain;

import com.devwinter.postservice.common.exception.PostException;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.devwinter.postservice.common.exception.PostErrorCode.POST_ALREADY_DELETED;
import static com.devwinter.postservice.common.exception.PostErrorCode.POST_NOT_AUTHORITY;


@Getter
public class Post extends AbstractHistory {

    private final PostId id;
    private final MemberId memberId;
    private String title;
    private String contents;
    private PostImageCollection postImageCollection;
    private Category category;
    private boolean deleted;

    @Builder
    private Post(PostId id, MemberId memberId, String title, String contents, PostImageCollection postImageCollection, Category category, boolean deleted,
                 LocalDateTime createdAt, LocalDateTime modifiedAt, LocalDateTime deletedAt) {
        super(createdAt, modifiedAt, deletedAt);
        this.id = id;
        this.memberId = memberId;
        this.title = title;
        this.contents = contents;
        this.postImageCollection = postImageCollection;
        this.category = category;
        this.deleted = deleted;
    }

    public void edit(String title, String contents, Category category) {
        this.title = title;
        this.contents = contents;
        this.category = category;
    }

    public void delete() {
        if (deleted) {
            throw new PostException(POST_ALREADY_DELETED);
        }

        deleted = true;
        setDeletedAt(LocalDateTime.now());
    }

    public void checkAuthority(Long memberId) {
        if (!Objects.equals(this.memberId.value(), memberId)) {
            throw new PostException(POST_NOT_AUTHORITY);
        }
    }
}
