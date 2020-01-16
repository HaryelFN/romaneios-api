package com.romaneios.repository.projection;

import java.time.LocalDate;

public interface DevolucaoListProjecton {
	
	public String getNumPed();
	
	public String getCliente();
	
	public String getNumRom();
	
	public String getProduto();

	public Long getIdR();

	public LocalDate getDataR();

	public Integer getQtdR();

	public Float getValorR();

	public Long getIdD();

	public LocalDate getDataD();
	
	public Integer getQtdD();
	
	public Float getValorD();

	public LocalDate getDataPagD();
}
