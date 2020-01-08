package com.romaneios.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.romaneios.model.Cheque;

public interface ChequeRepository extends JpaRepository<Cheque, Long> {
	
	@Query(value = "SELECT c.* FROM cheque c WHERE c.num_cheque LIKE :numCheque", 
			countQuery = "SELECT COUNT(*) FROM cheque c WHERE c.num_cheque LIKE :numCheque", 
			nativeQuery = true)
	Page<Cheque> getAll(@Param("numCheque") String numCheque, Pageable pageable);

	@Query(value = "SELECT * FROM cheque c WHERE c.dt_pag IS NULL", nativeQuery = true)
	public List<Cheque> getByAbertos();
	
	@Query(value = "SELECT c.* FROM cheque c INNER JOIN item_mov im ON im.id_cheque = c.id INNER JOIN mov_caixa mc ON mc.id = im.id_mov_caixa INNER JOIN pedido p ON p.id = mc.id_pedido INNER JOIN item_pedido ip ON ip.id_pedido = p.id INNER JOIN produto_romaneio pr ON pr.id = ip.id_produto_romaneio WHERE pr.id_romaneio = :id AND c.dt_pag IS NULL", nativeQuery = true)
	public List<Cheque> getByRomaneioId(@Param("id") Long id);
}
