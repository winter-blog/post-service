package com.devwinter.postservice.domain;

import com.devwinter.postservice.common.exception.PostException;
import com.devwinter.postservice.mother.PostMother;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.devwinter.postservice.common.exception.PostErrorCode.POST_ALREADY_DELETED;
import static com.devwinter.postservice.common.exception.PostErrorCode.POST_NOT_AUTHORITY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PostTest {

    @Test
    @DisplayName("게시글 삭제 시 이미 삭제된 경우 테스트")
    void deleteAlreadyTest() {
        // given
        Post post = PostMother.complete()
                              .deleted(true)
                              .build();

        // when
        PostException e = assertThrows(PostException.class, post::delete);

        // then
        assertThat(e.getErrorCode()).isEqualTo(POST_ALREADY_DELETED);
    }

    @Test
    @DisplayName("게시글 삭제 테스트")
    void deleteTest() {
        // given
        Post post = PostMother.complete()
                              .build();

        assertThat(post.isDeleted()).isFalse();

        // when
        post.delete();

        // then
        assertThat(post.isDeleted()).isTrue();
    }

    @Test
    @DisplayName("게시글 권한 검사 실패 테스트")
    void checkAuthorityNotValidTest() {
        // given
        Post post = PostMother.complete()
                              .memberId(new MemberId(1L))
                              .build();

        // when
        PostException e = assertThrows(PostException.class, () -> post.checkAuthority(2L));

        // then
        assertThat(e.getErrorCode()).isEqualTo(POST_NOT_AUTHORITY);
    }
    
    @Test
    @DisplayName("게시글 권한 검사 테스트")
    void checkAuthorityTest() {
        // given
        Post post = PostMother.complete()
                              .memberId(new MemberId(1L))
                              .build();

        // when
        post.checkAuthority(1L);
        
        // then
    }

}