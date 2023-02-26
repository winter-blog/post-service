package com.devwinter.postservice.application.port.input;

import com.devwinter.postservice.common.exception.PostException;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.devwinter.postservice.common.exception.PostErrorCode.*;

public interface RegisterPostUseCase {
    Long register(RegisterPostCommand command);


    record RegisterPostCommand (String title, String contents, String category, Long memberId, List<String> images) {

        @Builder
        private RegisterPostCommand(Long memberId, String title, String contents, String category, List<String> images) {
            this(title, contents, category, memberId, (images != null) ? images : new ArrayList<>());
            validateSelf();
        }

        private void validateSelf() {
            if(Objects.isNull(memberId)) throw new PostException(MEMBER_NOT_VALID);
            if(Objects.isNull(title)) throw new PostException(POST_TITLE_REQUIRED);
            if(Objects.isNull(contents)) throw new PostException(POST_CONTENTS_REQUIRED);
            if(Objects.isNull(category)) throw new PostException(POST_CATEGORY_REQUIRED);
        }
    }
}
