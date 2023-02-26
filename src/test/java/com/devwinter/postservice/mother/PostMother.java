package com.devwinter.postservice.mother;

import com.devwinter.postservice.domain.Category;
import com.devwinter.postservice.domain.MemberId;
import com.devwinter.postservice.domain.Post;
import com.devwinter.postservice.domain.PostId;

public class PostMother {
    public static Post.PostBuilder complete() {
        return Post.builder()
                   .id(new PostId(1L))
                   .memberId(new MemberId(1L))
                   .title("post title")
                   .contents("post contents")
                   .category(Category.IT)
                   .postImageCollection(PostImageCollectionMother.complete()
                                                                 .build())
                   .deleted(false)
                ;
    }
}
