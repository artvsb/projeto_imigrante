package br.com.projetoimigrante.api.service;

import br.com.projetoimigrante.api.model.Pais;
import br.com.projetoimigrante.api.model.Regiao;
import br.com.projetoimigrante.api.repository.PaisRepository;
import br.com.projetoimigrante.api.repository.RegiaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaisService {

	@Autowired
	private PaisRepository paisRepository;

	@Autowired
	private RegiaoRepository regiaoRepository;

	public Pais criarPais(Pais pais) {
		Integer idRegiao = pais.getRegiao().getId();

		Regiao regiao = regiaoRepository.findById(idRegiao)
				.orElseThrow(() -> new RuntimeException("Região não encontrada."));

		pais.setRegiao(regiao);
		return paisRepository.save(pais);
	}
}
