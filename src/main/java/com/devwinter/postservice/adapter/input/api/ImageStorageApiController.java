package com.devwinter.postservice.adapter.input.api;

import com.devwinter.postservice.adapter.input.api.dto.BaseResponse;
import com.devwinter.postservice.adapter.input.api.dto.UploadImage;
import com.devwinter.postservice.application.port.input.UploadImageUseCase;
import com.devwinter.postservice.application.port.input.UploadImageUseCase.UploadImageCommand;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/images")
public class ImageStorageApiController {

    private final UploadImageUseCase uploadImageUseCase;

    @PostMapping("/upload")
    public BaseResponse<UploadImage.Response> upload(@RequestPart(value = "image", required = true) MultipartFile image) {
        String extension = FilenameUtils.getExtension(image.getOriginalFilename());
        String path = uploadImageUseCase.upload(new UploadImageCommand(image, extension));
        return UploadImage.Response.success(path);
    }
}
