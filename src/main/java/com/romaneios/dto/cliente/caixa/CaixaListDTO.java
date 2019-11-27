package com.romaneios.dto.cliente.caixa;

import java.time.LocalDate;

public interface CaixaListDTO {

	public Long getId();

	public LocalDate getData();

	public Float getValor();

	public String getOperacao();

	public String getDescricao();

	public Long getIdPed();

	public Long getIdRom();
	
	public Long getIdRet();
}
