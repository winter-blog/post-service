package com.devwinter.postservice.application.port.output;

import com.devwinter.postservice.domain.Post;

public interface SavePostPort {
    Post save(Post post);
}
