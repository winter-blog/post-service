package com.devwinter.postservice.domain;

import com.devwinter.postservice.domain.exception.PostException;
import lombok.Builder;

import java.time.LocalDateTime;

import static com.devwinter.postservice.domain.exception.PostErrorCode.POST_ALREADY_DELETED;

public class Post extends AbstractHistory {

    private final PostId id;
    private final MemberId memberId;
    private String title;
    private String contents;
    private PostImageCollection postImageCollection;
    private Category category;
    private boolean deleted;

    @Builder
    private Post(LocalDateTime createdAt, LocalDateTime modifiedAt, LocalDateTime deletedAt, PostId id, MemberId memberId, String title, String contents, PostImageCollection postImageCollection, Category category, boolean deleted) {
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

}
