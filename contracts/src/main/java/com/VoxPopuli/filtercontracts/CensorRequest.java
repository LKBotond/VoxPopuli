package com.VoxPopuli.filtercontracts;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CensorRequest {
    @NotBlank(message = "TextInput is required")
    String textInput;
}
