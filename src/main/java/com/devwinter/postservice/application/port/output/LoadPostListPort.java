package com.devwinter.postservice.application.port.output;

import com.devwinter.postservice.domain.Post;

import java.util.List;

public interface LoadPostListPort {
    List<Post> load(Long key, Integer size);
}
