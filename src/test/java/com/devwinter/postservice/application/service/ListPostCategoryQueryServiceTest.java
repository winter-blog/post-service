package com.devwinter.postservice.application.service;

import com.devwinter.postservice.application.port.input.ListPostCategoryQuery;
import com.devwinter.postservice.domain.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ListPostCategoryQueryServiceTest {

    private ListPostCategoryQueryService service = new ListPostCategoryQueryService();
    
    
    @Test
    @DisplayName("카테고리 리스트 조회 테스트")
    void getCategoryListTest() {

        // when
        List<ListPostCategoryQuery.PostCategoryItemDto> result = service.query();

        // then
        assertThat(result.size()).isEqualTo(Category.values().length);
    }
    
}