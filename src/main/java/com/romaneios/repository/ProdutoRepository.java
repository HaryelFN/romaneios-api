package com.romaneios.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.romaneios.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}