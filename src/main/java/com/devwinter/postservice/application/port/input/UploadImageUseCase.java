package com.devwinter.postservice.application.port.input;

import com.devwinter.postservice.common.exception.PostException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

import static com.devwinter.postservice.common.exception.PostErrorCode.*;

public interface UploadImageUseCase {
    String upload(UploadImageCommand command);

    enum ExtensionSupportType {
        JPEG,
        GIF,
        PNG
    }

    record UploadImageCommand(
            MultipartFile multipartFile,
            String extension) {

        public UploadImageCommand(MultipartFile multipartFile, String extension) {
            this.multipartFile = multipartFile;
            this.extension = extension;
            validSupportExtension();
        }

        private void validSupportExtension() {
            boolean flag = false;
            for (ExtensionSupportType value : ExtensionSupportType.values()) {
                if(value.name().equals(extension.toUpperCase())) {
                    flag = true;
                    break;
                }
            }
            if(!flag) {
                throw new PostException(POST_IMAGE_UPLOAD_NOT_SUPPORT);
            }
        }
    }
}
