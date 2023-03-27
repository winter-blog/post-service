package com.devwinter.postservice.adapter.output.client;

import com.devwinter.postservice.application.port.output.LoadMemberInfoPort;
import com.devwinter.postservice.common.ClientAdapter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Set;
@ClientAdapter
@RequiredArgsConstructor
public class MemberInfoClientAdapter implements LoadMemberInfoPort {

    private final MemberInfoServiceClient memberInfoServiceClient;
    @Override
    public Map<Long, MemberInfoDto> load(Set<Long> memberIds) {
        return memberInfoServiceClient.getMemberInfo(memberIds.stream().toList());
    }
}