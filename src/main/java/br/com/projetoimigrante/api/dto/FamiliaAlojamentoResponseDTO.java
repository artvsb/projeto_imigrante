package br.com.projetoimigrante.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record FamiliaAlojamentoResponseDTO(
		String logradouro,
		String cidade,
		String estado,
		String cep,

		@JsonProperty("nome_familia")
		String nomeFamilia,

		List<String> membros
) {
}
