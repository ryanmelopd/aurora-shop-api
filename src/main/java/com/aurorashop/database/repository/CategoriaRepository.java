package com.aurorashop.database.repository;

import com.aurorashop.database.model.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Integer> {
    Optional<CategoriaEntity> findByNome(String nome);
    boolean existsByNome(String nome);
}
