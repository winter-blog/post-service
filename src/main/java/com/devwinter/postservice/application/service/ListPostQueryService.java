package com.devwinter.postservice.application.service;

import com.devwinter.postservice.application.port.input.ListPostQuery;
import com.devwinter.postservice.application.port.output.LoadMemberInfoPort;
import com.devwinter.postservice.application.port.output.LoadPostListPort;
import com.devwinter.postservice.common.QueryService;
import com.devwinter.postservice.domain.Post;
import lombok.RequiredArgsConstructor;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.text.TextContentRenderer;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.devwinter.postservice.application.port.output.LoadMemberInfoPort.MemberInfoDto;


@QueryService
@RequiredArgsConstructor
public class ListPostQueryService implements ListPostQuery {

    private final LoadPostListPort loadPostListPort;
    private final LoadMemberInfoPort loadMemberInfoPort;
    private final Parser parser;
    private final TextContentRenderer textContentRenderer;
    @Value("${cloud.aws.s3.base-url}")
    private String baseUrl;
    @Value("${cloud.aws.s3.post-image-prefix}")
    private String basePrefix;

    @Override
    public ListPostQueryResultDto query(CursorBaseCommand command) {
        List<Post> posts = loadPostListPort.load(command.key(), command.size());

        Set<Long> memberIds = posts.stream()
                                   .map(p -> p.getMemberId()
                                              .value())
                                   .collect(Collectors.toSet());

        Map<Long, MemberInfoDto> memberInfoDtoMap = loadMemberInfoPort.load(memberIds);

        var nextKey = posts.stream()
                           .mapToLong(p -> p.getId().value())
                           .min()
                           .orElse(-1L);

        return new ListPostQueryResultDto(nextKey, posts.stream()
                                                        .map(
                                                                p -> new PostItemDto(p.getId().value(),
                                                                        p.getMemberId().value(),
                                                                        p.getTitle(),
                                                                        getPlainContents(p),
                                                                        p.getCreatedAt(),
                                                                        memberInfoDtoMap.get(p.getMemberId().value()).nickName(),
                                                                        memberInfoDtoMap.get(p.getMemberId().value()).profile(),
                                                                        (p.getPostImageCollection().hasThumbNail()) ?
                                                                                baseUrl + basePrefix + p.getPostImageCollection().getThumbNailPath()
                                                                                : null))
                                                        .collect(Collectors.toList()));
    }

    private String getPlainContents(Post post) {
        Node document = parser.parse(post.getContents());
        String render = textContentRenderer.render(document);
        return render.replaceAll("(\r\n|\r|\n|\n\r)", ""); // 개행문자 제거
    }
}
