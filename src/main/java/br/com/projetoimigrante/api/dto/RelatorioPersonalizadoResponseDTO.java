package br.com.projetoimigrante.api.dto;

import java.util.List;
import java.util.Map;

public record RelatorioPersonalizadoResponseDTO(
		List<CampoRelatorioDTO> campos,
		List<Map<String, Object>> linhas
) {
}
