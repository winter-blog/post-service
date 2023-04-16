package com.devwinter.postservice.adapter.input.api;

import com.devwinter.postservice.adapter.input.api.dto.*;
import com.devwinter.postservice.application.port.input.DeletePostUseCase;
import com.devwinter.postservice.application.port.input.RegisterPostUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostCommandApiController {

    private final RegisterPostUseCase registerPostUseCase;
    private final DeletePostUseCase deletePostUseCase;

    @PostMapping
    public BaseResponse<CreatePost.Response> register(
            @RequestHeader("MemberId") Long memberId,
            @Valid @RequestBody CreatePost.Request request) {

        Long postId = registerPostUseCase.register(memberId, request.toCommand(), request.getImages());
        return CreatePost.Response.success(postId);
    }

    @DeleteMapping("/{postId}")
    public BaseResponse<DeletePost.Response> delete(
            @RequestHeader("MemberId") Long memberId,
            @PathVariable Long postId) {
        deletePostUseCase.delete(new DeletePostUseCase.DeletePostCommand(memberId, postId));
        return DeletePost.Response.success(postId);
    }

}
