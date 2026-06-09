package br.com.projetoimigrante.api.service;

import br.com.projetoimigrante.api.dto.AlocarFamiliaRequestDTO;
import br.com.projetoimigrante.api.model.Familia;
import br.com.projetoimigrante.api.model.FamiliaAlojamento;
import br.com.projetoimigrante.api.repository.AlojamentoRepository;
import br.com.projetoimigrante.api.repository.FamiliaAlojamentoRepository;
import br.com.projetoimigrante.api.repository.FamiliaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
