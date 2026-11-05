package com.aurorashop.service;

import com.aurorashop.database.model.ProdutoEntity;
import com.aurorashop.database.repository.ProdutoRepository;
import com.aurorashop.dto.request.ProdutoRequestDto;
import com.aurorashop.dto.response.ProdutoResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public void criarProduto(ProdutoRequestDto produtoRequestDto) {
        ProdutoEntity produtoEntity = ProdutoEntity.builder()
                .nome(produtoRequestDto.nome())
                .descricao(produtoRequestDto.descricao())
                .preco(produtoRequestDto.preco())
                .quantidade(produtoRequestDto.quantidade())
                .build();

        produtoRepository.save(produtoEntity);
    }

    public List<ProdutoResponseDto> verProdutos() {
       return produtoRepository.findAll()
               .stream()
               .map(produto -> new ProdutoResponseDto(
                       produto.getId(),
                       produto.getNome(),
                       produto.getDescricao(),
                       produto.getPreco(),
                       produto.getQuantidade()
               ))
               .toList();
    }

    public ProdutoResponseDto verUmProduto(Integer id) {
        ProdutoEntity produtoEntity = produtoRepository.findById(id)
                .orElseThrow();

        return new ProdutoResponseDto(
                produtoEntity.getId(),
                produtoEntity.getNome(),
                produtoEntity.getDescricao(),
                produtoEntity.getPreco(),
                produtoEntity.getQuantidade()
        );
    }

    @Transactional
    public void atualizarProduto(Integer id, ProdutoRequestDto produtoRequestDto) {
        ProdutoEntity produtoEntity = produtoRepository.findById(id)
                .orElseThrow();

        produtoEntity.setNome(produtoRequestDto.nome());
        produtoEntity.setDescricao(produtoRequestDto.descricao());
        produtoEntity.setPreco(produtoRequestDto.preco());
        produtoEntity.setQuantidade(produtoRequestDto.quantidade());
    }

    @Transactional
    public void deletarProduto(Integer id) {
        ProdutoEntity produtoEntity = produtoRepository.findById(id)
                .orElseThrow();

        produtoRepository.delete(produtoEntity);
    }
}
