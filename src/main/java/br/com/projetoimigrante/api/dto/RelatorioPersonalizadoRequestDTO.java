package br.com.projetoimigrante.api.dto;

import java.util.List;

public record RelatorioPersonalizadoRequestDTO(
		List<CampoSelecionadoRelatorioDTO> campos,
		Integer limite
) {
}
