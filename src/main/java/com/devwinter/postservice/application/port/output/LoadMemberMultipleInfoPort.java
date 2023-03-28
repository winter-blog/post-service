package com.devwinter.postservice.application.port.output;

import java.util.Map;
import java.util.Set;

public interface LoadMemberMultipleInfoPort {
    Map<Long, MemberInfoDto> load(Set<Long> memberIds);

    record MemberInfoDto(Long memberId,
                         String nickName,
                         String profile) {
    }
}
