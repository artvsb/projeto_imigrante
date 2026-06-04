package br.com.projetoimigrante.api.service;

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

	public List<Familia> buscarTodasFamilias() {
		return familiaRepository.findAll();
	}

	public Familia buscarPorId(Long id) {
		return familiaRepository.findById(id)
				.orElseThrow(
						() -> new RuntimeException("Nenhuma família encontrada."));
	}

	public void deletarPorId(Long id) {
		familiaRepository.deleteById(id);
	}

	public void atualizarPorId(Long id, Familia familiaAtt) {
		familiaAtt.setId(id);
		familiaRepository.save(familiaAtt);
	}
}
