package com.aurorashop.database.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "categorias")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CategoriaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    @Column(unique = true)
    private String nome;

    @ManyToMany(mappedBy = "categorias")
    private List<ProdutoEntity> produtos;
}
