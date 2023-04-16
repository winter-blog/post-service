package com.devwinter.postservice.domain.factory;

import com.devwinter.postservice.domain.*;

import java.util.List;
import java.util.stream.Collectors;

public class PostFactory {
    public static Post create(Long memberId, String title, String contents, String category, List<String> images) {
        return Post.builder()
                   .title(title)
                   .contents(contents)
                   .memberId(new MemberId(memberId))
                   .category(Category.valueOf(category))
                   .postImageCollection(PostImageCollection.builder()
                                                           .postImages(images
                                                                   .stream()
                                                                   .map(i -> PostImage.builder()
                                                                                      .path(i)
                                                                                      .orderNumber(images.indexOf(i) + 1)
                                                                                      .build())
                                                                   .collect(Collectors.toList()))
                                                           .build())
                   .deleted(false)
                   .build();
    }
}
