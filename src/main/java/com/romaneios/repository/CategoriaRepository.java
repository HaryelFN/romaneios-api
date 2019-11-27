package com.romaneios.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.romaneios.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
