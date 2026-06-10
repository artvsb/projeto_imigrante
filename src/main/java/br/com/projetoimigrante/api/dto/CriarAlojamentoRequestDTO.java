package br.com.projetoimigrante.api.dto;

import br.com.projetoimigrante.api.enums.StatusAlojamentoEnum;

import java.math.BigDecimal;

public record CriarAlojamentoRequestDTO(
		BigDecimal tamanhoM2,
		BigDecimal custoMensal,
		StatusAlojamentoEnum status,
		Integer idProprietario,
		EnderecoDTO endereco
) {
}
