package com.devwinter.postservice.application.port.output;

import com.devwinter.postservice.domain.Post;

public interface LoadPostPort {
    Post load(Long postId);
}
