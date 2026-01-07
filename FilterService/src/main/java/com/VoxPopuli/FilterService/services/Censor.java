package com.VoxPopuli.FilterService.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.VoxPopuli.FilterService.dtos.CensorRequest;
import com.VoxPopuli.FilterService.dtos.CensorResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Censor {

    //extra filters should be put here
    private final TextNormalizer normalizer;
    private final ProfanityDataHandler handler;

    //feel free to rewise this logic with your own flavour of filters
    public CensorResponse censorInput(CensorRequest request) {

        List<String> exteractedAndNormalizedWords = normalizer.normalizeInputIntoList(request.getTextInput());
        List<String> flagged = new ArrayList<>();
        for (String word : exteractedAndNormalizedWords) {
            if (handler.foundWord(word)) {
                flagged.add(word);
            }
        }
        if (!flagged.isEmpty()) {
            return buildResponse(true, flagged);
        }
        return buildResponse(false, flagged);
    }

    private CensorResponse buildResponse(boolean flag, List<String> foundWords) {
        return CensorResponse.builder().caughtWords(foundWords).flagged(flag).build();
    }
}
