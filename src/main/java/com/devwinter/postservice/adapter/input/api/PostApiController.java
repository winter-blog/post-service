package com.devwinter.postservice.adapter.input.api;

import com.devwinter.postservice.adapter.input.api.dto.BaseResponse;
import com.devwinter.postservice.adapter.input.api.dto.CreatePost;
import com.devwinter.postservice.adapter.input.api.dto.DeletePost;
import com.devwinter.postservice.adapter.input.api.dto.DetailPost;
import com.devwinter.postservice.application.port.input.DeletePostUseCase;
import com.devwinter.postservice.application.port.input.DetailPostQuery;
import com.devwinter.postservice.application.port.input.DetailPostQuery.DetailPostDto;
import com.devwinter.postservice.application.port.input.RegisterPostUseCase;
import com.devwinter.postservice.application.port.input.RegisterPostUseCase.RegisterPostCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostApiController {

    private final RegisterPostUseCase registerPostUseCase;
    private final DeletePostUseCase deletePostUseCase;
    private final DetailPostQuery detailPostQuery;

    @PostMapping
    public BaseResponse<CreatePost.Response> register(
            @RequestHeader("MemberId") Long memberId,
            @Valid @RequestBody CreatePost.Request request) {
        Long postId = registerPostUseCase.register(memberId, request.toCommand());
        return CreatePost.Response.success(postId);
    }

    @DeleteMapping("/{postId}")
    public BaseResponse<DeletePost.Response> delete(
            @RequestHeader("MemberId") Long memberId,
            @PathVariable Long postId) {
        deletePostUseCase.delete(new DeletePostUseCase.DeletePostCommand(memberId, postId));
        return DeletePost.Response.success(postId);
    }

    @GetMapping("/{postId}")
    public BaseResponse<?> detail(
            @RequestHeader(value = "MemberId", required = false) Long memberId,
            @PathVariable Long postId) {
        DetailPostDto detailPost = detailPostQuery.find(postId);

        return (Objects.isNull(memberId)) ?
                DetailPost.AnonymousResponse.success(detailPost) :
                DetailPost.Response.success(memberId, detailPost);
    }
}
