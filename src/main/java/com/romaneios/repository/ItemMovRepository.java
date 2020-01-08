package com.romaneios.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.romaneios.model.ItemMov;

public interface ItemMovRepository extends JpaRepository<ItemMov, Long> {

	public List<ItemMov> findByMovCaixaId(Long id);
	
	@Query(value = "SELECT im.* FROM item_mov im INNER JOIN mov_caixa mc ON mc.id = im.id_mov_caixa WHERE mc.id_pedido = :id", nativeQuery = true)
	public List<ItemMov> getByPedidoId(@Param("id") Long id);
}
