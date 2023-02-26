package com.devwinter.postservice.adapter.input.api;

import com.devwinter.postservice.adapter.input.api.dto.BaseResponse;
import com.devwinter.postservice.adapter.input.api.dto.CreatePost;
import com.devwinter.postservice.application.port.input.RegisterPostUseCase;
import com.devwinter.postservice.application.port.input.RegisterPostUseCase.RegisterPostCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostApiController {

    private final RegisterPostUseCase registerPostUseCase;

    @PostMapping
    public BaseResponse<CreatePost.Response> register(
            @RequestHeader("MemberId") Long memberId,
            @Valid @RequestBody CreatePost.Request request) {

        RegisterPostCommand command = RegisterPostCommand.builder()
                                                         .memberId(memberId)
                                                         .title(request.getTitle())
                                                         .contents(request.getContents())
                                                         .category(request.getCategory())
                                                         .images(request.getImages())
                                                         .build();

        Long postId = registerPostUseCase.register(command);
        return CreatePost.Response.success(postId);
    }
}
