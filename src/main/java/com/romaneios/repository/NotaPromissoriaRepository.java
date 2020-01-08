package com.romaneios.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.romaneios.model.NotaPromissoria;

public interface NotaPromissoriaRepository extends JpaRepository<NotaPromissoria, Long> {

	@Query(value = "SELECT np.* FROM np WHERE np.emitente LIKE :emitente", countQuery = "SELECT COUNT(*) FROM np WHERE np.emitente LIKE :emitente", nativeQuery = true)
	Page<NotaPromissoria> getAll(@Param("emitente") String emitente, Pageable pageable);

	@Query(value = "SELECT * FROM np WHERE np.dt_pag IS NULL", nativeQuery = true)
	public List<NotaPromissoria> getByAbertos();

	@Query(value = "SELECT np.* FROM np INNER JOIN item_mov im ON im.id_np = np.id INNER JOIN mov_caixa mc ON mc.id = im.id_mov_caixa INNER JOIN pedido p ON p.id = mc.id_pedido INNER JOIN item_pedido ip ON ip.id_pedido = p.id INNER JOIN produto_romaneio pr ON pr.id = ip.id_produto_romaneio WHERE pr.id_romaneio = 1 AND np.dt_pag IS NULL", nativeQuery = true)
	public List<NotaPromissoria> getByRomaneioId(@Param("id") Long id);
}
