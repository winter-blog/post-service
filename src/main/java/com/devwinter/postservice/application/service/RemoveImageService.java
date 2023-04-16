package com.devwinter.postservice.application.service;

import com.devwinter.postservice.application.port.input.RemoveImageUseCase;
import com.devwinter.postservice.application.port.output.RemoveImagePort;
import com.devwinter.postservice.common.UseCase;
import com.devwinter.postservice.common.exception.PostException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.devwinter.postservice.common.exception.PostErrorCode.POST_IMAGE_NOT_FOUND;

@Slf4j
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

    @Override
    @Async("removeImageTaskExecutor")
    public void remove(List<String> totalImages, List<String> updateImages) {
        List<String> exclusiveImages = new ArrayList<>(totalImages);
        exclusiveImages.removeAll(updateImages);

        for (String url : exclusiveImages) {
            try {
                if(Objects.nonNull(url) && !url.equals("")) {
                    String filePath = getFilePath(url);

                    if (!removeImagePort.existImage(filePath)) {
                        throw new PostException(POST_IMAGE_NOT_FOUND);
                    }
                    removeImagePort.remove(filePath);
                }
            } catch (PostException e) {
                log.error("remove error:", e);
            }
        }
    }

    private String getFilePath(String url) {
        if (!Objects.isNull(baseUrl)) {
            return url.replace(baseUrl, "");
        }
        return null;
    }
}
