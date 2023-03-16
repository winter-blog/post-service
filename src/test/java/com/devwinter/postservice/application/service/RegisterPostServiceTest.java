package com.devwinter.postservice.application.service;

import com.devwinter.postservice.application.port.input.RegisterPostUseCase;
import com.devwinter.postservice.application.port.output.SavePostPort;
import com.devwinter.postservice.domain.Post;
import com.devwinter.postservice.mother.PostMother;
import com.devwinter.postservice.mother.RegisterPostCommandMother;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RegisterPostServiceTest {

    @Mock
    private SavePostPort savePostPort;

    @InjectMocks
    private RegisterPostService registerPostService;

    @Test
    @DisplayName("게시글 저장 시 도메인 정상 생성 체크 테스트")
    void registerTest() {
        // given
        RegisterPostUseCase.RegisterPostCommand command = RegisterPostCommandMother.complete().build();
        Post post = PostMother.complete().build();
        Long memberId = 1L;

        given(savePostPort.save(any())).willReturn(post);
        ArgumentCaptor<Post> postArgumentCaptor = ArgumentCaptor.forClass(Post.class);

        // when
        Long postId = registerPostService.register(memberId, command);

        // then
        then(savePostPort).should().save(postArgumentCaptor.capture());
        assertNull(postArgumentCaptor.getValue().getId());
        assertThat(postArgumentCaptor.getValue().getTitle()).isEqualTo(command.title());
        assertThat(postArgumentCaptor.getValue().getContents()).isEqualTo(command.contents());
        assertThat(postArgumentCaptor.getValue().getMemberId().value()).isEqualTo(memberId);
        assertThat(postArgumentCaptor.getValue().getCategory().name()).isEqualTo(command.category());
        assertThat(postArgumentCaptor.getValue().getPostImageCollection().getPostImages().size())
                .isEqualTo(command.images().size());
        assertThat(postArgumentCaptor.getValue().isDeleted()).isFalse();
        assertThat(postId).isEqualTo(post.getId().value());
    }

    @Test
    @DisplayName("게시글 저장 시 이미지가 없는 경우 테스트")
    void registerImageEmptyTest() {
        // given
        RegisterPostUseCase.RegisterPostCommand command = RegisterPostCommandMother.complete().images(null).build();
        Post post = PostMother.complete().build();
        Long memberId = 1L;

        given(savePostPort.save(any())).willReturn(post);
        ArgumentCaptor<Post> postArgumentCaptor = ArgumentCaptor.forClass(Post.class);

        // when
        Long postId = registerPostService.register(memberId, command);

        // then
        then(savePostPort).should().save(postArgumentCaptor.capture());
        assertNull(postArgumentCaptor.getValue().getId());
        assertThat(postArgumentCaptor.getValue().getTitle()).isEqualTo(command.title());
        assertThat(postArgumentCaptor.getValue().getContents()).isEqualTo(command.contents());
        assertThat(postArgumentCaptor.getValue().getMemberId().value()).isEqualTo(memberId);
        assertThat(postArgumentCaptor.getValue().getCategory().name()).isEqualTo(command.category());
        assertThat(postArgumentCaptor.getValue().getPostImageCollection().getPostImages().isEmpty()).isTrue();
        assertThat(postArgumentCaptor.getValue().isDeleted()).isFalse();
        assertThat(postId).isEqualTo(post.getId().value());
    }

}