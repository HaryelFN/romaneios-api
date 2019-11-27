package com.romaneios.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.romaneios.dto.prestador.PrestadorResumoDTO;
import com.romaneios.model.Prestador;

public interface PrestadorRepository extends JpaRepository<Prestador, Long>{

	@Query(value = "SELECT p.id, p.cpf, p.nome, p.telefone FROM prestador p WHERE p.nome LIKE :nome", 
			countQuery = "SELECT COUNT(*) FROM prestador p WHERE p.nome LIKE :nome", 
			nativeQuery = true)
	Page<PrestadorResumoDTO> getAll(@Param("nome") String nome, Pageable pageable);
}
