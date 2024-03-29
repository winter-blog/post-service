package com.devwinter.postservice.application.port.output;

import java.util.Map;
import java.util.Set;

public interface LoadMemberInfoPort {
    MemberInfoDto load(Long memberId);

    record MemberInfoDto(Long memberId,
                         String nickName,
                         String profile) {
    }
}
