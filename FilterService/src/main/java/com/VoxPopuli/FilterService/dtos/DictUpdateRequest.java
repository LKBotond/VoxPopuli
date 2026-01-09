package com.VoxPopuli.FilterService.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DictUpdateRequest {
    String dictLang;
    Set<String> words;
}
