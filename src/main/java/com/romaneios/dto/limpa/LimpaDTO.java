package com.romaneios.dto.limpa;

import java.time.LocalDate;

public interface LimpaDTO {


	public Long getId();

	public String getNumPed();

	public String getNomeRazao();

	public String getNumRom();

	public String getProduto();

	public Integer getQtd();

	public Float getValor();

	public LocalDate getDataInicio();
	
	public LocalDate getDataConclusao();
}
