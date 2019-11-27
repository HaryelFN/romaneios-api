package com.romaneios.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.romaneios.model.Cheque;

public interface ChequeRepository extends JpaRepository<Cheque, Long> {

	@Query(value = "SELECT * FROM cheque c WHERE c.dt_pag IS NULL")
	public List<Cheque> getByAbertos();
}
