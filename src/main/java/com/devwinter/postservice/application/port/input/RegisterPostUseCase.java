package com.devwinter.postservice.application.port.input;

import com.devwinter.postservice.common.exception.PostException;
import lombok.Builder;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.devwinter.postservice.common.exception.PostErrorCode.*;

public interface RegisterPostUseCase {
    Long register(Long memberId, RegisterPostCommand command, List<String> totalImages);


    record RegisterPostCommand (String title, String contents, String category) {

        @Builder
        public RegisterPostCommand(String title, String contents, String category) {
            this.title = title;
            this.contents = contents;
            this.category = category;
            validateSelf();
        }

        private void validateSelf() {
            if(Objects.isNull(title)) throw new PostException(POST_TITLE_REQUIRED);
            if(Objects.isNull(contents)) throw new PostException(POST_CONTENTS_REQUIRED);
            if(Objects.isNull(category)) throw new PostException(POST_CATEGORY_REQUIRED);
        }
    }
}
