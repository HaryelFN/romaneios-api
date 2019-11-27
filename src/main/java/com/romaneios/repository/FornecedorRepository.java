package com.romaneios.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.romaneios.dto.FornecedorResumoDTO;
import com.romaneios.model.Fornecedor;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

	@Query(value = "SELECT f.id, f.cpf_cnpj AS cpfCnpj, f.nome_razao AS nomeRazao, f.telefone, f.email FROM fornecedor f WHERE f.nome_razao LIKE :nome", 
			countQuery = "SELECT COUNT(*) FROM fornecedor f WHERE f.nome_razao LIKE :nome", 
			nativeQuery = true)
	Page<FornecedorResumoDTO> getAll(@Param("nome") String nome, Pageable pageable);

}
