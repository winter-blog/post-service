package com.devwinter.postservice.adapter.output.client;

import com.devwinter.postservice.application.port.output.LoadMemberInfoPort;
import com.devwinter.postservice.application.port.output.LoadMemberMultipleInfoPort;
import com.devwinter.postservice.common.ClientAdapter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Set;
@ClientAdapter
@RequiredArgsConstructor
public class MemberInfoClientAdapter implements LoadMemberMultipleInfoPort, LoadMemberInfoPort {

    private final MemberInfoServiceClient memberInfoServiceClient;
    @Override
    public Map<Long, LoadMemberMultipleInfoPort.MemberInfoDto> load(Set<Long> memberIds) {
        return memberInfoServiceClient.getMemberMultipleInfo(memberIds.stream().toList());
    }

    @Override
    public LoadMemberInfoPort.MemberInfoDto load(Long memberId) {
        return memberInfoServiceClient.getMemberInfo(memberId);
    }
}