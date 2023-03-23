package com.devwinter.postservice.fixture;

import com.devwinter.postservice.application.port.input.ListPostCategoryQuery;
import com.devwinter.postservice.domain.Category;

import java.util.List;
import java.util.stream.Stream;

public class PostCategoryFixture {
    public static List<ListPostCategoryQuery.PostCategoryItemDto> complete() {
        return Stream.of(Category.values())
                     .map(ListPostCategoryQuery.PostCategoryItemDto::new)
                     .toList();
    }
}
