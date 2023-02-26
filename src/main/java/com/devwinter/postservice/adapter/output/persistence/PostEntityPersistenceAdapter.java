package com.devwinter.postservice.adapter.output.persistence;

import com.devwinter.postservice.adapter.output.persistence.entity.PostEntity;
import com.devwinter.postservice.application.port.output.SavePostPort;
import com.devwinter.postservice.common.PersistenceAdapter;
import com.devwinter.postservice.domain.Post;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class PostEntityPersistenceAdapter implements SavePostPort {

    private final PostEntityRepository postEntityRepository;
    private final PostMapper postMapper;

    @Override
    public Post save(Post post) {
        PostEntity postEntity = postMapper.domainToEntity(post);
        return postMapper.entityToDomain(postEntityRepository.save(postEntity));
    }
}
