package com.devwinter.postservice.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Setter(AccessLevel.PROTECTED)
public abstract class AbstractHistory {

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime deletedAt;
}
