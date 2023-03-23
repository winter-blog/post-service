package com.devwinter.postservice.application.port.input;

import com.devwinter.postservice.domain.Category;

import java.util.List;

public interface ListPostCategoryQuery {


    List<PostCategoryItemDto> query();

    record PostCategoryItemDto(String name, String description) {

        public PostCategoryItemDto(Category category) {
            this(category.name(), category.getDescription());
        }
    }
}
