package com.aurorashop.controller;

import com.aurorashop.dto.request.CategoriaRequestDto;
import com.aurorashop.dto.response.CategoriaResponseDto;
import com.aurorashop.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criarCategoria(@Valid @RequestBody CategoriaRequestDto categoriaRequestDto) {
        categoriaService.criarCategoria(categoriaRequestDto);
    }

    @GetMapping
    public List<CategoriaResponseDto> verCategorias() {
        return categoriaService.verCategorias();
    }

    @GetMapping("/{id}")
    public CategoriaResponseDto verUmaCategoria(@PathVariable Integer id) {
        return categoriaService.verUmaCategoria(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarCategoria(@PathVariable Integer id, @Valid @RequestBody CategoriaRequestDto categoriaRequestDto) {
        categoriaService.atualizarCategoria(id, categoriaRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarCategoria(@PathVariable Integer id) {
        categoriaService.deletarCategoria(id);
    }

}
