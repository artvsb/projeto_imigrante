package br.com.projetoimigrante.api.dto;

import br.com.projetoimigrante.api.enums.SexoEnum;

import java.time.LocalDate;

public record ImigranteRequestDTO(
		String nome,
		SexoEnum sexo,
		LocalDate dataNascimento,
		String nrDocumento,
		Boolean refugiado,
		String idPais,
		Integer idFamilia
) {
}
