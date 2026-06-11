package br.com.projetoimigrante.api.dto;

import java.math.BigDecimal;

public interface AlojamentoDisponivelViewDTO {
	Integer getIdAlojamento();
	BigDecimal getTamanhoM2();
	BigDecimal getCustoMensal();
	String getStatus();
	String getLogradouro();
	String getCidade();
	String getEstado();
	String getCep();
	Integer getIdProprietario();
	String getNomeProprietario();
	String getTelefoneProprietario();
	String getEmailProprietario();
}