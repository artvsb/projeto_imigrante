package br.com.projetoimigrante.api.service;

import br.com.projetoimigrante.api.dto.FamiliaResponseDTO;
import br.com.projetoimigrante.api.enums.StatusFamiliaEnum;
import br.com.projetoimigrante.api.model.Familia;
import br.com.projetoimigrante.api.model.Imigrante;
import br.com.projetoimigrante.api.repository.FamiliaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FamiliaService {

	@Autowired
	private FamiliaRepository familiaRepository;

	public Familia criarFamilia(Familia familia) {
		familia.setDataCadastro(LocalDate.now());
		familia.setStatus(StatusFamiliaEnum.ATIVA);

		if (familia.getMembros() != null) {
			for (Imigrante imigrante : familia.getMembros()) {
				imigrante.setFamilia(familia);
			}
		}

		return familiaRepository.save(familia);
	}

	public List<FamiliaResponseDTO> buscarTodasFamilias() {
		return familiaRepository.findAll()
				.stream()
				.map(familia -> new FamiliaResponseDTO(
						familia.getId(),
						familia.getNomeReferencia(),
						familia.getDataCadastro(),
						familia.getStatus(),
						familia.getMembros()
								.stream()
								.map(imigrante -> imigrante.getNome())
								.toList()
				))
				.toList();
	}
	public Familia buscarPorId(Integer id) {
		return familiaRepository.findById(id)
				.orElseThrow(
						() -> new RuntimeException("Nenhuma família encontrada."));
	}

	public void deletarPorId(Integer id) {
		familiaRepository.deleteById(id);
	}

	public void atualizarPorId(Integer id, Familia familiaAtt) {
		familiaAtt.setId(id);
		familiaRepository.save(familiaAtt);
	}
}
