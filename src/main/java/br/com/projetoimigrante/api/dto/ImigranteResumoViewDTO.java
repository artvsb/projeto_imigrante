package br.com.projetoimigrante.api.dto;

import java.time.LocalDate;

public interface ImigranteResumoViewDTO {
	String getNome();
	LocalDate getDataNascimento();
	String getPaisOrigem();
}
