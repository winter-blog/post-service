package com.devwinter.postservice.application.service;

import com.devwinter.postservice.application.port.output.RemoveImagePort;
import com.devwinter.postservice.common.exception.PostException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.devwinter.postservice.common.exception.PostErrorCode.POST_IMAGE_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class RemoveImageServiceTest {
    
    @Mock
    private RemoveImagePort removeImagePort;

    @InjectMocks
    private RemoveImageService removeImageService;
    
    @Test
    @DisplayName("이미지 삭제 시 원격지에 이미지가 없는 경우 테스트")
    void removeNotExistImageTest() {
        // given
        given(removeImagePort.existImage(any()))
                .willReturn(false);

        // when
        PostException e = assertThrows(PostException.class,
                () -> removeImageService.remove("remove"));

        // then
        assertThat(e.getErrorCode()).isEqualTo(POST_IMAGE_NOT_FOUND);

    }

}