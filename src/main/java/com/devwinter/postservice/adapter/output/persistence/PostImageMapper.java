package com.devwinter.postservice.adapter.output.persistence;

import com.devwinter.postservice.adapter.output.persistence.entity.PostImageCollectionEmbedded;
import com.devwinter.postservice.adapter.output.persistence.entity.PostImageValueObject;
import com.devwinter.postservice.common.Mapper;
import com.devwinter.postservice.domain.PostImage;
import com.devwinter.postservice.domain.PostImageCollection;

import java.util.stream.Collectors;

@Mapper
class PostImageMapper {

    PostImageCollectionEmbedded domainToEntity(PostImageCollection postImageCollection) {
        return PostImageCollectionEmbedded.builder()
                                          .postImages(postImageCollection.getPostImages()
                                                                         .stream()
                                                                         .map(i -> PostImageValueObject.builder()
                                                                                                       .path(i.getPath())
                                                                                                       .build())
                                                                         .collect(Collectors.toList()))
                                          .build();
    }

    PostImageCollection entityToDomain(PostImageCollectionEmbedded postImageCollectionEmbedded) {
        return PostImageCollection.builder()
                                  .postImages(postImageCollectionEmbedded.getPostImages()
                                                                         .stream()
                                                                         .map(i -> PostImage.builder()
                                                                                            .path(i.getPath())
                                                                                            .build())
                                                                         .collect(Collectors.toList()))
                                  .build();
    }
}
