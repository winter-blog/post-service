package com.devwinter.postservice.adapter.output.persistence.command;

import com.devwinter.postservice.adapter.output.persistence.PostMapper;
import com.devwinter.postservice.adapter.output.persistence.entity.PostEntity;
import com.devwinter.postservice.application.port.output.DeletePostPort;
import com.devwinter.postservice.application.port.output.SavePostPort;
import com.devwinter.postservice.common.PersistenceAdapter;
import com.devwinter.postservice.common.exception.PostException;
import com.devwinter.postservice.domain.Post;
import lombok.RequiredArgsConstructor;

import static com.devwinter.postservice.common.exception.PostErrorCode.POST_NOT_FOUND;

@PersistenceAdapter
@RequiredArgsConstructor
public class PostEntityPersistenceAdapter implements SavePostPort, DeletePostPort {

    private final PostEntityCommandRepository postEntityCommandRepository;
    private final PostMapper postMapper;

    @Override
    public Post save(Post post) {
        PostEntity postEntity = postMapper.domainToEntity(post);
        return postMapper.entityToDomain(postEntityCommandRepository.save(postEntity));
    }

    @Override
    public void delete(Long postId) {
        PostEntity postEntity = postEntityCommandRepository.findById(postId)
                                                           .orElseThrow(() -> new PostException(POST_NOT_FOUND));
        postEntity.delete();
    }
}
