package br.com.projetoimigrante.api.dto;

import br.com.projetoimigrante.api.enums.StatusAlojamentoEnum;

import java.math.BigDecimal;

public record CriarAlojamentoRequestDTO(
		Double tamanhoM2,
		Double custoMensal,
		StatusAlojamentoEnum status,
		Integer idProprietario,
		EnderecoDTO endereco
) {
}
