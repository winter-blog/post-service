package com.devwinter.postservice.mother;

import com.devwinter.postservice.application.port.input.RegisterPostUseCase;
import com.devwinter.postservice.domain.Category;

import java.util.List;

public class RegisterPostCommandMother {
    public static RegisterPostUseCase.RegisterPostCommand.RegisterPostCommandBuilder complete() {
        return RegisterPostUseCase.RegisterPostCommand.builder()
                                                      .title("post title")
                                                      .contents("post contents")
                                                      .category(Category.IT.name())
                ;
    }
}
