package com.devwinter.postservice.adapter.output.persistence.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostImageValueObject {
    private String path;
    private int orderNumber;

    @Builder
    private PostImageValueObject(String path, int orderNumber) {
        this.path = path;
        this.orderNumber = orderNumber;
    }
}
