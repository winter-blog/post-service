package com.devwinter.postservice.adapter.input.api;

import com.devwinter.postservice.adapter.input.api.dto.BaseResponse;
import com.devwinter.postservice.adapter.input.api.dto.DetailPost;
import com.devwinter.postservice.adapter.input.api.dto.ListPost;
import com.devwinter.postservice.application.port.input.DetailPostQuery;
import com.devwinter.postservice.application.port.input.ListPostQuery;
import com.devwinter.postservice.config.PostLengthProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostQueryApiController {
    private final DetailPostQuery detailPostQuery;
    private final ListPostQuery listPostQuery;
    private final PostLengthProperties postLengthProperties;

    @GetMapping("/{postId}")
    public BaseResponse<?> detail(
            @RequestHeader(value = "MemberId", required = false) Long memberId,
            @PathVariable Long postId) {
        DetailPostQuery.DetailPostDto detailPost = detailPostQuery.find(postId);

        return (Objects.isNull(memberId)) ?
                DetailPost.AnonymousResponse.success(detailPost) :
                DetailPost.Response.success(memberId, detailPost);
    }

    @GetMapping
    public BaseResponse<ListPost.Response> getList(@ModelAttribute ListPost.Request request) {
        if(request.getSize() == null) {
            request.setSize(postLengthProperties.getDefaultPaginationSize());
        }
        ListPostQuery.ListPostQueryResultDto result = listPostQuery.query(new ListPostQuery.CursorBaseCommand(request.getKey(), request.getSize()));
        List<ListPost.ListPostItem> listPostItems = result.items()
                                                          .stream()
                                                          .map(v -> ListPost.ListPostItem.success(v, getTitle(v.title()), getContents(v.contents())))
                                                          .toList();

        return ListPost.Response.success(new ListPost.CursorResponse(result.key()), listPostItems);
    }

    private String getTitle(String title) {
        return (title.length() < postLengthProperties.getMaxTitleLength()) ? title : title.substring(0, postLengthProperties.getMaxTitleLength());
    }

    private String getContents(String contents) {
        return (contents.length() < postLengthProperties.getMaxContentsLength()) ? contents : contents.substring(0, postLengthProperties.getMaxContentsLength());
    }
}
