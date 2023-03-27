package com.devwinter.postservice.domain;

import lombok.Builder;
import lombok.Getter;


@Getter
public class PostImage {
    private final String path;
    private final Integer orderNumber;

    @Builder
    private PostImage(String path, Integer orderNumber) {
        this.path = path;
        this.orderNumber = orderNumber;
    }
}
