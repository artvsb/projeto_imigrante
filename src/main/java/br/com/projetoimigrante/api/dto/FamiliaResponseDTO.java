package br.com.projetoimigrante.api.dto;

import br.com.projetoimigrante.api.enums.StatusFamiliaEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@Builder
public record FamiliaResponseDTO(
		Integer id,
		String nomeReferencia,
		LocalDate dataCadastro,
		StatusFamiliaEnum status,
		List<String> membros
) {
}
