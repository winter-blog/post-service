package com.devwinter.postservice.application.port.input;

import com.devwinter.postservice.common.exception.PostException;
import com.devwinter.postservice.mother.RegisterPostCommandMother;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.devwinter.postservice.common.exception.PostErrorCode.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RegisterPostUseCaseTest {

    @Test
    @DisplayName("게시글 등록 시 title 유효성 검사 테스트")
    void registerPostCommandTitleValidateTest() {
        PostException e = assertThrows(PostException.class,
                () -> RegisterPostCommandMother.complete().title(null).build());
        assertThat(e.getErrorCode()).isEqualTo(POST_TITLE_REQUIRED);
    }

    @Test
    @DisplayName("게시글 등록 시 contents 유효성 검사 테스트")
    void registerPostCommandContentsValidateTest() {
        PostException e = assertThrows(PostException.class,
                () -> RegisterPostCommandMother.complete().contents(null).build());
        assertThat(e.getErrorCode()).isEqualTo(POST_CONTENTS_REQUIRED);
    }

    @Test
    @DisplayName("게시글 등록 시 category 유효성 검사 테스트")
    void registerPostCommandCategoryValidateTest() {
        PostException e = assertThrows(PostException.class,
                () -> RegisterPostCommandMother.complete().category(null).build());
        assertThat(e.getErrorCode()).isEqualTo(POST_CATEGORY_REQUIRED);
    }

    @Test
    @DisplayName("게시글 등록 시 이미지가 없는 경우 테스트")
    void registerPostCommandImageEmptyValidateTest() {
        RegisterPostUseCase.RegisterPostCommand command = RegisterPostCommandMother.complete()
                                                                                 .build();
        assertNotNull(command.category());
        assertNotNull(command.title());
        assertNotNull(command.contents());
    }
}