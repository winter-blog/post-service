package com.devwinter.postservice.application.service;

import com.devwinter.postservice.application.port.input.ListPostQuery;
import com.devwinter.postservice.application.port.output.LoadMemberMultipleInfoPort;
import com.devwinter.postservice.application.port.output.LoadPostListPort;
import com.devwinter.postservice.common.QueryService;
import com.devwinter.postservice.domain.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.StopWatch;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.text.TextContentRenderer;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.devwinter.postservice.application.port.output.LoadMemberMultipleInfoPort.MemberInfoDto;


@Slf4j
@QueryService
@RequiredArgsConstructor
public class ListPostQueryService implements ListPostQuery {

    private final LoadPostListPort loadPostListPort;
    private final LoadMemberMultipleInfoPort loadMemberMultipleInfoPort;
    private final Parser parser;
    private final TextContentRenderer textContentRenderer;
    @Value("${cloud.aws.s3.base-url}")
    private String baseUrl;
    @Value("${cloud.aws.s3.post-image-prefix}")
    private String basePrefix;

    private final StopWatch stopWatch = new StopWatch();
    @Override
    public ListPostQueryResultDto query(CursorBaseCommand command) {
        stopWatch.reset();


        stopWatch.start();
        List<Post> posts = loadPostListPort.load(command.key(), command.size());
        stopWatch.stop();
        log.info("post list query seconds: {}", stopWatch);

        stopWatch.reset();
        Set<Long> memberIds = posts.stream()
                                   .map(p -> p.getMemberId()
                                              .value())
                                   .collect(Collectors.toSet());
        stopWatch.start();
        Map<Long, MemberInfoDto> memberInfoDtoMap = loadMemberMultipleInfoPort.load(memberIds);
        stopWatch.stop();
        log.info("member list query seconds: {}", stopWatch);

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
