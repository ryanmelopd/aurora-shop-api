package com.aurorashop.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CategoriaRequestDto(
        @NotBlank
        String nome
) {
}
