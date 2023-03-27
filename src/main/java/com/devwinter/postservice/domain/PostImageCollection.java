package com.devwinter.postservice.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class PostImageCollection {

    private List<PostImage> postImages = new ArrayList<>();

    @Builder
    private PostImageCollection(List<PostImage> postImages) {
        this.postImages = postImages;
    }

    public String getThumbNailPath() {
        if(postImages.isEmpty()) {
            return null;
        }

        return postImages.get(0).getPath();
    }

    public boolean hasThumbNail() {
        if(postImages.isEmpty()) {
            return false;
        } else return !Objects.isNull(postImages.get(0).getPath());
    }
}
