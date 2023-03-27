package com.devwinter.postservice.adapter.input.api;

import com.devwinter.postservice.adapter.input.api.dto.BaseResponse;
import com.devwinter.postservice.adapter.input.api.dto.RemoveImage;
import com.devwinter.postservice.adapter.input.api.dto.UploadImage;
import com.devwinter.postservice.application.port.input.RemoveImageUseCase;
import com.devwinter.postservice.application.port.input.UploadImageUseCase;
import com.devwinter.postservice.application.port.input.UploadImageUseCase.UploadImageCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/images")
public class ImageStorageApiController {

    private final UploadImageUseCase uploadImageUseCase;
    private final RemoveImageUseCase removeImageUseCase;


    @PostMapping("/upload")
    public BaseResponse<UploadImage.Response> upload(@RequestPart(value = "image", required = true) MultipartFile image) {
        String extension = FilenameUtils.getExtension(image.getOriginalFilename());
        String path = uploadImageUseCase.upload(new UploadImageCommand(image, extension));
        return UploadImage.Response.success(path);
    }

    @DeleteMapping("/remove")
    public BaseResponse<RemoveImage.Response> remove(
            @Valid @RequestBody RemoveImage.Request request) {
        removeImageUseCase.remove(request.getUrl());
        return RemoveImage.Response.success();
    }
}
