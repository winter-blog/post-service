package com.devwinter.postservice.application.service;

import com.devwinter.postservice.application.port.input.UploadImageUseCase;
import com.devwinter.postservice.application.port.output.UploadImagePort;
import com.devwinter.postservice.common.UseCase;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class UploadImageService implements UploadImageUseCase {
    private final UploadImagePort uploadImagePort;

    @Override
    public void upload(UploadImageCommand command) {
        String target = String.format("%s.%s", UUID.randomUUID(), command.extension());
        uploadImagePort.upload(target, command.multipartFile());
    }
}
