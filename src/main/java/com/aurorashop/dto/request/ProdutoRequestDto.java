package com.aurorashop.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ProdutoRequestDto(
        @NotBlank
        String nome,
        @NotBlank
        String descricao,
        @NotNull
        @Positive
        BigDecimal preco,
        @NotNull
        @PositiveOrZero
        Integer quantidade
) {
}
