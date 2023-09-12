package dev.odane.bagservice.client;

import dev.odane.bagservice.model.Member;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "member-service",url = "${spring.application.config.member-url}")
public interface MemberClient {

    @GetMapping("/{member-id}")
    Member findById(@PathVariable("member-id") UUID member);

    @DeleteMapping("")
    void delete(Member member);
}
