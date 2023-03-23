package com.devwinter.postservice.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {

    IT("IT 카테고리");

    private final String description;
}
