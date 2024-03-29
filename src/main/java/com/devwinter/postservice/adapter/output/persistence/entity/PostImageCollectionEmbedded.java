package com.devwinter.postservice.adapter.output.persistence.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostImageCollectionEmbedded {
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "post_image",
            joinColumns = {
                    @JoinColumn(name = "post_id")
            }
    )
    private List<PostImageValueObject> postImages = new ArrayList<>();

    @Builder
    private PostImageCollectionEmbedded(List<PostImageValueObject> postImages) {
        this.postImages = postImages;
    }
}
