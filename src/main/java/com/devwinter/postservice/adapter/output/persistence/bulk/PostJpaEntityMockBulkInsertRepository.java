package com.devwinter.postservice.adapter.output.persistence.bulk;

import com.devwinter.postservice.adapter.output.persistence.entity.PostEntity;

import java.util.List;

public interface PostJpaEntityMockBulkInsertRepository {
    void bulkInsert(int startIndex, List<PostEntity> posts);
    int getMaxPostId();
}
