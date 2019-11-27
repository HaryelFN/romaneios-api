package com.romaneios.dto.romaneio;

import java.time.LocalDate;

public interface RomaneioResumoDTO {

	public Long getId();
	public String getFornecedor();
	public Long getNumero();
	public LocalDate getDataEntrada();
	public Float getValorRomaneio();
	public Float getValorTransporte();
	public String getSituacao();

}
