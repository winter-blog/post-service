package com.devwinter.postservice.adapter.input.api;

import com.devwinter.postservice.adapter.input.api.document.AbstractRestDocs;
import com.devwinter.postservice.adapter.input.api.dto.DetailPost;
import com.devwinter.postservice.adapter.input.api.dto.ListPostCategory;
import com.devwinter.postservice.application.port.input.ListPostCategoryQuery;
import com.devwinter.postservice.application.port.input.ListPostCategoryQuery.PostCategoryItemDto;
import com.devwinter.postservice.domain.Category;
import com.devwinter.postservice.fixture.PostCategoryFixture;
import com.epages.restdocs.apispec.SimpleType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static com.devwinter.postservice.adapter.input.api.document.PostApiDocumentInfo.POST_CATEGORY_FIND;
import static com.devwinter.postservice.adapter.input.api.document.PostApiDocumentInfo.POST_DETAIL_FIND;
import static com.devwinter.postservice.adapter.input.api.document.dto.FieldDescriptorDto.fieldDescriptor;
import static com.devwinter.postservice.adapter.input.api.document.dto.ParameterDescriptorDto.parameterDescriptor;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostCategoryApiController.class)
class PostCategoryApiControllerTest extends AbstractRestDocs {
    @MockBean
    private ListPostCategoryQuery listPostCategoryQuery;
    private static String BASE_URL = "/api/v1/posts";


    @Test
    @DisplayName("카테고리 목록 조회 API 테스트")
    void detailApiTest() throws Exception {
        // given
        List<PostCategoryItemDto> result = PostCategoryFixture.complete();

        given(listPostCategoryQuery.query())
                .willReturn(result);

        // when & then
        mockMvc.perform(get(BASE_URL + "/categories")
                       .headers(auth())
                       .contentType(APPLICATION_JSON)
               )
               .andDo(
                       document(
                               POST_CATEGORY_FIND,
                               null,
                               ListPostCategory.Response.class,
                               null,
                               Arrays.asList(
                                       fieldDescriptor("result.status", STRING, "결과"),
                                       fieldDescriptor("body.categories", ARRAY, "카테고리 목록"),
                                       fieldDescriptor("body.categories[].name", STRING, "카테고리명"),
                                       fieldDescriptor("body.categories[].description", STRING, "설명")
                               ),
                               null
                       )
               )
               .andExpect(status().isOk());
    }
}
