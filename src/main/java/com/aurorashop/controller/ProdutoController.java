package com.aurorashop.controller;

import com.aurorashop.dto.request.ProdutoRequestDto;
import com.aurorashop.dto.response.ProdutoResponseDto;
import com.aurorashop.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {
    private final ProdutoService produtoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criarProduto(@Valid @RequestBody ProdutoRequestDto produtoRequestDto) {
        produtoService.criarProduto(produtoRequestDto);
    }

    @GetMapping
    public List<ProdutoResponseDto> verProdutos() {
        return produtoService.verProdutos();
    }

    @GetMapping("/{id}")
    public ProdutoResponseDto verUmProduto(@PathVariable Integer id) {
        return produtoService.verUmProduto(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarProduto(@PathVariable Integer id, @Valid @RequestBody ProdutoRequestDto produtoRequestDto) {
        produtoService.atualizarProduto(id, produtoRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarProduto(@PathVariable Integer id) {
        produtoService.deletarProduto(id);
    }
}
