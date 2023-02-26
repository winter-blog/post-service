package com.devwinter.postservice.mother;

import com.devwinter.postservice.domain.PostImage;
import com.devwinter.postservice.domain.PostImageCollection;

import java.util.List;

public class PostImageCollectionMother {
    public static PostImageCollection.PostImageCollectionBuilder complete() {
        return PostImageCollection.builder()
                                  .postImages(List.of(
                                                  PostImage.builder().path("image1.png").build(),
                                                  PostImage.builder().path("image2.png").build(),
                                                  PostImage.builder().path("image3.png").build())
                                  )
                ;
    }
}
