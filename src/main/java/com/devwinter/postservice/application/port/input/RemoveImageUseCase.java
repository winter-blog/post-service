package com.devwinter.postservice.application.port.input;

import java.util.List;

public interface RemoveImageUseCase {
    void remove(String url);
    void remove(List<String> totalImages, List<String> updateImages);
}
