package com.VoxPopuli.Gateway.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.VoxPopuli.filtercontracts.DictUpdateRequest;

@FeignClient(name = "filter-service", url = "")
public interface FilterClient {

    @PostMapping("/internal/censor/add")
    public Void addDict(@RequestBody DictUpdateRequest update);
}
