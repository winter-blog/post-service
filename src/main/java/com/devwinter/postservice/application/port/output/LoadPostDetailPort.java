package com.devwinter.postservice.application.port.output;

import com.devwinter.postservice.domain.Post;

public interface LoadPostDetailPort {
    Post load(Long postId);
}
