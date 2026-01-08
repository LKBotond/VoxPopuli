package com.VoxPopuli.FilterService.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.VoxPopuli.FilterService.dtos.CensorRequest;
import com.VoxPopuli.FilterService.dtos.CensorResponse;
import com.VoxPopuli.FilterService.dtos.DiktUpdateRequest;
import com.VoxPopuli.FilterService.services.Censor;
import com.VoxPopuli.FilterService.services.ProfanityDataHandler;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/internal/censor")
@RequiredArgsConstructor
public class FilterController {
    private final Censor censor;
    private final ProfanityDataHandler handler;

    @PostMapping("/check")
    public ResponseEntity<CensorResponse> checkRequest(@RequestBody CensorRequest request) {
        return ResponseEntity.ok(censor.censorInput(request));
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addDict(@RequestBody DiktUpdateRequest update) {
        handler.loadDictionary(update.getDictLang(), update.getWords());
        return ResponseEntity.noContent().build();
    }

}
