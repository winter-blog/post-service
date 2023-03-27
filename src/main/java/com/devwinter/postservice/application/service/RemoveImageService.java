package com.devwinter.postservice.application.service;

import com.devwinter.postservice.application.port.input.RemoveImageUseCase;
import com.devwinter.postservice.application.port.output.RemoveImagePort;
import com.devwinter.postservice.common.UseCase;
import com.devwinter.postservice.common.exception.PostException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;

import java.util.Objects;

import static com.devwinter.postservice.common.exception.PostErrorCode.POST_IMAGE_NOT_FOUND;

@UseCase
@RequiredArgsConstructor
public class RemoveImageService implements RemoveImageUseCase {

    private final RemoveImagePort removeImagePort;
    @Value("${cloud.aws.s3.base-url}")
    private String baseUrl;

    @Override
    @Async("removeImageTaskExecutor")
    public void remove(String url) {
        String filePath = getFilePath(url);

        if (!removeImagePort.existImage(filePath)) {
            throw new PostException(POST_IMAGE_NOT_FOUND);
        }
        removeImagePort.remove(filePath);
    }

    private String getFilePath(String url) {
        if (!Objects.isNull(baseUrl)) {
            return url.replace(baseUrl, "");
        }
        return null;
    }
}
