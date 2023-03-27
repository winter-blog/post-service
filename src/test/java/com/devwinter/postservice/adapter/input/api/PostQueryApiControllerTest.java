package com.devwinter.postservice.adapter.input.api;

import com.devwinter.postservice.adapter.input.api.document.AbstractRestDocs;
import com.devwinter.postservice.adapter.input.api.dto.DetailPost;
import com.devwinter.postservice.adapter.input.api.dto.ListPost;
import com.devwinter.postservice.application.port.input.DetailPostQuery;
import com.devwinter.postservice.application.port.input.ListPostQuery;
import com.devwinter.postservice.application.port.input.ListPostQuery.ListPostQueryResultDto;
import com.devwinter.postservice.config.PostLengthProperties;
import com.devwinter.postservice.domain.Category;
import com.epages.restdocs.apispec.SimpleType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.devwinter.postservice.adapter.input.api.document.PostApiDocumentInfo.POST_DETAIL_FIND;
import static com.devwinter.postservice.adapter.input.api.document.PostApiDocumentInfo.POST_LIST_FIND;
import static com.devwinter.postservice.adapter.input.api.document.dto.FieldDescriptorDto.fieldDescriptor;
import static com.devwinter.postservice.adapter.input.api.document.dto.ParameterDescriptorDto.parameterDescriptor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostQueryApiController.class)
class PostQueryApiControllerTest extends AbstractRestDocs {

    @MockBean
    private DetailPostQuery detailPostQuery;
    @MockBean
    private ListPostQuery listPostQuery;
    @MockBean
    private PostLengthProperties postLengthProperties;
    private static String BASE_URL = "/api/v1/posts";

    @Test
    @DisplayName("게시글 상세 조회 API 테스트")
    void detailApiTest() throws Exception {
        // given
        DetailPostQuery.DetailPostDto detailPostDto = new DetailPostQuery.DetailPostDto(1L, 1L, "title",
                "contents", Category.IT, LocalDateTime.now());
        given(detailPostQuery.find(anyLong()))
                .willReturn(detailPostDto);

        // when & then
        mockMvc.perform(get(BASE_URL + "/{postId}", 1L)
                       .headers(auth())
                       .contentType(APPLICATION_JSON)
               )
               .andDo(
                       document(
                               POST_DETAIL_FIND,
                               null,
                               DetailPost.Response.class,
                               null,
                               Arrays.asList(
                                       fieldDescriptor("result.status", STRING, "결과"),
                                       fieldDescriptor("body.postId", NUMBER, "게시글 id"),
                                       fieldDescriptor("body.memberId", NUMBER, "작성자 id"),
                                       fieldDescriptor("body.title", STRING, "제목"),
                                       fieldDescriptor("body.contents", STRING, "본문"),
                                       fieldDescriptor("body.category", STRING, "카테고리"),
                                       fieldDescriptor("body.createdAt", STRING, "생성일시"),
                                       fieldDescriptor("body.editAble", BOOLEAN, "수정 가능 여부"),
                                       fieldDescriptor("body.deleteAble", BOOLEAN, "삭제 가능 여부")
                               ),
                               List.of(
                                       parameterDescriptor("postId", SimpleType.INTEGER, "게시글 id")
                               )
                       )
               )
               .andExpect(status().isOk());
    }

    @Test
    @DisplayName("게시글 목록 조회 API 테스트")
    void getListApiTest() throws Exception {
        // given
        ListPostQueryResultDto listPostQueryResultDto = new ListPostQueryResultDto(
                1L,
                List.of(
                        new ListPostQuery.PostItemDto(1L, 1L, "title", "contents",
                                LocalDateTime.now(), "nickName", "memberProfile", "thumbNail")
                )
        );

        given(listPostQuery.query(any())).willReturn(listPostQueryResultDto);

        // when & then
        mockMvc.perform(get(BASE_URL)
                       .param("size", "10")
                       .param("key", "1")
                       .headers(auth())
                       .contentType(APPLICATION_JSON)
               )
               .andDo(
                       document(
                               POST_LIST_FIND,
                               null,
                               ListPost.Response.class,
                               null,
                               Arrays.asList(
                                       fieldDescriptor("result.status", STRING, "결과"),
                                       fieldDescriptor("body.nextCursor.key", NUMBER, "다음 요청 키값"),
                                       fieldDescriptor("body.items", ARRAY, "게시글 목록"),
                                       fieldDescriptor("body.items[].postId", NUMBER, "게시글 id"),
                                       fieldDescriptor("body.items[].memberId", NUMBER, "회원 id"),
                                       fieldDescriptor("body.items[].thumbNail", STRING, "썸네일"),
                                       fieldDescriptor("body.items[].title", STRING, "제목"),
                                       fieldDescriptor("body.items[].contents", STRING, "본문"),
                                       fieldDescriptor("body.items[].createdAt", STRING, "생성일시"),
                                       fieldDescriptor("body.items[].nickName", STRING, "회원 닉네임"),
                                       fieldDescriptor("body.items[].memberProfile", STRING, "회원 프로필")
                               ),
                               null,
                               Arrays.asList(
                                       parameterDescriptor("key", SimpleType.NUMBER, "요청키 값"),
                                       parameterDescriptor("size", SimpleType.NUMBER, "요청 개수")
                               )
                       )
               )
               .andExpect(status().isOk());
    }

}