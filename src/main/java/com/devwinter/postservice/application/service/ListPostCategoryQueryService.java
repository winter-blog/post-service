package com.devwinter.postservice.application.service;

import com.devwinter.postservice.application.port.input.ListPostCategoryQuery;
import com.devwinter.postservice.common.QueryService;
import com.devwinter.postservice.domain.Category;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Stream;

@QueryService
@RequiredArgsConstructor
public class ListPostCategoryQueryService implements ListPostCategoryQuery {

    @Override
    public List<PostCategoryItemDto> query() {
        return Stream.of(Category.values())
                     .map(PostCategoryItemDto::new)
                     .toList();
    }
}
