package com.romaneios.dto.pagitempedido;

import java.time.LocalDate;

public interface PagAVencerDTO {

	public Long getId();

	public String getTipoPagamento();

	public LocalDate getDataVencimento();

	public Float getValor();

	public String getBanco();

	public String getAgencia();

	public String getConta();

	public String getEmitente();

	public String getNumCheque();

	public String getNomeRazao();

	public Integer getNumero();
}
