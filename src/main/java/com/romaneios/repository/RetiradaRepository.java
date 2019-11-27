package com.romaneios.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.romaneios.model.Retirada;

public interface RetiradaRepository extends JpaRepository<Retirada, Long> {

	public List<Retirada> findByLimpaId(Long id);
}
