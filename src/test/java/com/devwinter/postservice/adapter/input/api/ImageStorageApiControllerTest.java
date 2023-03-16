package com.devwinter.postservice.adapter.input.api;

import com.devwinter.postservice.adapter.input.api.document.AbstractRestDocs;
import com.devwinter.postservice.adapter.input.api.document.dto.FieldDescriptorDto;
import com.devwinter.postservice.application.port.input.UploadImageUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.snippet.Attributes;

import java.util.Arrays;
import java.util.List;

import static com.devwinter.postservice.adapter.input.api.document.PostApiDocumentInfo.IMAGE_UPLOAD;
import static com.devwinter.postservice.adapter.input.api.document.dto.FieldDescriptorDto.fieldDescriptor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ImageStorageApiController.class)
class ImageStorageApiControllerTest extends AbstractRestDocs {
    @MockBean
    private UploadImageUseCase uploadImageUseCase;
    
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
                       multipart("/api/v1/images/upload")
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
                                       FieldDescriptorDto.fieldDescriptor("result.status", STRING, "결과"),
                                       FieldDescriptorDto.fieldDescriptor("result.message", STRING, "메세지"),
                                       FieldDescriptorDto.fieldDescriptor("body.path", STRING, "경로")
                               )
                       )
               )
               .andExpect(status().isOk());
    }
}