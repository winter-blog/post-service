package com.devwinter.postservice.application.port.output;

public interface RemoveImagePort {
    boolean existImage(String filePath);
    void remove(String filePath);
}
