package com.devwinter.postservice.application.service;

import com.devwinter.postservice.application.port.input.UploadImageUseCase;
import com.devwinter.postservice.application.port.output.UploadImagePort;
import com.devwinter.postservice.common.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class UploadImageService implements UploadImageUseCase {
    @Value("${cloud.aws.s3.base-url}")
    private String baseUrl;
    private final UploadImagePort uploadImagePort;

    @Override
    public String upload(UploadImageCommand command) {
        String remotePath = String.format("%s.%s", UUID.randomUUID(), command.extension());
        String path = uploadImagePort.upload(remotePath, command.multipartFile());
        return baseUrl + path;
    }
}
