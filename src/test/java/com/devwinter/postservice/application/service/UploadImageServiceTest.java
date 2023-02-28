package com.devwinter.postservice.application.service;

import com.devwinter.postservice.application.port.input.UploadImageUseCase;
import com.devwinter.postservice.application.port.output.UploadImagePort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class UploadImageServiceTest {

    @Mock
    private UploadImagePort uploadImagePort;
    
    @InjectMocks
    private UploadImageService uploadImageService;
    
    @Test
    @DisplayName("게시글 이미지 업로드 테스트")
    void uploadTest() {
        // given
        UploadImageUseCase.UploadImageCommand command = new UploadImageUseCase.UploadImageCommand(null, "png");
        ArgumentCaptor<String> targetArgumentCaptor = ArgumentCaptor.forClass(String.class);

        // when
        uploadImageService.upload(command);
        
        // then
        then(uploadImagePort).should().upload(targetArgumentCaptor.capture(), any());
        assertNotNull(targetArgumentCaptor.getValue());
    }
}