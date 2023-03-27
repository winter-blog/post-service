package com.devwinter.postservice.adapter.input.api;

import com.devwinter.postservice.adapter.input.api.document.AbstractRestDocs;
import com.devwinter.postservice.adapter.input.api.dto.CreatePost;
import com.devwinter.postservice.adapter.input.api.dto.DeletePost;
import com.devwinter.postservice.adapter.input.api.dto.DetailPost;
import com.devwinter.postservice.application.port.input.DeletePostUseCase;
import com.devwinter.postservice.application.port.input.DetailPostQuery;
import com.devwinter.postservice.application.port.input.ListPostQuery;
import com.devwinter.postservice.application.port.input.RegisterPostUseCase;
import com.devwinter.postservice.domain.Category;
import com.epages.restdocs.apispec.SimpleType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.devwinter.postservice.adapter.input.api.document.PostApiDocumentInfo.*;
import static com.devwinter.postservice.adapter.input.api.document.dto.FieldDescriptorDto.fieldDescriptor;
import static com.devwinter.postservice.adapter.input.api.document.dto.ParameterDescriptorDto.parameterDescriptor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostCommandApiController.class)
class PostCommandApiControllerTest extends AbstractRestDocs {
    @MockBean
    private RegisterPostUseCase registerPostUseCase;
    @MockBean
    private DeletePostUseCase deletePostUseCase;
    private static String BASE_URL = "/api/v1/posts";

    @Test
    @DisplayName("게시글 등록 API 테스트")
    void registerApiTest() throws Exception {
        // given
        CreatePost.Request request = new CreatePost.Request("title", "contents", "IT", Arrays.asList("1.png", "2.png"));

        given(registerPostUseCase.register(anyLong(), any()))
                .willReturn(1L);

        // when & then
        mockMvc.perform(post(BASE_URL)
                       .headers(auth())
                       .contentType(APPLICATION_JSON)
                       .content(requestToJson(request))
               )
               .andDo(
                       document(
                               POST_REGISTER,
                               CreatePost.Request.class,
                               CreatePost.Response.class,
                               Arrays.asList(
                                       fieldDescriptor("title", STRING, "제목"),
                                       fieldDescriptor("contents", STRING, "본문"),
                                       fieldDescriptor("category", STRING, "카테고리"),
                                       fieldDescriptor("images", ARRAY, "업로드할 이미지 목록")

                               ),
                               Arrays.asList(
                                       fieldDescriptor("result.status", STRING, "결과"),
                                       fieldDescriptor("result.message", STRING, "메세지"),
                                       fieldDescriptor("body.postId", NUMBER, "게시글 id")
                               )
                       )
               )
               .andExpect(status().isOk());
    }

    @Test
    @DisplayName("게시글 삭제 API 테스트")
    void deleteApiTest() throws Exception {
        // given
        doNothing().when(deletePostUseCase)
                   .delete(any());

        // when & then
        mockMvc.perform(delete(BASE_URL + "/{postId}", 1L)
                       .headers(auth())
                       .contentType(APPLICATION_JSON)
               )
               .andDo(
                       document(
                               POST_DELETE,
                               null,
                               DeletePost.Response.class,
                               null,
                               Arrays.asList(
                                       fieldDescriptor("result.status", STRING, "결과"),
                                       fieldDescriptor("result.message", STRING, "메세지"),
                                       fieldDescriptor("body.postId", NUMBER, "게시글 id")
                               ),
                               List.of(
                                       parameterDescriptor("postId", SimpleType.INTEGER, "게시글 id")
                               )
                       )
               )
               .andExpect(status().isOk());
    }
    

}