package br.com.projetoimigrante.api.dto;

import br.com.projetoimigrante.api.model.Estado;

public record EnderecoDTO(
		String logradouro,
		String cidade,
		Estado estado,
		String cep
) {
}
