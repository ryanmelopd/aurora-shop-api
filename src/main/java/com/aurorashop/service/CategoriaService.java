package com.aurorashop.service;

import com.aurorashop.database.model.CategoriaEntity;
import com.aurorashop.database.repository.CategoriaRepository;
import com.aurorashop.dto.request.CategoriaRequestDto;
import com.aurorashop.dto.response.CategoriaResponseDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    public void criarCategoria(CategoriaRequestDto categoriaRequestDto) {
        if (categoriaRepository.existsByNome(categoriaRequestDto.nome())) {
            throw new RuntimeException("Categoria já cadastrada");
        }

        CategoriaEntity categoriaEntity = CategoriaEntity.builder()
                .nome(categoriaRequestDto.nome())
                .build();

        categoriaRepository.save(categoriaEntity);
    }

    public List<CategoriaResponseDto> verCategorias() {
        return categoriaRepository.findAll()
                .stream()
                .map(categoria -> new CategoriaResponseDto(
                        categoria.getId(),
                        categoria.getNome()
                ))
                .toList();
    }

    public CategoriaResponseDto verUmaCategoria(Integer id) {
        CategoriaEntity categoriaEntity = categoriaRepository.findById(id)
                .orElseThrow();

        return new CategoriaResponseDto(
                categoriaEntity.getId(),
                categoriaEntity.getNome()
        );
    }

    @Transactional
    public void atualizarCategoria(Integer id, CategoriaRequestDto categoriaRequestDto) {
        CategoriaEntity categoriaEntity = categoriaRepository.findById(id)
                .orElseThrow();

        categoriaEntity.setNome(categoriaRequestDto.nome());
    }

    @Transactional
    public void deletarCategoria(Integer id) {
        CategoriaEntity categoriaEntity = categoriaRepository.findById(id)
                .orElseThrow();

        categoriaRepository.delete(categoriaEntity);
    }
}
