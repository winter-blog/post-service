package com.devwinter.postservice.application.service;

import com.devwinter.postservice.application.markdown.MarkdownAnalyze;
import com.devwinter.postservice.application.port.input.RegisterPostUseCase;
import com.devwinter.postservice.application.port.input.RemoveImageUseCase;
import com.devwinter.postservice.application.port.output.SavePostPort;
import com.devwinter.postservice.common.UseCase;
import com.devwinter.postservice.domain.Post;
import com.devwinter.postservice.domain.factory.PostFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class RegisterPostService implements RegisterPostUseCase {

    private final SavePostPort savePostPort;
    private final MarkdownAnalyze markdownAnalyze;
    private final RemoveImageUseCase removeImageUseCase;
    @Value("${cloud.aws.s3.base-url}")
    private String baseUrl;
    @Value("${cloud.aws.s3.post-image-prefix}")
    private String basePrefix;
    @Override
    public Long register(Long memberId, RegisterPostCommand command, List<String> totalImages) {
        // 게시글 도메인 생성
        List<String> images = markdownAnalyze.getImages(command.contents());

        Post post = PostFactory.create(memberId,
                command.title(),
                command.contents(),
                command.category(),
                images.stream().map(v ->
                        v.replace(baseUrl + basePrefix, "")
                ).toList());

        removeImageUseCase.remove(totalImages
                .stream()
                .map(markdownAnalyze::extractImageUrl)
                .toList(), images);
        return savePostPort.save(post).getId().value();
    }
}
