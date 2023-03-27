package com.devwinter.postservice.adapter.input.api;

import com.devwinter.postservice.adapter.input.api.document.AbstractRestDocs;
import com.devwinter.postservice.adapter.input.api.document.dto.FieldDescriptorDto;
import com.devwinter.postservice.adapter.input.api.dto.RemoveImage;
import com.devwinter.postservice.application.port.input.RemoveImageUseCase;
import com.devwinter.postservice.application.port.input.UploadImageUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.snippet.Attributes;

import java.util.Arrays;
import java.util.List;

import static com.devwinter.postservice.adapter.input.api.document.PostApiDocumentInfo.IMAGE_REMOVE;
import static com.devwinter.postservice.adapter.input.api.document.PostApiDocumentInfo.IMAGE_UPLOAD;
import static com.devwinter.postservice.adapter.input.api.document.dto.FieldDescriptorDto.fieldDescriptor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ImageStorageApiController.class)
class ImageStorageApiControllerTest extends AbstractRestDocs {
    @MockBean
    private UploadImageUseCase uploadImageUseCase;
    @MockBean
    private RemoveImageUseCase removeImageUseCase;

    private static final String BASE_URL = "/api/v1/images";
    
    @Test
    @DisplayName("게시글 이미지 업로드 API 테스트")
    void uploadApiTest() throws Exception {
        // given
        given(uploadImageUseCase.upload(any()))
                .willReturn(anyString());

        MockMultipartFile profile = new MockMultipartFile(
                "image",
                "test.png",
                MULTIPART_FORM_DATA_VALUE,
                "test image.png".getBytes());

        // when & then
        mockMvc.perform(
                       multipart(BASE_URL + "/upload")
                               .file(profile)
                               .headers(auth())
               )
               .andDo(print())
               .andDo(
                       document(
                               IMAGE_UPLOAD,
                               null,
                               List.of(
                                       partWithName("image").description("이미지").attributes(Attributes.key("format").value("binary"))
                               ),
                               Arrays.asList(
                                       fieldDescriptor("result.status", STRING, "결과"),
                                       fieldDescriptor("result.message", STRING, "메세지"),
                                       fieldDescriptor("body.path", STRING, "경로")
                               )
                       )
               )
               .andExpect(status().isOk());
    }
    
    @Test
    @DisplayName("게시글 이미지 제거 API 테스트")
    void removeApiTest() throws Exception {
        // given
        RemoveImage.Request request = new RemoveImage.Request("http://aws_s3_url");
        doNothing().when(removeImageUseCase)
                .remove(anyString());

        // when & then
        mockMvc.perform(
                       delete(BASE_URL + "/remove")
                               .headers(auth())
                               .contentType(MediaType.APPLICATION_JSON)
                               .content(requestToJson(request))
               )
               .andDo(print())
               .andDo(
                       document(
                               IMAGE_REMOVE,
                               RemoveImage.Request.class,
                               RemoveImage.Response.class,
                               List.of(
                                       fieldDescriptor("url", STRING, "삭제할 이미지 url")
                               ),
                               List.of(
                                       fieldDescriptor("result.status", STRING, "결과")
                               )
                       )
               )
               .andExpect(status().isOk());
    }
}