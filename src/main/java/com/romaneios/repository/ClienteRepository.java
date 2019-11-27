package com.romaneios.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.romaneios.dto.cliente.ClienteResDTO;
import com.romaneios.dto.cliente.ClienteResForListDTO;
import com.romaneios.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	@Query(value = "SELECT c.id, c.cpf_cnpj AS cpfCnpj, c.nome_razao AS nomeRazao FROM cliente c", nativeQuery = true)
	List<ClienteResForListDTO> getAllForList();

	@Query(value = "SELECT c.id, c.cpf_cnpj AS cpfCnpj, c.nome_razao AS nomeRazao, c.telefone, c.email FROM cliente c WHERE c.nome_razao LIKE :nome", countQuery = "SELECT COUNT(*) FROM cliente c WHERE c.nome_razao LIKE :nome", nativeQuery = true)
	Page<ClienteResDTO> getAll(@Param("nome") String nome, Pageable pageable);
}
