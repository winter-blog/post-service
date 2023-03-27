package com.devwinter.postservice.application.port.output;

import org.springframework.web.multipart.MultipartFile;

public interface UploadImagePort {
    String upload(String target, MultipartFile multipartFile);
}
