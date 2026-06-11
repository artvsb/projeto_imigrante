package br.com.projetoimigrante.api.service;

import br.com.projetoimigrante.api.dto.AlocarFamiliaRequestDTO;
import br.com.projetoimigrante.api.dto.FamiliaAlojamentoResponseDTO;
import br.com.projetoimigrante.api.model.Familia;
import br.com.projetoimigrante.api.model.FamiliaAlojamento;
import br.com.projetoimigrante.api.repository.AlojamentoRepository;
import br.com.projetoimigrante.api.repository.FamiliaAlojamentoRepository;
import br.com.projetoimigrante.api.repository.FamiliaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class FamiliaAlojamentoService {

	@Autowired
	private FamiliaAlojamentoRepository familiaAlojamentoRepository;

	@Transactional
	public void alocarFamilia(AlocarFamiliaRequestDTO request) {
		familiaAlojamentoRepository.alocarFamilia(
				request.idFamilia(),
				request.idAlojamento());
	}

	@Transactional
	public void desalocarFamilia(Integer idFamilia) {
		familiaAlojamentoRepository.desalocarFamilia(idFamilia);
	}

	public List<FamiliaAlojamentoResponseDTO> buscarAlojamentosOcupados() {
		List<Object[]> linhas = familiaAlojamentoRepository.buscarAlojamentosOcupados();

		Map<String, FamiliaAlojamentoResponseDTO> mapa = new LinkedHashMap<>();

		for (Object[] linha: linhas) {
			Integer idAlojamento = ((Number) linha[0]).intValue();
			Integer idFamilia = ((Number) linha[1]).intValue();

			String chave = idAlojamento + "-" + idFamilia;

			FamiliaAlojamentoResponseDTO dto = mapa.computeIfAbsent(chave, k ->
					new FamiliaAlojamentoResponseDTO(
							(String) linha[2], // logradouro
							(String) linha[3], // cidade
							(String) linha[4], // estado
							(String) linha[5], // cep
							(String) linha[6], // nome familia
							new ArrayList<>()
					)
			);
			dto.membros().add((String) linha[7]); // nome do membro
		}
		return new ArrayList<>(mapa.values());
	}
}
