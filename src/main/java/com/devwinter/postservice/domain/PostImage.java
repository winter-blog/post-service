package com.devwinter.postservice.domain;

import lombok.Builder;
import lombok.Getter;


@Getter
public class PostImage {
    private String path;

    @Builder
    private PostImage(String path) {
        this.path = path;
    }
}
