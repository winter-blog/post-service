package com.devwinter.postservice.application.port.input;

import com.devwinter.postservice.common.exception.PostException;

import java.util.Objects;

import static com.devwinter.postservice.common.exception.PostErrorCode.MEMBER_ID_REQUIRED;
import static com.devwinter.postservice.common.exception.PostErrorCode.POST_ID_REQUIRED;

public interface DeletePostUseCase {
    void delete(DeletePostCommand command);

    record DeletePostCommand(Long memberId, Long postId) {

        public DeletePostCommand(Long memberId, Long postId) {
            this.memberId = memberId;
            this.postId = postId;

            selfValid();
        }

        private void selfValid() {
            if(Objects.isNull(memberId)) throw new PostException(MEMBER_ID_REQUIRED);
            if(Objects.isNull(postId)) throw new PostException(POST_ID_REQUIRED);
        }
    }
}
