package com.devwinter.postservice.adapter.input.api;

import com.devwinter.postservice.adapter.input.api.dto.BaseResponse;
import com.devwinter.postservice.adapter.input.api.dto.ListPostCategory;
import com.devwinter.postservice.application.port.input.ListPostCategoryQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostCategoryApiController {

    private final ListPostCategoryQuery listPostCategoryQuery;

    @GetMapping("/categories")
    public BaseResponse<ListPostCategory.Response> getCategoryList() {
        return ListPostCategory.Response.success(listPostCategoryQuery.query());
    }
}
