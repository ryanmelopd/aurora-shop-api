package com.aurorashop.dto.response;

import java.math.BigDecimal;

public record ProdutoResponseDto(
        Integer id,
        String nome,
        String descricao,
        BigDecimal preco,
        Integer quantidade
) {
}
