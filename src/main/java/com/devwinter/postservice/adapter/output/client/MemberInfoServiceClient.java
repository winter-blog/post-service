package com.devwinter.postservice.adapter.output.client;

import com.devwinter.postservice.application.port.output.LoadMemberInfoPort;
import com.devwinter.postservice.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;
@FeignClient(name = "member-service", configuration = FeignClientConfig.class)
public interface MemberInfoServiceClient {
    @GetMapping("/api/v1/members/internal/info/{memberIds}")
    Map<Long, LoadMemberInfoPort.MemberInfoDto> getMemberInfo(@PathVariable("memberIds") List<Long> memberIds);

}
