package com.aurorashop.database.repository;

import com.aurorashop.database.model.CategoriaEntity;
import com.aurorashop.database.model.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Integer> {
    Optional<ProdutoEntity> findByNome(String nome);
}
