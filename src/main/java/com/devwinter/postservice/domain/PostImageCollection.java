package com.devwinter.postservice.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PostImageCollection {

    private List<PostImage> postImages = new ArrayList<>();

    @Builder
    private PostImageCollection(List<PostImage> postImages) {
        this.postImages = postImages;
    }
}
