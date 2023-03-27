package com.devwinter.postservice.adapter.output.persistence.entity;

import com.devwinter.postservice.common.exception.PostErrorCode;
import com.devwinter.postservice.common.exception.PostException;
import com.devwinter.postservice.domain.Category;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.devwinter.postservice.common.exception.PostErrorCode.POST_ALREADY_DELETED;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "post")
public class PostEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;
    @Column(nullable = false)
    private Long memberId;
    private boolean memberActive;
    @Column(nullable = false)
    private String title;
    @Lob
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String contents;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;
    @Embedded
    private PostImageCollectionEmbedded postImageCollection;
    private boolean deleted;

    @Builder
    private PostEntity(Long id, Long memberId, String title, String contents, Category category, PostImageCollectionEmbedded postImageCollection, boolean deleted) {
        this.id = id;
        this.memberId = memberId;
        this.title = title;
        this.contents = contents;
        this.category = category;
        this.postImageCollection = postImageCollection;
        this.deleted = deleted;
        this.memberActive = true;
    }

    public void delete() {
        if(this.deleted) {
            throw new PostException(POST_ALREADY_DELETED);
        }

        this.deleted = true;
        setDeletedAt(LocalDateTime.now());
    }
}
