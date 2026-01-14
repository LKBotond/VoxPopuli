package com.VoxPopuli.CommentService.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.VoxPopuli.filtercontracts.CensorRequest;
import com.VoxPopuli.filtercontracts.CensorResponse;


@FeignClient(name = "filter-service", url = "http://filter-service:8080")
public interface FilterClient {

    @PostMapping("/internal/censor/check")
    public CensorResponse checkRequest(@RequestBody CensorRequest request);
}